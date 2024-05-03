package com.github.moaxcp.vertxexamples;

import java.util.HashMap;
import java.util.Map;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import static io.vertx.core.Future.succeededFuture;

public class PersonServiceImpl implements ReadPersonService, WritePersonService {

    private final Map<String, Person> people = new HashMap<>();

    @Override
    public void readPerson(String name, Handler<AsyncResult<Person>> resultHandler) {
        resultHandler.handle(succeededFuture(people.get(name)));
    }

    @Override
    public void writePerson(Person person, Handler<AsyncResult<Void>> resultHandler) {
        people.put(person.getName(), person);
        resultHandler.handle(succeededFuture());
    }
}
