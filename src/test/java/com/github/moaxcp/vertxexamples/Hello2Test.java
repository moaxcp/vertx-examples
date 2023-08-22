package com.github.moaxcp.vertxexamples;

import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.Timeout;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.github.moaxcp.vertxexamples.HelloService.VERTX_HELLO_SERVICE;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(VertxUnitRunner.class)
public class Hello2Test {

    @ClassRule
    public static final RunTestOnContext contextRule = new RunTestOnContext();

    @ClassRule
    public static final Timeout timeout = Timeout.seconds(2);

    static HelloService service;

    @BeforeClass
    public static void setupClass(TestContext context) {
        contextRule.vertx().exceptionHandler(context.exceptionHandler());
        contextRule.vertx().deployVerticle(new HelloVerticle(), context.asyncAssertSuccess(result -> {
            Assertions.assertThat(result).isNotBlank();
        }));
        service = HelloService.createProxy(contextRule.vertx(), VERTX_HELLO_SERVICE);
    }

    @AfterClass
    public static void teardownClass(TestContext context) {
        contextRule.vertx().close(context.asyncAssertSuccess());
    }

    @Test
    public void test(TestContext context) {
        service.hello("John2", context.asyncAssertSuccess(r -> {
            assertThat(r).isEqualTo("Hello John2");
        }));
    }
}
