package com.gameserver.task.actortask;

import com.gameserver.model.actor.BaseActor;

import java.util.TimerTask;

public class AttackTask extends TimerTask {

    private BaseActor _pc;
    private BaseActor _target;

    public AttackTask(BaseActor pc, BaseActor target)
    {
        _pc = pc;
        _target = target;
    }

    @Override
    public void run() {
        _pc.attack(_target);
    }
}
