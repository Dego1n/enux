/*
 * Author: Dego1n
 * 4.12.2019
 */

package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class SpecialActorInfo extends AbstractSendablePacket implements IServerPacket {

    private final BaseActor actor;

    public SpecialActorInfo(BaseActor actor)
    {
        super();
        this.actor = actor;
        build();
    }

    private void build() {
        writeH(ServerPackets.SPECIAL_ACTOR_INFO);

        writeS(actor.getName() + "("+actor.getClass().getSimpleName()+" ObjId#"+actor.getObjectId()+")");
        writeD(actor.getLevel());

        writeD((int)actor.getCurrentHp()); //TODO: float when client support it
        writeD((int)actor.getMaxHp()); //TODO: float when client support it
        writeD((int)actor.getCurrentMp()); //TODO: float when client support it
        writeD((int)actor.getMaxMp()); //TODO: float when client support it

        writeD((int) actor.getStats().getPhysicalAttack());
        writeD((int) actor.getStats().getPhysicalDefence());
    }
}
