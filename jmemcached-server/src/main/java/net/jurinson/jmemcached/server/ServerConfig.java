package net.jurinson.jmemcached.server;

import net.jurinson.jmemcached.protocol.RequestConverter;
import net.jurinson.jmemcached.protocol.ResponseConverter;

import java.net.Socket;
import java.util.concurrent.ThreadFactory;

public interface ServerConfig extends AutoCloseable{

    RequestConverter getRequestConverter();

    ResponseConverter getResponseConverter();

    Storage getStorage();

    CommandHandler getCommandHandler();

    ThreadFactory getWorkerThreadFactory();

    int getClearDataIntervalInMs();

    int getServerPort();

    int getInitThreadCount();

    int getMaxThreadCount();

    ClientSocketHandler buildNewClientSocketHandler(Socket clientSocket);
}
