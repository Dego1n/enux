package com.gameserver.packet.game2client;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class PCActorInfo extends AbstractSendablePacket implements IServerPacket {

    private PlayableCharacter actor;

    public PCActorInfo(PlayableCharacter actor)
    {
        super();
        this.actor = actor;
        build();
    }

    private void build() {
        writeH(ServerPackets.PC_ACTOR_INFO);
        writeD(actor.getObjectId());
        writeD(actor.getTemplateId());

        writeD(actor.getLocationX());
        writeD(actor.getLocationY());
        writeD(actor.getLocationZ());

        writeD(actor.getCollisionHeight());
        writeD(actor.getCollisionRadius());

        writeH(actor.isFriendly() ? 1 : 0);

        writeD((int)actor.getCurrentHp());
        writeD((int)actor.getMaxHp());

        writeS(actor.getName());

        /* EquipInfo start */

        writeD(actor.getEquipInfo().getRightHand().getItemId());

        /* EquipInfo end */

    }
}
