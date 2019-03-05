package com.gameserver.model.actor;

public class NPCActor extends BaseActor {


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
    }

}
