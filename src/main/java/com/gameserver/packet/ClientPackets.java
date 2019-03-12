package com.gameserver.packet;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.client2game.*;

public class ClientPackets {

    private final static short REQUEST_CONNECT_TO_GAME_SERVER = 0x01;
    private final static short CHARACTER_SELECTED = 0x02;
    private final static short REQUEST_CREATE_CHARACTER = 0x03;


    private final static short MOVE_TO_LOCATION = 0x05;
    private final static short ENTER_WORLD = 0x06;
    private final static short ACTION = 0x07;
    private final static short REQUEST_DIALOG = 0x08;

    public static void HandlePacket(ClientListenerThread clientListenerThread, byte [] packet)
    {
        short packetID = (short)(((packet[1] & 0xFF) << 8) | (packet[0] & 0xFF));
        switch (packetID)
        {
            case REQUEST_CONNECT_TO_GAME_SERVER:
                new RequestConnectToGameServer(clientListenerThread,packet);
                break;
            case CHARACTER_SELECTED:
                new CharacterSelected(clientListenerThread,packet);
                break;
            case REQUEST_CREATE_CHARACTER:
                new RequestCreateCharacter(clientListenerThread,packet);
                break;
            case MOVE_TO_LOCATION:
                new MoveToLocation(clientListenerThread,packet);
                break;
            case ENTER_WORLD:
                new EnterWorld(clientListenerThread, packet);
                break;
            case ACTION:
                new Action(clientListenerThread,packet);
                break;
            case REQUEST_DIALOG:
                new RequestDialog(clientListenerThread,packet);
                break;
        }
    }
}
