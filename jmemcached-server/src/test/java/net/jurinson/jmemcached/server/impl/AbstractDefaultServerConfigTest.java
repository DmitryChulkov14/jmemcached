package net.jurinson.jmemcached.server.impl;

import net.jurinson.jmemcached.server.Storage;

import java.util.Properties;

import static org.mockito.Mockito.mock;

public abstract class AbstractDefaultServerConfigTest {

    protected Storage storage;

    protected DefaultServerConfig createDefaultServerConfigMock(Properties overrideApplicationProperties) {
        storage = mock(Storage.class);
        return new DefaultServerConfig(overrideApplicationProperties) {
            @Override
            protected Storage createStorage() {
                return storage;
            }
        };
    }
}
