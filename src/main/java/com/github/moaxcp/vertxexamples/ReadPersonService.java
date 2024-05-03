package com.github.moaxcp.vertxexamples;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

@ProxyGen
@VertxGen
public interface ReadPersonService {
    String ADDRESS = "vertx.read-person-service";

    static ReadPersonService createProxy(Vertx vertx) {
        return new ReadPersonServiceVertxEBProxy(vertx, ADDRESS);
    }

    void readPerson(String name, Handler<AsyncResult<Person>> resultHandler);
}
