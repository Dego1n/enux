/*
 * Author: Dego1n
 * 4.1.2020
 */

package com.gameserver.packet.game2client;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.ServerPackets;
import com.gameserver.template.quest.QuestProgression;

import java.util.List;

public class QuestList extends AbstractSendablePacket {
    private final List<QuestProgression> questProgressions;
    public QuestList(List<QuestProgression> questProgressions)
    {
        super();
        this.questProgressions = questProgressions;
        build();
    }

    private void build() {
        writeH(ServerPackets.QUEST_LIST);
        writeD(questProgressions.size());
        for(QuestProgression qp : questProgressions)
        {
            writeD(qp.getQuest().getQuestId());
            writeD(qp.getCompletedQuestStates().size());
            for(String completedState : qp.getCompletedQuestStates())
            {
                writeS(completedState);
            }
            writeS(qp.getCurrentQuestState());
        }

    }
}
