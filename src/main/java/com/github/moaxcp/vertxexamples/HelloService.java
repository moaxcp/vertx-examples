package com.github.moaxcp.vertxexamples;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

@ProxyGen
@VertxGen
public interface HelloService {
    public static final String VERTX_HELLO_SERVICE = "vertx.hello-service";
    static HelloService create(Vertx vertx) {
        return new HelloServiceImpl(vertx);
    }

    static HelloService createProxy(Vertx vertx, String address) {
        return new HelloServiceVertxEBProxy(vertx, address);
    }

    public void hello(String name, Handler<AsyncResult<String>> result);
}
