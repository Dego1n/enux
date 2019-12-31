package com.gameserver.template;

import org.luaj.vm2.LuaValue;

public class Quest {
    private int questId;
    private int startNpcId;
    private int questType;
    private LuaValue script;

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public int getStartNpcId() {
        return startNpcId;
    }

    public void setStartNpcId(int startNpcId) {
        this.startNpcId = startNpcId;
    }

    public int getQuestType() {
        return questType;
    }

    public void setQuestType(int questType) {
        this.questType = questType;
    }

    public LuaValue getScript() {
        return script;
    }

    public void setScript(LuaValue script) {
        this.script = script;
    }
}
