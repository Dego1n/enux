package com.gameserver.packet.game2client;

import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.ServerPackets;

public class PlaySound extends AbstractSendablePacket {
    public enum Sounds {
        INVENTORY_OPEN(1),
        ITEM_PLACE(2),
        LEVEL_UP(3),
        QUEST_ACCEPTED(4),
        QUEST_COMPLETED(5),
        QUEST_NEW_STEP(6)
        ;

        private int numVal;

        Sounds(int numVal) {
            this.numVal = numVal;
        }
        public int getNumericValue()
        {
            return numVal;
        }
    }

    private Sounds selectedSound;
    public PlaySound(Sounds sound)
    {
        super();
        selectedSound = sound;
        build();
    }

    private void build() {
        writeH(ServerPackets.PLAY_SOUND);
        writeD(selectedSound.getNumericValue());
    }
}
