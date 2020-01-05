package com.gameserver.template.quest;

public enum QuestRewardType {
    EXPERIENCE(1),
    SKILL_POINTS(2),
    CHOICE_REWARD(3);

    private int numVal;

    QuestRewardType(int numVal)
    {
        this.numVal = numVal;
    }
    public int getNumVal()
    {
        return numVal;
    }
}
