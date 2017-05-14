package net.jurinson.jmemcached.server;

import net.jurinson.jmemcached.protocol.model.Request;
import net.jurinson.jmemcached.protocol.model.Response;

public interface CommandHandler {

    Response handle(Request request);
}
