package com.gameserver.task.actortask;

import com.gameserver.model.World;
import com.gameserver.model.actor.NPCActor;

import java.util.TimerTask;

public class RemoveActorTask extends TimerTask {

    private final NPCActor actor;

    public RemoveActorTask(NPCActor actor)
    {
        this.actor = actor;
    }
    @Override
    public void run() {
        World.getInstance().removeActorFromWorld(this.actor);
    }
}
