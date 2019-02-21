package com.gameserver;

import com.gameserver.config.Config;
import com.gameserver.database.presets.CharacterPresets;
import com.gameserver.network.AuthServerSocket;
import com.gameserver.network.instance.GameServerSocketInstance;

import java.io.IOException;

public class GameServer {
    public static void main(String[] args)
    {

        Config.Load();
        CharacterPresets.Load();

        try {
            new AuthServerSocket().EstablishConnection();
            GameServerSocketInstance.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try
        {
            Thread.sleep( 6000000 );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}
