
import com.gameserver.template.Quest;


public class Q1_ReportToTheWatcher implements Quest {

    private final int QUEST_ID = 1;
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
}
