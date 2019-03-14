package com.gameserver.model;

import com.gameserver.database.dao.spawn.SpawnDao;
import com.gameserver.database.entity.spawn.Spawn;
import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.DestroyActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class World {

    private static final Logger log = LoggerFactory.getLogger(World.class);

    private static World _instance;

    public static World getInstance()
    {
        if(_instance == null)
            _instance = new World();

        return _instance;
    }

    private List<BaseActor> actors;

    private World()
    {
        log.info("Initializing world");
        actors = new ArrayList<>();
        log.info("Spawning NPCS");
        log.info("Spawned {} NPCs",SpawnNpcs());

    }

    public int SpawnNpcs()
    {
        int count = 0;
        SpawnDao spawnDao = new SpawnDao();
        for(Spawn spawn : spawnDao.getAllSpawns())
        {
            actors.add(new NPCActor(spawn));
            count++;
        }

        return count;
    }

    public void addPlayerToWorld(PlayableCharacter player)
    {
        actors.add(player);
    }

    public void removePlayerFromWorld(PlayableCharacter player)
    {
        for(BaseActor pc : getActorsInRadius(player,10000))
        {
            if(pc instanceof PlayableCharacter)
                ((PlayableCharacter)pc).getClientListenerThread().sendPacket(new DestroyActor(player.getObjectId()));
        }
        actors.remove(player);
    }

    public List<BaseActor> getActorsInRadius(BaseActor character, float radius)
    {
        List<BaseActor> actorsInRaidus = new ArrayList<>();

        for(BaseActor actor : actors)
        {
            if (actor == character)
                continue;
            if(
                    Math.sqrt(
                        Math.pow((actor.getLocationX() - character.getLocationX()),2) +
                        Math.pow((actor.getLocationY() - character.getLocationY()),2) +
                        Math.pow((actor.getLocationZ() - character.getLocationZ()),2)
                    ) <= radius
            )
            {
                actorsInRaidus.add(actor);
            }
        }

        return actorsInRaidus;
    }

    public List<PlayableCharacter> getPlayableCharactersInRadius(BaseActor actor, float radius)
    {
        List<PlayableCharacter> characters = new ArrayList<>();
        for(BaseActor baseActor : getActorsInRadius(actor,radius))
        {
            if(baseActor instanceof PlayableCharacter)
                characters.add((PlayableCharacter) baseActor);
        }
        return characters;
    }

    public List<PlayableCharacter> getAllPlayers()
    {
        List<PlayableCharacter> list = new ArrayList<>();

        for(BaseActor actor : actors)
        {
            if(actor instanceof PlayableCharacter)
                list.add((PlayableCharacter)actor);
        }
        
        return list;
    }
    public BaseActor getActorByObjectId(int objectId)
    {
        for(BaseActor ba : actors)
        {
            if(ba.getObjectId() == objectId)
                return ba;
        }

        return null;
    }
}
