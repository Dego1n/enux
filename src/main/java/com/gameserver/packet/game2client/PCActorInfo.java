package com.gameserver.packet.game2client;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.model.actor.playable.equip.EquipInfo;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

import java.util.Optional;

public class PCActorInfo extends AbstractSendablePacket implements IServerPacket {

    private final PlayableCharacter actor;

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

        EquipInfo _equipInfo = actor.getEquipInfo();
        Optional.ofNullable(_equipInfo.getRightHand()).ifPresentOrElse( (rightHand) -> { writeD(rightHand.getItemId()); writeD(rightHand.getObjectId());},  () -> { writeD(0); writeD(0); } );
        Optional.ofNullable(_equipInfo.getLeftHand()).ifPresentOrElse( (leftHand) -> { writeD(leftHand.getItemId()); writeD(leftHand.getObjectId());},  () -> { writeD(0); writeD(0); } );
        Optional.ofNullable(_equipInfo.getHelmet()).ifPresentOrElse( (helmet) -> { writeD(helmet.getItemId()); writeD(helmet.getObjectId());},  () -> { writeD(0); writeD(0); } );
        Optional.ofNullable(_equipInfo.getUpperArmor()).ifPresentOrElse( (upperArmor) -> { writeD(upperArmor.getItemId()); writeD(upperArmor.getObjectId());},  () -> { writeD(0); writeD(0); } );
        Optional.ofNullable(_equipInfo.getLowerArmor()).ifPresentOrElse( (lowerArmor) -> { writeD(lowerArmor.getItemId()); writeD(lowerArmor.getObjectId());},  () -> { writeD(0); writeD(0); } );
        Optional.ofNullable(_equipInfo.getGloves()).ifPresentOrElse( (gloves) -> { writeD(gloves.getItemId()); writeD(gloves.getObjectId());},  () -> { writeD(0); writeD(0); } );
        Optional.ofNullable(_equipInfo.getBoots()).ifPresentOrElse( (boots) -> { writeD(boots.getItemId()); writeD(boots.getObjectId());},  () -> { writeD(0); writeD(0); } );
        Optional.ofNullable(_equipInfo.getBelt()).ifPresentOrElse( (belt) -> { writeD(belt.getItemId()); writeD(belt.getObjectId());},  () -> { writeD(0); writeD(0); } );
        Optional.ofNullable(_equipInfo.getEarringFirst()).ifPresentOrElse( (earningFirst) -> { writeD(earningFirst.getItemId()); writeD(earningFirst.getObjectId());},  () -> { writeD(0); writeD(0); } );
        Optional.ofNullable(_equipInfo.getEarringSecond()).ifPresentOrElse( (earningSecond) -> { writeD(earningSecond.getItemId()); writeD(earningSecond.getObjectId());},  () -> { writeD(0); writeD(0); } );
        Optional.ofNullable(_equipInfo.getRingFirst()).ifPresentOrElse( (ringFirst) -> { writeD(ringFirst.getItemId()); writeD(ringFirst.getObjectId());},  () -> { writeD(0); writeD(0); } );
        Optional.ofNullable(_equipInfo.getRingSecond()).ifPresentOrElse( (ringSecond) -> { writeD(ringSecond.getItemId()); writeD(ringSecond.getObjectId());},  () -> { writeD(0); writeD(0); } );
        Optional.ofNullable(_equipInfo.getNecklace()).ifPresentOrElse( (necklace) -> { writeD(necklace.getItemId()); writeD(necklace.getObjectId());},  () -> { writeD(0); writeD(0); } );
        /* EquipInfo end */

    }
}
