import com.gameserver.instance.DataEngine;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.PlaySound;
import com.gameserver.packet.game2client.SystemMessage;
import com.gameserver.template.quest.Quest;
import com.gameserver.template.quest.QuestProgression;
import com.gameserver.template.quest.QuestRewardType;

import java.util.HashMap;
import java.util.Map;

public class Q2_HelpWatcher extends Quest {

    private final int QUEST_ID = 2;
    private final String QUEST_NAME = "Help Watcher";
    private final int START_NPC_ID = 103;
    private final int QUEST_TYPE = 1;
    private final Map<QuestRewardType, Integer> questRewards = new HashMap<>();
    private final Map<Integer, Integer> questItemRewards = new HashMap<>();

    public Q2_HelpWatcher() {
        questRewards.put(QuestRewardType.EXPERIENCE, 110);
        questRewards.put(QuestRewardType.SKILL_POINTS, 210);

        questItemRewards.put(2, 1);
    }

    @Override
    public int getQuestId() {
        return QUEST_ID;
    }

    @Override
    public int getStartNpcId() {
        return START_NPC_ID;
    }

    @Override
    public int getQuestType() {
        return QUEST_TYPE;
    }

    @Override
    public String getQuestName() {
        return QUEST_NAME;
    }

    @Override
    public Map<QuestRewardType, Integer> getQuestRewards() {
        return questRewards;
    }

    @Override
    public Map<Integer, Integer> getQuestItemsRewards() {
        return questItemRewards;
    }

    @Override
    public void onQuestTalk(PlayableCharacter pc, String ref, int object_id) {
        QuestProgression qp = pc.getQuestProgression(QUEST_ID);
        if(getNpcId(object_id) == START_NPC_ID)
        {
            switch (ref)
            {
                case "index":
                    if(qp == null || qp.getCurrentQuestState() == null)
                    {
                        prepareAndSendDialog(pc, getDialog("index.dialog"), object_id);
                    }
                    else {
                        switch (qp.getCurrentQuestState()) {
                            case "A1":
                                prepareAndSendDialog(pc, getDialog("A1.dialog"), object_id);
                                break;
                            case "A2":
                                pc.sendPacket(new PlaySound(PlaySound.Sounds.QUEST_COMPLETED));
                                pc.questCompleted(pc,qp);
                                for(Map.Entry<QuestRewardType, Integer> questReward : questRewards.entrySet())
                                {
                                    switch (questReward.getKey())
                                    {
                                        case EXPERIENCE:
                                            pc.addExperience(questReward.getValue());
                                            break;
                                        case SKILL_POINTS:
                                            //TODO: add skill points
                                            break;
                                    }
                                }
                                for(Map.Entry<Integer, Integer> questItemReward : questItemRewards.entrySet())
                                {
                                    pc.giveItem(DataEngine.getInstance().getItemById(questItemReward.getKey()), questItemReward.getValue());
                                }
                                prepareAndSendDialog(pc, getDialog("A2.dialog"), object_id, true);
                                break;
                            default:
                                prepareAndSendDialog(pc, getDialog("index.dialog"), object_id);
                                break;
                        }
                    }
                    break;
                case "A1":
                    if(qp == null || qp.getCurrentQuestState() == null) {
                        pc.sendPacket(new PlaySound(PlaySound.Sounds.QUEST_ACCEPTED));
                    }
                    updateProgression(pc, qp, "A1", new int[] {103,104}, 104);
                    prepareAndSendDialog(pc, getDialog("A1.dialog"), object_id, true);
                    break;

            }
        }
    }

    @Override
    public void onQuestKill(PlayableCharacter pc, int npc_id) {
        QuestProgression qp = pc.getQuestProgression(QUEST_ID);
        if(qp.getCurrentQuestState().equals("A1") && npc_id == 104)
        {
            Integer value = qp.getQuestVariable("kill_103");
            if(value == null)
            {
                value = 1;
            }
            if(value >= 4) {
                pc.sendPacket(new PlaySound(PlaySound.Sounds.QUEST_ACCEPTED));
                updateProgression(pc,qp, "A2", new int[103], 103);
            }
            else{
                pc.sendPacket(new PlaySound(PlaySound.Sounds.QUEST_NEW_STEP));
                qp.updateQuestVariable("kill_103", value + 1);
            }
        }
    }

    @Override
    public boolean isLastStep(PlayableCharacter pc, int npc_id)
    {
        QuestProgression qp = pc.getQuestProgression(QUEST_ID);
        if(qp == null || qp.getCurrentQuestState() == null)
            return false;

        return qp.getCurrentQuestState().equals("A2") && npc_id == START_NPC_ID;
    }
}
