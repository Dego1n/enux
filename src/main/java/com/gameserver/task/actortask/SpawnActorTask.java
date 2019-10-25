package com.gameserver.task.actortask;

import com.gameserver.model.World;
import com.gameserver.model.actor.NPCActor;

import java.util.TimerTask;

public class SpawnActorTask extends TimerTask {

    private NPCActor actor;

    public SpawnActorTask(NPCActor actor)
    {
        this.actor = actor;
    }
    @Override
    public void run() {
        World.getInstance().spawnNpcAndBroadcast(this.actor);
    }
}
