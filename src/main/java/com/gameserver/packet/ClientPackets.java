package com.gameserver.packet;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.client2game.MoveToLocation;
import com.gameserver.packet.client2game.RequestConnectToGameServer;

public class ClientPackets {

    public static void HandlePacket(ClientListenerThread clientListenerThread, byte [] packet)
    {
        short packetID = (short)(((packet[1] & 0xFF) << 8) | (packet[0] & 0xFF));
        switch (packetID)
        {
            case 0x01:
                new RequestConnectToGameServer(clientListenerThread,packet);
                break;

            case 0x05:
                new MoveToLocation(clientListenerThread,packet);
                break;
        }
    }
}
