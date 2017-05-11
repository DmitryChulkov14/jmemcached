package net.jurinson.jmemcached.protocol.impl;

import net.jurinson.jmemcached.exception.JMemcachedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class AbstractPackageConvertorTest {

    private AbstractPackageConvertor abstractPackageConvertor = new AbstractPackageConvertor() {
    };

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkProtocolVersionSuccess() {
        try {
            abstractPackageConvertor.checkProtocolVersion((byte) 16);
        } catch (Exception e) {
            fail("Supported protocol version should be 1.0");
        }
    }

    @Test
    public void checkProtocolVersionFailed() {
        thrown.expect(JMemcachedException.class);
        thrown.expectMessage("Unsupported protocol version: 0.0");
        abstractPackageConvertor.checkProtocolVersion((byte) 0);
    }

    @Test
    public void getVersionByte() {
        assertEquals(16, abstractPackageConvertor.getVersionByte());
    }
}