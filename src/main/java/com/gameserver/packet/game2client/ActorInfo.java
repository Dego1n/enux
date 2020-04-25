package com.gameserver.packet.game2client;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

public class ActorInfo extends AbstractSendablePacket implements IServerPacket {

    private final BaseActor actor;
    private final PlayableCharacter playableCharacter;

    public ActorInfo(BaseActor actor, PlayableCharacter playableCharacter)
    {
        super();
        this.actor = actor;
        this.playableCharacter = playableCharacter;
        build();
    }

    private void build() {
        writeH(ServerPackets.ACTOR_INFO);
        writeD(actor.getObjectId());
        writeD(actor.getTemplateId());

        writeD(actor.getLocationX());
        writeD(actor.getLocationY());
        writeD(actor.getLocationZ());

        writeD(actor.getCollisionHeight());
        writeD(actor.getCollisionRadius());

        writeH(actor.isFriendly());

        writeD((int)actor.getCurrentHp());
        writeD((int)actor.getMaxHp());

        writeS(actor.getName());
        writeD((int)actor.getStats().getAttackSpeed());
        writeD((int)actor.getStats().getMoveSpeed());

        if(actor instanceof NPCActor) {
            if(((NPCActor) actor).getNpcAi().hasLastStepForCharacter(playableCharacter))
            {
                writeH(2);
            }
            else if(((NPCActor) actor).getNpcAi().hasQuestsForCharacter(playableCharacter))
            {
                writeH(1);
            }
            else {
                writeH(0);
            }
        }
        else {
            writeH(0);
        }
    }
}
