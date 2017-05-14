package net.jurinson.jmemcached.server;

import net.jurinson.jmemcached.protocol.model.Status;

public interface Storage extends AutoCloseable {

    Status put(String key, Long ttl, byte[] data);

    byte[] get(String key);

    Status remove(String key);

    Status clear();
}
