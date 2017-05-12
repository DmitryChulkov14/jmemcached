package net.jurinson.jmemcached.client;

import net.jurinson.jmemcached.protocol.ObjectSerializer;
import net.jurinson.jmemcached.protocol.RequestConverter;
import net.jurinson.jmemcached.protocol.ResponseConverter;

public interface ClientConfig {

    String getHost();

    int getPort();

    RequestConverter getRequestConverter();

    ResponseConverter getResponseConverter();

    ObjectSerializer getObjectSerializer();
}
