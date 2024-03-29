package com.gameserver.network.thread;

import com.gameserver.packet.game2auth.Pong;
import com.gameserver.packet.game2auth.RequestRegisterGameServer;
import com.gameserver.packet.AbstractSendablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AuthServerConnectionThread {

    private static final Logger log = LoggerFactory.getLogger(AuthServerConnectionThread.class);

    private final AsynchronousSocketChannel _socketChannel;

    private final List<AbstractSendablePacket> packetBuffer;

    private boolean writeIsPending = false;

    public AuthServerConnectionThread(AsynchronousSocketChannel socketChanel)
    {
        _socketChannel = socketChanel;
        packetBuffer = new ArrayList<>();
        sendPacket(new RequestRegisterGameServer());
    }

    public void sendPacket(AbstractSendablePacket packet)
    {
        if(writeIsPending)
            packetBuffer.add(packet);
        else {
            writeIsPending = true;
            _socketChannel.write(ByteBuffer.wrap(packet.prepareAndGetData()), this, new CompletionHandler<>() {
                @Override
                public void completed(Integer result, AuthServerConnectionThread thread) {
                    thread.writeIsPending = false;
                    if (packetBuffer.size() > 0) {
                        thread.sendPacket(packetBuffer.get(0));
                    }
                }

                @Override
                public void failed(Throwable exc, AuthServerConnectionThread thread) {
                    //TODO: close connection?
                    thread.writeIsPending = false;
                }
            });
        }
    }

    public void receivableStream()
    {
        try
        {
            while( _socketChannel.isOpen())
            {
                // Выделаем память 2 байта в байтбаффер для размера пакета
                ByteBuffer byteBuffer = ByteBuffer.allocate( 2 );

                // Читаем размер пакета
                _socketChannel.read( byteBuffer ).get( 3, TimeUnit.MINUTES );

                //Конвертим байтбаффер в массив байтов
                byte[] bytePacketSize =  byteBuffer.array();

                //Конвертим массив байтов в шорт и получаем длинну пакета
                short size = (short)(((bytePacketSize[1] & 0xFF) << 8) | (bytePacketSize[0] & 0xFF));

                //Выделяем память под пакет нужного размера - 2 байта (размер мы уже получили)
                byteBuffer = ByteBuffer.allocate(size - 2);

                //Читаем пакет
                _socketChannel.read(byteBuffer).get(20, TimeUnit.SECONDS);

                //Передаем пакет Хендлеру
                AuthServerPacketHandler.handlePacket(this,byteBuffer.array());
            }
        }
        catch (InterruptedException | ExecutionException e)
        {
            log.error(e.getMessage());
        } catch (TimeoutException e)
        {
            // The user exceeded the 20 second timeout, so close the connection
        }

        try
        {
            // Close the connection if we need to
            if( _socketChannel.isOpen() )
            {
                _socketChannel.close();
            }
        }
        catch (IOException e1)
        {
            log.error(e1.getMessage());
        }
    }
}

class AuthServerPacketHandler {

    private final static short PING = 0x02;

    static void handlePacket(AuthServerConnectionThread authServer, byte [] packet)
    {

        short packetID = (short)(((packet[1] & 0xFF) << 8) | (packet[0] & 0xFF));

        switch(packetID)
        {
            case PING:
                authServer.sendPacket(new Pong());
                break;
        }
    }
}

