package com.gameserver.task.actortask;

import com.gameserver.database.entity.spawn.Spawn;
import com.gameserver.model.World;

import java.util.TimerTask;

public class SpawnActorTask extends TimerTask {

    private Spawn spawn;

    public SpawnActorTask(Spawn spawn)
    {
        this.spawn = spawn;
    }
    @Override
    public void run() {
        World.getInstance().spawnNpcAndBroadcast(spawn);
    }
}
