package net.jurinson.jmemcached.protocol.impl;

import net.jurinson.jmemcached.protocol.model.Response;
import net.jurinson.jmemcached.protocol.model.Status;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class DefaultResponseConverterTest {

    private final DefaultResponseConverter defaultResponseConvertor = new DefaultResponseConverter();

    private byte[] responseWithoutData = new byte[]{
            16, // version
            0,  // status
            0   // flags
    };

    private byte[] responseWithData = new byte[]{
            16,          // version
            0,           // status
            1,           // flags
            0, 0, 0, 3,  // int length
            1, 2, 3      // byte array
    };

    @Test
    public void readResponseWithoutData() throws IOException {
        Response response = defaultResponseConvertor.readResponse(new ByteArrayInputStream(responseWithoutData));
        assertEquals(Status.ADDED, response.getStatus());
        assertFalse(response.hasData());
    }

    @Test
    public void readResponseWithData() throws IOException {
        Response response = defaultResponseConvertor.readResponse(new ByteArrayInputStream(responseWithData));
        assertEquals(Status.ADDED, response.getStatus());
        assertTrue(response.hasData());
        assertArrayEquals(new byte[] {1,2,3}, response.getData());
    }

    @Test
    public void writeResponseWithoutData() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Response response = new Response(Status.GOTTEN);
        defaultResponseConvertor.writeResponse(out, response);
        assertArrayEquals(new byte[]{
                16,     // version
                2,      // status
                0       // flags
        }, out.toByteArray());
    }

    @Test
    public void writeResponseWithData() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Response response = new Response(Status.ADDED, new byte[]{1, 2, 3});
        defaultResponseConvertor.writeResponse(out, response);
        assertArrayEquals(responseWithData, out.toByteArray());
    }
}