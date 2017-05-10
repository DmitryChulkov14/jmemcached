package net.jurinson.jmemcached.protocol;

import net.jurinson.jmemcached.protocol.model.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ResponseConvertor {

    Response readResponse(InputStream inputStream) throws IOException;

    void writeResponse(OutputStream outputStream, Response response) throws IOException;
}
