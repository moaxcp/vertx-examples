package com.github.moaxcp.vertxexamples;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public class HelloServiceImpl implements HelloService {

    public HelloServiceImpl(Vertx vertx) {
    }

    @Override
    public void hello(String name, Handler<AsyncResult<String>> result) {
        result.handle(Future.succeededFuture("Hello %s".formatted(name)));
    }

    @Override
    public void exception(String name, Handler<AsyncResult<String>> result) {
        throw new IllegalStateException("Hello %s".formatted(name));
    }
}
