package net.jurinson.jmemcached.protocol.impl;

import net.jurinson.jmemcached.exception.JMemcachedException;
import net.jurinson.jmemcached.protocol.model.Version;

abstract class AbstractPackageConvertor {

    protected void checkProtocolVersion(byte versionByte) {
        Version version = Version.valueOf(versionByte);
        if (version != Version.VERSION_1_0) {
            throw new JMemcachedException("Unsupported protocol version: " + version);
        }
    }

    protected byte getVersionByte(){
        return Version.VERSION_1_0.getByteCode();
    }
}
