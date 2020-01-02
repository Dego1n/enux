package com.gameserver.instance.loader;

import com.gameserver.config.Config;
import com.gameserver.scripting.engine.JavaScriptingEngine;
import com.gameserver.template.quest.Quest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestsLoader {
    public static List<Quest> LoadQuests(){

        List<Quest> quests = new ArrayList<>();


        Map<String, String> questFolders = new HashMap<>();
        File questFolder = new File(Config.DATAPACK_PATH + "scripts/quests/");
        for(File quest : questFolder.listFiles())
        {
            questFolders.put(quest.getName(), quest.getAbsolutePath().toString());
        }
        for(Map.Entry<String,String> questData : questFolders.entrySet())
        {
            String questName = questData.getKey();
            Quest quest = JavaScriptingEngine.getInstance().compileQuestScript(questName);
            quests.add(quest);
        }
        return quests;
    }
}
