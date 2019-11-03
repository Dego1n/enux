package com.gameserver.task.actortask;

import com.gameserver.model.World;
import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;

import java.util.TimerTask;

public class SpawnActorTask extends TimerTask {

    private final BaseActor actor;

    public SpawnActorTask(BaseActor actor)
    {
        this.actor = actor;
    }
    @Override
    public void run() {
        if(actor instanceof NPCActor) {
            World.getInstance().spawnNpcAndBroadcast((NPCActor) this.actor);
        }
    }
}
