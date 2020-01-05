
import com.gameserver.instance.DataEngine;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.model.item.Item;
import com.gameserver.packet.game2client.PlaySound;
import com.gameserver.packet.game2client.SystemMessage;
import com.gameserver.template.quest.Quest;
import com.gameserver.template.quest.QuestProgression;
import com.gameserver.template.quest.QuestRewardType;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Q1_ReportToTheWatcher extends Quest {

    private final int QUEST_ID = 1;
    private final String QUEST_NAME = "Report to the Watcher";
    private final int START_NPC_ID = 100;
    private final int QUEST_TYPE = 1;
    private final Map<QuestRewardType, Integer> questRewards = new HashMap<>();
    private final Map<Integer, Integer> questItemRewards = new HashMap<>();
    public Q1_ReportToTheWatcher() {
        questRewards.put(QuestRewardType.EXPERIENCE, 100);
        questRewards.put(QuestRewardType.SKILL_POINTS, 200);

        questItemRewards.put(23, 80);
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
        switch(getNpcId(object_id))
        {
            case 100:
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
                                default:
                                    prepareAndSendDialog(pc, getDialog("index.dialog"), object_id);
                                    break;
                            }
                        }
                        break;
                    case "A1":
                        updateProgression(pc, qp, "A1", new int[] {100,103});
                        pc.sendPacket(new PlaySound(PlaySound.Sounds.QUEST_ACCEPTED));
                        prepareAndSendDialog(pc, getDialog("A1.dialog"), object_id);
                }
                break;
            case 103:
                if ("index".equals(ref)) {
                    if (qp.getCurrentQuestState().equals("A1")) {
                        prepareAndSendDialog(pc, getDialog("A2.dialog"), object_id);
                        pc.questCompleted(pc, qp);
                        pc.sendPacket(new PlaySound(PlaySound.Sounds.QUEST_COMPLETED));
                        pc.sendPacket(new SystemMessage("You successfully completed quest " + QUEST_NAME));
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
                    }
                }
                break;
        }
    }

    @Override
    public void onQuestKill(PlayableCharacter pc, int npc_id) {

    }
}
