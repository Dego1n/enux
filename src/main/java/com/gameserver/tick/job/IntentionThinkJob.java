package com.gameserver.tick.job;

import com.gameserver.model.actor.BaseActor;

import java.util.ArrayList;
import java.util.List;

public class IntentionThinkJob extends AbstractTickJob implements ITickJob{

    private final List<BaseActor> _actorList;

    public IntentionThinkJob()
    {
        this._actorList = new ArrayList<>();
    }

    @Override
    public void runJob() {
        for(BaseActor actor :_actorList ) {
            System.out.println("Executed intentionThink tick for actor: "+actor.getName());
            actor.getActorIntention().intentionThink();
        }
    }

    public void addToThink(BaseActor actor)
    {
        _actorList.add(actor);
    }

    public void deleteFromThink(BaseActor actor)
    {
        _actorList.remove(actor);
    }
}