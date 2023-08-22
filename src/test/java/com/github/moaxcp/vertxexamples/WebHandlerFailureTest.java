package com.github.moaxcp.vertxexamples;

import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.Timeout;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.Router;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.github.moaxcp.vertxexamples.HelloService.VERTX_HELLO_SERVICE;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(VertxUnitRunner.class)
public class WebHandlerFailureTest {

    @ClassRule
    public static final RunTestOnContext contextRule = new RunTestOnContext();

    @ClassRule
    public static final Timeout timeout = Timeout.seconds(2);

    static HelloService service;

    private static HttpClient client;

    @BeforeClass
    public static void setupClass(TestContext context) {
        contextRule.vertx().exceptionHandler(context.exceptionHandler());
        contextRule.vertx().deployVerticle(new HelloVerticle(), context.asyncAssertSuccess(result -> {
            Assertions.assertThat(result).isNotBlank();
        }));
        service = HelloService.createProxy(contextRule.vertx(), VERTX_HELLO_SERVICE);
        var router = Router.router(contextRule.vertx());
        router.route().path("/webexception").handler(routing -> {
            throw new IllegalStateException("exception from web handler");
        });
        router.route().path("/serviceexception").handler(routing -> {
            service.exception("hello", context.asyncAssertSuccess());
            routing.end();
        });
        router.route().path("/hello").handler(routing -> {
            service.hello("John", context.asyncAssertSuccess(result -> {
                routing.response().end(result);
            }));
        });
        contextRule.vertx().createHttpServer().requestHandler(router).listen(8080);
        client = contextRule.vertx().createHttpClient();
    }

    @AfterClass
    public static void teardownClass(TestContext context) {
        contextRule.vertx().close(context.asyncAssertSuccess());
        client.close(context.asyncAssertSuccess());
    }

    @Test
    public void web_exception(TestContext context) {
        client.request(HttpMethod.GET, 8080, "localhost", "/webexception").onComplete(context.asyncAssertSuccess(request -> {
            request.send().onComplete(context.asyncAssertSuccess(result -> {
                assertThat(result.statusCode()).isEqualTo(500);
            }));
        }));
    }

    @Test
    public void service_exception(TestContext context) {
        client.request(HttpMethod.GET, 8080, "localhost", "/serviceexception").onComplete(context.asyncAssertSuccess(request -> {
            request.send().onComplete(context.asyncAssertSuccess(result -> {
                assertThat(result.statusCode()).isEqualTo(200);
            }));
        }));
    }

    @Test
    public void hello(TestContext context) {
        client.request(HttpMethod.GET, 8080, "localhost", "/hello").onComplete(context.asyncAssertSuccess(request -> {
            request.send().onComplete(context.asyncAssertSuccess(result -> {
                assertThat(result.statusCode()).isEqualTo(200);
                result.body().onComplete(context.asyncAssertSuccess(buffer -> {
                    assertThat(buffer.toString()).isEqualTo("Hello John");
                }));
            }));
        }));
    }
}
