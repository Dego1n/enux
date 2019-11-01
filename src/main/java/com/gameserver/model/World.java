package com.gameserver.model;

import com.gameserver.config.Config;
import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.ActorInfo;
import com.gameserver.packet.game2client.DestroyActor;
import com.gameserver.tick.GameTickController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private int SpawnNpcs()
    {
        try {
            List<File> spawnFiles = Files.walk(Paths.get(Config.DATAPACK_PATH + "npc/spawn")).filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
            for(File f : spawnFiles)
            {
                String npcYaml = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())), StandardCharsets.UTF_8);
                Yaml yaml = new Yaml();
                ArrayList<Map<String, Object>> spawns = yaml.load(npcYaml);

                for(Map<String, Object> entry : spawns)
                {
                    NPCActor npc = new NPCActor((int)entry.get("npc_id"),
                            (int)entry.get("x"),
                            (int)entry.get("y"),
                            (int)entry.get("z")
                    );
                    spawnNpc(npc);
                }
            }

        } catch (IOException e) {
            log.error("Can't read npc yaml file");
            log.error(e.getMessage());
            return -1;
        }

        return actors.size();


    }

    private void spawnNpc(NPCActor npc)
    {
        actors.add(npc);
    }

    public void spawnNpcAndBroadcast(NPCActor npc)
    {
        npc.onRespawn();
        actors.add(npc);
        for(PlayableCharacter pc : getPlayableCharactersInRadius(npc,100000))
        {
            pc.sendPacket(new ActorInfo(npc));
        }
    }

    public void addPlayerToWorld(PlayableCharacter player)
    {
        actors.add(player);
    }

    public void removeActorFromWorld(BaseActor actor)
    {
        actors.remove(actor);
    }

    public void removePlayerFromWorld(PlayableCharacter player)
    {
        player.getActorIntention().setIntention(null);
        GameTickController.getInstance().onPlayerDisconnect(player);
        for(BaseActor pc : getActorsInRadius(player,100000))
        {
            if(pc instanceof PlayableCharacter)
                ((PlayableCharacter)pc).sendPacket(new DestroyActor(player.getObjectId()));
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
