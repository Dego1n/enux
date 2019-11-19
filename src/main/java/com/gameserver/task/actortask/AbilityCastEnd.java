package com.gameserver.task.actortask;

import com.gameserver.model.World;
import com.gameserver.model.ability.Ability;
import com.gameserver.model.actor.BaseActor;

import java.util.TimerTask;

public class AbilityCastEnd extends TimerTask {
    private final BaseActor actor;
    private final BaseActor target;
    private final Ability ability;

    public AbilityCastEnd(BaseActor actor, BaseActor target, Ability ability)
    {
        this.actor = actor;
        this.target = target;
        this.ability = ability;
    }
    @Override
    public void run() {
        actor.onAbilityCastEnd(this);
    }

    public BaseActor getActor() {
        return actor;
    }

    public BaseActor getTarget() {
        return target;
    }

    public Ability getAbility() {
        return ability;
    }
}