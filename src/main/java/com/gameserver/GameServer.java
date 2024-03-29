package com.gameserver;

import com.gameserver.config.Config;
import com.gameserver.database.DBDatastore;
import com.gameserver.database.presets.CharacterPresets;
import com.gameserver.geodata.GeoEngine;
import com.gameserver.instance.CommandEngine;
import com.gameserver.instance.DataEngine;
import com.gameserver.model.World;
import com.gameserver.network.AuthServerSocket;
import com.gameserver.network.instance.GameServerSocketInstance;
import com.gameserver.tick.GameTickController;

import java.io.IOException;

class GameServer {
    public static void main(String[] args)
    {
        Config.Load();
        DBDatastore.getInstance();
        DataEngine.getInstance();
        /* PRESETS_START */
        CharacterPresets.Load();
        /* PRESETS_END */

        //GeoEngine.getInstance();
        GameTickController.init();
        World.getInstance();
        CommandEngine.getInstance();
        try {
            new AuthServerSocket().EstablishConnection();
            GameServerSocketInstance.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try
        {
            Thread.sleep( 2678400000L ); //month
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}
