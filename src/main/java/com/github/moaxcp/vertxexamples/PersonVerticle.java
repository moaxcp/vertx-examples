package com.github.moaxcp.vertxexamples;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;

public class PersonVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> start) {
        PersonServiceImpl service = new PersonServiceImpl();
        ServiceBinder readBinder = new ServiceBinder(vertx);
        readBinder.setAddress(ReadPersonService.ADDRESS).register(ReadPersonService.class, service);
        ServiceBinder writeBinder = new ServiceBinder(vertx);
        writeBinder.setAddress(WritePersonService.ADDRESS).register(WritePersonService.class, service);
        start.complete();
    }
}
