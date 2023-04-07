package com.github.moaxcp.vertxexamples;

import io.vertx.ext.unit.TestContext;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Hello2Test extends BaseTest {
    @Test
    public void test(TestContext context) {
        service.hello("John2", context.asyncAssertSuccess(r -> {
            assertThat(r).isEqualTo("Hello John2");
        }));
    }
}
