package com.gameserver.model.actor;

import com.gameserver.config.Config;
import com.gameserver.database.entity.spawn.Spawn;
import com.gameserver.instance.DataEngine;
import com.gameserver.scripting.ai.npc.NpcAi;
import com.gameserver.template.NPC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NPCActor extends BaseActor {

    private static final Logger log = LoggerFactory.getLogger(NPCActor.class);

    private NpcAi npcAi;

    public NPCActor(Spawn spawn)
    {
        super();
        NPC npc = DataEngine.getInstance().getNPCById(spawn.getActorId());
        id = npc.getId();
        setLocationX(spawn.getLocationX());
        setLocationY(spawn.getLocationY());
        setLocationZ(spawn.getLocationZ());
        setName(npc.getName());
        //setRace(npcActor.getRace());
        setTemplateId(npc.getTemplateId());

        String path = Config.DATAPACK_PATH + "scripts/ai/npc/"+id;

        if(new File(path).exists())
        {
            npcAi = new NpcAi(this,path);
        }
        else
        {
            log.warn("Not found AI script for npc id: {}",id);
        }

    }

    public NpcAi getNpcAi() {
        return npcAi;
    }
}
