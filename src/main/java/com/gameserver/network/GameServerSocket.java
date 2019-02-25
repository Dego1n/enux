package com.gameserver.network;

import com.gameserver.config.Config;
import com.gameserver.network.instance.GameServerSocketInstance;
import com.gameserver.network.thread.ClientListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class GameServerSocket {

    private static final Logger log = LoggerFactory.getLogger(GameServerSocket.class);

    public GameServerSocket()
    {
        try
        {
            log.info("Listening clients on {}:{}",Config.GAME_SOCKET_LISTEN_ADDRESS, Config.GAME_SOCKET_LISTEN_PORT);
            // Создаем AsynchronousServerSocketChannel, адрес и порт слушателя достаем из конфига
            final AsynchronousServerSocketChannel listener =
                    AsynchronousServerSocketChannel.open().bind(new InetSocketAddress( Config.GAME_SOCKET_LISTEN_ADDRESS,Config.GAME_SOCKET_LISTEN_PORT));

            // Делаем коллбек на accept
            listener.accept( null, new CompletionHandler<AsynchronousSocketChannel,Void>() {

                @Override
                public void completed(AsynchronousSocketChannel ch, Void att)
                {
                    // Принимаем соединение
                    listener.accept( null, this );

                    ClientListenerThread clientListenerThread = GameServerSocketInstance.getInstance().newClient(ch);
                    clientListenerThread.receivableStream();

                }

                @Override
                public void failed(Throwable exc, Void att) {
                    //TODO
                }
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
