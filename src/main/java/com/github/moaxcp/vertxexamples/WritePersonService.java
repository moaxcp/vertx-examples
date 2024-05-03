package com.github.moaxcp.vertxexamples;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

@ProxyGen
@VertxGen
public interface WritePersonService {
    String ADDRESS = "vertx.write-person-service";

    static WritePersonService createProxy(Vertx vertx) {
        return new WritePersonServiceVertxEBProxy(vertx, ADDRESS);
    }

    void writePerson(Person person, Handler<AsyncResult<Void>> resultHandler);
}
