package net.jurinson.jmemcached.protocol.impl;

import net.jurinson.jmemcached.exception.JMemcachedException;
import net.jurinson.jmemcached.protocol.model.Command;
import net.jurinson.jmemcached.protocol.model.Request;
import org.apache.commons.lang.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class DefaultRequestConverterTest {

    private final DefaultRequestConverter defaultRequestConvertor = new DefaultRequestConverter();

    private byte[] requestClear = new byte[]{
            16,     // version
            0,      // status
            0       // flags
    };

    private byte[] requestPut = new byte[]{
            16,                     // version
            1,                      // status
            7,                      // flags
            3,                      // key length
            49, 50, 51,             // key bytes (123)
            0, 0, 0, 0, 0, 0, 0, 5, // ttl (long)
            0, 0, 0, 3,             // data length (int)
            1, 2, 3                 // data bytes
    };

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFlagsByteEmpty() {
        Request request = new Request(Command.CLEAR);
        byte flags = defaultRequestConvertor.getFlagsByte(request);
        assertEquals(0, flags);
    }

    @Test
    public void getFlagsByteAll() {
        Request request = new Request(Command.CLEAR, "key", System.currentTimeMillis(), new byte[]{1});
        byte flags = defaultRequestConvertor.getFlagsByte(request);
        assertEquals(7, flags);
    }

    @Test
    public void writeKeySuccess() throws IOException {
        DataOutputStream dataOutputStream = spy(new DataOutputStream(mock(OutputStream.class)));
        String key = "key";
        defaultRequestConvertor.writeKey(dataOutputStream, new Request(Command.GET, key));

        verify(dataOutputStream).write(key.getBytes(StandardCharsets.US_ASCII));
        verify(dataOutputStream).writeByte(3);
    }

    @Test
    public void writeKeyFailed() throws IOException {
        String key = StringUtils.repeat("a", 128);
        thrown.expect(JMemcachedException.class);
        thrown.expectMessage(is("Key length should be <= 127 bytes for key= " + key));
        DataOutputStream dataOutputStream = new DataOutputStream(null);
        defaultRequestConvertor.writeKey(dataOutputStream, new Request(Command.CLEAR, key));
    }

    @Test
    public void readSimpleRequest() throws IOException {
        Request request = defaultRequestConvertor.readRequest(new ByteArrayInputStream(requestClear));
        assertEquals(Command.CLEAR, request.getCommand());
        assertFalse(request.hasKey());
        assertFalse(request.hasTtl());
        assertFalse(request.hasData());
    }

    @Test
    public void readComplexRequest() throws IOException {
        Request request = defaultRequestConvertor.readRequest(new ByteArrayInputStream(requestPut));
        assertEquals(Command.PUT, request.getCommand());
        assertTrue(request.hasKey());
        assertEquals("123", request.getKey());
        assertTrue(request.hasTtl());
        assertEquals(Long.valueOf(5L), request.getTtl());
        assertTrue(request.hasData());
        assertArrayEquals(new byte[]{1, 2, 3}, request.getData());
    }

    @Test
    public void writeRequestWithoutData() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        defaultRequestConvertor.writeRequest(out, new Request(Command.CLEAR));
        assertArrayEquals(requestClear, out.toByteArray());
    }

    @Test
    public void writeRequestWithData() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        defaultRequestConvertor.writeRequest(out, new Request(Command.PUT, "123", 5L, new byte[]{1, 2, 3}));
        assertArrayEquals(requestPut, out.toByteArray());
    }

}