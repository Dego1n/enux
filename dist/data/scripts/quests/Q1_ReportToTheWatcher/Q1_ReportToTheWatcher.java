
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.SystemMessage;
import com.gameserver.template.quest.Quest;
import com.gameserver.template.quest.QuestProgression;

import java.util.ArrayList;


public class Q1_ReportToTheWatcher extends Quest {

    private final int QUEST_ID = 1;
    private final String QUEST_NAME = "Report to the Watcher";
    private final int START_NPC_ID = 100;
    private final int QUEST_TYPE = 1;

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
                        updateProgression(pc, qp, ref, new int[] {100,103});
                        prepareAndSendDialog(pc, getDialog("A1.dialog"), object_id);
                }
                break;
            case 103:
                if ("index".equals(ref)) {
                    if (qp.getCurrentQuestState().equals("A1")) {
                        prepareAndSendDialog(pc, getDialog("A2.dialog"), object_id);
                        pc.questCompleted(qp);
                        pc.sendPacket(new SystemMessage("You successfully completed quest " + QUEST_NAME));
                    }
                }
                break;
        }
    }
}
