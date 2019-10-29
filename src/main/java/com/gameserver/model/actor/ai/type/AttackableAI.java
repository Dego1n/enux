package com.gameserver.model.actor.ai.type;

import com.gameserver.model.actor.BaseActor;

public class AttackableAI extends AbstractAI {

    public AttackableAI(BaseActor actor) {
        super(actor);
    }

    @Override
    public void onAttacked(BaseActor source) {
        moveToActor(source,100);
    }
}
