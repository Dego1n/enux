package com.gameserver.task.actortask;

import com.gameserver.model.World;
import com.gameserver.model.actor.BaseActor;

import java.util.TimerTask;

public class RemoveActorTask extends TimerTask {

    private final BaseActor actor;

    public RemoveActorTask(BaseActor actor)
    {
        this.actor = actor;
    }
    @Override
    public void run() {
        World.getInstance().removeActorFromWorld(this.actor);
    }
}
