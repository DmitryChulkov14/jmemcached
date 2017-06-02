package net.jurinson.jmemcached.server.impl;

import net.jurinson.jmemcached.exception.JMemcachedException;
import net.jurinson.jmemcached.protocol.model.Command;
import net.jurinson.jmemcached.protocol.model.Request;
import net.jurinson.jmemcached.protocol.model.Response;
import net.jurinson.jmemcached.protocol.model.Status;
import net.jurinson.jmemcached.server.CommandHandler;
import net.jurinson.jmemcached.server.Storage;

class DefaultCommandHandler implements CommandHandler {

    private final Storage storage;

    DefaultCommandHandler(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Response handle(Request request) {
        Status status;
        byte[] data = null;
        if (request.getCommand() == Command.CLEAR) {
            status = storage.clear();
        } else if (request.getCommand() == Command.PUT) {
            status = storage.put(request.getKey(), request.getTtl(), request.getData());
        } else if (request.getCommand() == Command.REMOVE) {
            status = storage.remove(request.getKey());
        } else if (request.getCommand() == Command.GET) {
            data = storage.get(request.getKey());
            status = data == null ? Status.NOT_FOUND : Status.GOTTEN;
        } else {
            throw new JMemcachedException("Unsupported command: " + request.getCommand());
        }
        return new Response(status, data);
    }
}
