/*
 * Author: Dego1n
 * 2.1.2020
 */

package com.gameserver.template.quest;


import com.gameserver.config.Config;
import com.gameserver.instance.DataEngine;
import com.gameserver.model.World;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.QuestList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public abstract class Quest {
    public abstract int getQuestId();

    public abstract int getStartNpcId();

    public abstract int getQuestType();

    public abstract String getQuestName();

    public abstract Map<QuestRewardType, Integer> getQuestRewards();

    public abstract Map<Integer, Integer> getQuestItemsRewards();

    public abstract void onQuestTalk(PlayableCharacter pc, String ref, int object_id);

    public abstract void onQuestKill(PlayableCharacter pc, int npc_id);

    public void updateProgression(PlayableCharacter pc, QuestProgression qp, String questState, int[] npcIds)
    {
        if(qp == null)
        {
            qp = new QuestProgression();
            qp.setQuest(DataEngine.getInstance().getQuestById(getQuestId()));
            pc.addQuestProgression(qp);
        }
        if(qp.getCurrentQuestState() != null)
        {
            qp.addCompletedQuestState(qp.getCurrentQuestState());
        }
        qp.setCurrentQuestState(questState);
        qp.setNpcIds(npcIds);
        pc.sendPacket(new QuestList(pc.getQuestProgressions()));
    }

    public String getDialog(String name)
    {
        try {
            return new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/" + Config.DATAPACK_PATH + "scripts/quests/" + getClass().getName() + "/dialogs/"+name)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void prepareAndSendDialog(PlayableCharacter pc, String dialog, int object_id)
    {
        dialog = dialog.replace("$npc_id", String.valueOf(object_id));
        dialog = dialog.replace("$quest_id", String.valueOf(getQuestId()));
        pc.sendDialog(dialog);
    }

    protected int getNpcId(int object_id)
    {
        NPCActor npcActor = (NPCActor) World.getInstance().getActorByObjectId(object_id);
        if(npcActor != null)
        {
            return npcActor.getNpcId();
        }
        return -1;
    }

}
