package com.gameserver.model.actor;

import com.gameserver.scripting.ai.npc.NpcAi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NPCActor extends BaseActor {

    private static final Logger log = LoggerFactory.getLogger(NPCActor.class);

    private NpcAi npcAi;

    public NPCActor(com.gameserver.database.entity.actor.NPCActor npcActor)
    {
        super();
        id = npcActor.getId();
        setLocationX(npcActor.getLocationX());
        setLocationY(npcActor.getLocationY());
        setLocationZ(npcActor.getLocationZ());
        setName(npcActor.getName());
        setRace(npcActor.getRace());
        setTemplateId(npcActor.getTemplateId());

        String path = "./scripts/ai/npc/"+id;

        if(Files.isDirectory(Paths.get("./dist/config")))
        {
            //From Editor
            path = "./dist/scripts/ai/npc/"+id;
        }

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
