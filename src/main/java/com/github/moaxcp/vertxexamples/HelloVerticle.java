package com.github.moaxcp.vertxexamples;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;

import static com.github.moaxcp.vertxexamples.HelloService.VERTX_HELLO_SERVICE;

public class HelloVerticle extends AbstractVerticle {


    @Override
    public void start(Promise<Void> start) {
        HelloService service = HelloService.create(vertx);
        ServiceBinder binder = new ServiceBinder(vertx);
        binder.setAddress(VERTX_HELLO_SERVICE)
                .register(HelloService.class, service);
        start.complete();
    }
}
