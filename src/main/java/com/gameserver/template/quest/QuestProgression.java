/*
 * Author: Dego1n
 * 2.1.2020
 */

package com.gameserver.template.quest;

import com.gameserver.model.actor.PlayableCharacter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestProgression {

    private Quest quest;
    private List<String> completedQuestStates;
    private String currentQuestState;
    private int[] npcIds;
    private Map<String, Integer> questVariables = new HashMap<>();

    public QuestProgression() {
        completedQuestStates = new ArrayList<>();
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public String getCurrentQuestState() {
        return currentQuestState;
    }

    public void setCurrentQuestState(String currentQuestState) {
        this.currentQuestState = currentQuestState;
    }

    public List<String> getCompletedQuestStates()
    {
        return completedQuestStates;
    }

    public void addCompletedQuestState(String state)
    {
        completedQuestStates.add(state);
    }

    public int[] getNpcIds() {
        return npcIds;
    }

    public void setNpcIds(int[] npcIds) {
        this.npcIds = npcIds;
    }

    public Integer getQuestVariable(String variable)
    {
        return questVariables.get(variable);
    }
    public void updateQuestVariable(String variable, int value)
    {
        questVariables.put(variable,value);
    }
}
