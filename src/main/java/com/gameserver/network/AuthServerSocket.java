package com.gameserver.network;

import com.gameserver.config.Config;
import com.gameserver.network.thread.AuthServerConnectionThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AuthServerSocket {
    private static final Logger log = LoggerFactory.getLogger(AuthServerSocket.class);
    public void EstablishConnection() throws IOException {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        InetSocketAddress hostAddress = new InetSocketAddress(Config.AUTH_SERVER_ADDRESS, Config.AUTH_SERVER_GAME_LISTEN_PORT);
        client.connect(hostAddress, null,  new CompletionHandler<Void,Void>()
        {
            @Override
            public void completed(Void att1, Void att2)
            {
                AuthServerConnectionThread thread = new AuthServerConnectionThread(client);
                log.info("Successfully connected to Ceadra");
                thread.receivableStream();
            }
            @Override
            public void failed(Throwable exc, Void att) {
                log.error("Cannot connect to Ceadra");
                log.error(exc.getMessage());
            }
        });
    }
}
