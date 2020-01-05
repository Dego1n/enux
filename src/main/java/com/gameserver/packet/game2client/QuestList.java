/*
 * Author: Dego1n
 * 4.1.2020
 */

package com.gameserver.packet.game2client;

import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.ServerPackets;
import com.gameserver.template.quest.Quest;
import com.gameserver.template.quest.QuestProgression;
import com.gameserver.template.quest.QuestRewardType;

import java.util.List;
import java.util.Map;

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
            Quest quest = qp.getQuest();
            writeD(qp.getQuest().getQuestId());
            writeD(qp.getCompletedQuestStates().size());
            for(String completedState : qp.getCompletedQuestStates())
            {
                writeS(completedState);
            }
            writeS(qp.getCurrentQuestState());
            Map<QuestRewardType, Integer> questRewards = quest.getQuestRewards();
            Map<Integer, Integer> questItemRewards = quest.getQuestItemsRewards();
            writeD(questRewards.size());
            for(Map.Entry<QuestRewardType, Integer> questReward : questRewards.entrySet())
            {
                writeD(questReward.getKey().getNumVal());
                writeD(questReward.getValue());
            }
            writeD(questItemRewards.size());
            for(Map.Entry<Integer, Integer> questItemReward : questItemRewards.entrySet()) {
                writeD(questItemReward.getKey());
                writeD(questItemReward.getValue());
            }
        }

    }
}
