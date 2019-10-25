package com.gameserver;

import com.gameserver.config.Config;
import com.gameserver.database.presets.CharacterPresets;
import com.gameserver.database.presets.SpawnPresets;
import com.gameserver.instance.CommandEngine;
import com.gameserver.instance.DataEngine;
import com.gameserver.model.World;
import com.gameserver.network.AuthServerSocket;
import com.gameserver.network.instance.GameServerSocketInstance;
import com.gameserver.tick.GameTickController;

import java.io.IOException;

public class GameServer {
    public static void main(String[] args)
    {
        Config.Load();
        DataEngine.getInstance();
        /* PRESETS_START */
        CharacterPresets.Load();
        SpawnPresets.Load();
        /* PRESETS_END */

        GameTickController.init();
        World.getInstance();
        CommandEngine.getInstance();
//        ((NPCActor)World.getInstance().getActorByObjectId(1)).getNpcAi().onTalk(new PlayableCharacter(null, new CharacterDao().getCharacterByName("PlayTest")));
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
