package com.gameserver.network.instance;

import com.gameserver.network.GameServerSocket;
import com.gameserver.network.thread.ClientListenerThread;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.List;

public class GameServerSocketInstance {

    private static GameServerSocketInstance _instance;

    public static GameServerSocketInstance getInstance()
    {
        if(_instance ==  null)
            _instance =  new GameServerSocketInstance();

        return _instance;
    }

    private final List<ClientListenerThread> _clientListenerThreads;

    private GameServerSocketInstance()
    {
        new GameServerSocket();
        _clientListenerThreads = new ArrayList<>();
    }

    public ClientListenerThread newClient(AsynchronousSocketChannel socketChannel)
    {
        ClientListenerThread clientListenerThread = new ClientListenerThread(socketChannel);
        _clientListenerThreads.add(clientListenerThread);
        return clientListenerThread;
    }


}
