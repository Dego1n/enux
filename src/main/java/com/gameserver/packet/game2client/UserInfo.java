package com.gameserver.packet.game2client;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

import java.util.Optional;

public class UserInfo extends AbstractSendablePacket implements IServerPacket {

    private final PlayableCharacter _character;

    public UserInfo(PlayableCharacter character)
    {
        super();
        _character = character;
        build();
    }

    private void build() {

        writeH(ServerPackets.USER_INFO);
        writeD(_character.getObjectId());
        writeH(_character.getRace().getValue());
        writeH(_character.getCharacterClass().getValue());
        writeD(_character.getLocationX());
        writeD(_character.getLocationY());
        writeD(_character.getLocationZ());

        writeD(_character.getBaseStats().getCollisionHeight());
        writeD(_character.getBaseStats().getCollisionRadius());

        writeS(_character.getName());

        /* EquipInfo start */
        Optional.ofNullable(_character.getEquipInfo().getRightHand()).ifPresentOrElse( (rightHand) -> writeD(rightHand.getItemId()), () -> writeD(0));
        Optional.ofNullable(_character.getEquipInfo().getLeftHand()).ifPresentOrElse( (leftHand) -> writeD(leftHand.getItemId()), () -> writeD(0));
        Optional.ofNullable(_character.getEquipInfo().getHelmet()).ifPresentOrElse( (helmet) -> writeD(helmet.getItemId()), () -> writeD(0));
        Optional.ofNullable(_character.getEquipInfo().getUpperArmor()).ifPresentOrElse( (upperArmor) -> writeD(upperArmor.getItemId()), () -> writeD(0));
        Optional.ofNullable(_character.getEquipInfo().getLowerArmor()).ifPresentOrElse( (lowerArmor) -> writeD(lowerArmor.getItemId()), () -> writeD(0));
        Optional.ofNullable(_character.getEquipInfo().getGloves()).ifPresentOrElse( (gloves) -> writeD(gloves.getItemId()), () -> writeD(0));
        Optional.ofNullable(_character.getEquipInfo().getBoots()).ifPresentOrElse( (boots) -> writeD(boots.getItemId()), () -> writeD(0));
        Optional.ofNullable(_character.getEquipInfo().getBelt()).ifPresentOrElse( (belt) -> writeD(belt.getItemId()), () -> writeD(0));
        Optional.ofNullable(_character.getEquipInfo().getEarringFirst()).ifPresentOrElse( (earningFirst) -> writeD(earningFirst.getItemId()), () -> writeD(0));
        Optional.ofNullable(_character.getEquipInfo().getEarringSecond()).ifPresentOrElse( (earningSecond) -> writeD(earningSecond.getItemId()), () -> writeD(0));
        Optional.ofNullable(_character.getEquipInfo().getRingFirst()).ifPresentOrElse( (ringFirst) -> writeD(ringFirst.getItemId()), () -> writeD(0));
        Optional.ofNullable(_character.getEquipInfo().getRingSecond()).ifPresentOrElse( (ringSecond) -> writeD(ringSecond.getItemId()), () -> writeD(0));
        Optional.ofNullable(_character.getEquipInfo().getNecklace()).ifPresentOrElse( (necklace) -> writeD(necklace.getItemId()), () -> writeD(0));

        /* EquipInfo end */
    }
}
