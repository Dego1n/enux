package com.gameserver.packet.game2client;

import com.gameserver.model.ability.Ability;
import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class UseAbility extends AbstractSendablePacket implements IServerPacket {

    private final BaseActor actor;
    private final BaseActor target;
    private final Ability ability;
    private final float castTime;

    public UseAbility(BaseActor actor, BaseActor target, Ability ability, float castTime) {
        super();

        this.actor = actor;
        this.target = target;
        this.ability = ability;
        this.castTime = castTime;
        build();

    }

    private void build() {
        writeH(ServerPackets.USE_ABILITY);

        writeD(actor.getObjectId());
        writeD(target.getObjectId());
        writeD(ability.getId());
        writeD((int)(castTime*10));
    }
}
