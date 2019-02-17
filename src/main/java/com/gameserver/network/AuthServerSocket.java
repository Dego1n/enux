package com.gameserver.network;

import com.gameserver.network.thread.AuthServerConnectionThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AuthServerSocket {

    public void EstablishConnection() throws IOException {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        InetSocketAddress hostAddress = new InetSocketAddress("127.0.0.1", 1234);
        client.connect(hostAddress, null,  new CompletionHandler<Void,Void>()
        {
            @Override
            public void completed(Void att1, Void att2)
            {
                AuthServerConnectionThread thread = new AuthServerConnectionThread(client);
                thread.receivableStream();
            }
            @Override
            public void failed(Throwable exc, Void att) {}
        });
    }
}
