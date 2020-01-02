package com.gameserver.packet.game2client;

import com.gameserver.model.actor.playable.equip.EquipInfo;
import com.gameserver.model.item.Item;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

import java.util.List;
import java.util.Optional;

public class Inventory extends AbstractSendablePacket implements IServerPacket {

    private final List<Item> _items;
    private final EquipInfo _equipInfo;

    public Inventory(List<Item> items, EquipInfo equipInfo)
    {
        super();
        _items = items;
        _equipInfo = equipInfo;
        build();
    }

    private void build() {
        writeH(ServerPackets.INVENTORY);

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

        writeD(_items.size()); //item size
        for(Item item : _items) {
            writeD(item.getObjectId());
            writeD(item.getItemId()); //item id
            writeD(item.getCount());
        }
    }
}
