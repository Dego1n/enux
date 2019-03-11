package com.gameserver;

import com.gameserver.config.Config;
import com.gameserver.database.dao.actor.CharacterDao;
import com.gameserver.database.presets.CharacterPresets;
import com.gameserver.database.presets.NPCPresets;
import com.gameserver.model.World;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.network.AuthServerSocket;
import com.gameserver.network.instance.GameServerSocketInstance;

import java.io.IOException;

public class GameServer {
    public static void main(String[] args)
    {

        Config.Load();
        /* PRESETS_START */
        CharacterPresets.Load();
        NPCPresets.Load();
        /* PRESETS_END */

        World.getInstance();
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
