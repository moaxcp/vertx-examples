package com.github.moaxcp.vertxexamples;

import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.Timeout;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(VertxUnitRunner.class)
public class PersonTest {

    @ClassRule
    public static final RunTestOnContext contextRule = new RunTestOnContext();

    @ClassRule
    public static final Timeout timeout = Timeout.seconds(2);

    private static ReadPersonService readPersonService;
    private static WritePersonService writePersonService;

    @BeforeClass
    public static void setupClass(TestContext context) {
        contextRule.vertx().exceptionHandler(context.exceptionHandler());
        contextRule.vertx().deployVerticle(new PersonVerticle(), context.asyncAssertSuccess(result -> {
            assertThat(result).isNotBlank();
        }));
        readPersonService = ReadPersonService.createProxy(contextRule.vertx());
        writePersonService = WritePersonService.createProxy(contextRule.vertx());
    }

    @AfterClass
    public static void teardownClass(TestContext context) {
        //contextRule.vertx().close(context.asyncAssertSuccess());
    }

    @Test
    public void test(TestContext context) {
        writePersonService.writePerson(new Person("John"), context.asyncAssertSuccess(ignored -> {
            readPersonService.readPerson("John", context.asyncAssertSuccess(result -> {
                assertThat(result).isEqualTo(new Person("John"));
            }));
        }));
    }
}
