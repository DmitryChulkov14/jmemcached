package net.jurinson.jmemcached.client.impl;

import net.jurinson.jmemcached.client.ClientConfig;
import net.jurinson.jmemcached.protocol.ObjectSerializer;
import net.jurinson.jmemcached.protocol.RequestConverter;
import net.jurinson.jmemcached.protocol.ResponseConverter;
import net.jurinson.jmemcached.protocol.impl.DefaultObjectSerializer;
import net.jurinson.jmemcached.protocol.impl.DefaultRequestConverter;
import net.jurinson.jmemcached.protocol.impl.DefaultResponseConverter;

class DefaultClientConfig implements ClientConfig{
    private final String host;
    private final int port;
    private final RequestConverter requestConverter;
    private final ResponseConverter responseConverter;
    private final ObjectSerializer objectSerializer;

    DefaultClientConfig(String host, int port) {
        this.host = host;
        this.port = port;
        this.requestConverter = new DefaultRequestConverter();
        this.responseConverter = new DefaultResponseConverter();
        this.objectSerializer = new DefaultObjectSerializer();
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public RequestConverter getRequestConverter() {
        return requestConverter;
    }

    @Override
    public ResponseConverter getResponseConverter() {
        return responseConverter;
    }

    @Override
    public ObjectSerializer getObjectSerializer() {
        return objectSerializer;
    }
}
