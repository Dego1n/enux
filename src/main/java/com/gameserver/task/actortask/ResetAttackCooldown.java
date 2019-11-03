package com.gameserver.task.actortask;

import com.gameserver.model.actor.BaseActor;

import java.util.TimerTask;

public class ResetAttackCooldown extends TimerTask {

    private final BaseActor _actor;
    public ResetAttackCooldown(BaseActor actor) {
        _actor = actor;
    }

    @Override
    public void run() {
        _actor.setCanAttack(true);
    }
}
