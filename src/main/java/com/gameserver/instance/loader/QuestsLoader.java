package com.gameserver.instance.loader;

import com.gameserver.config.Config;
import com.gameserver.template.Quest;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

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
        try {
            Files.walk(Paths.get(Config.DATAPACK_PATH + "scripts/quests/")).filter(Files::isDirectory).forEach(folder -> {
                questFolders.put(folder.getFileName().toString(), folder.toAbsolutePath().toString());
            });
            //Удаляем первый элемент - это root папка
            questFolders.remove(questFolders.keySet().stream().findFirst().get());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        for(Map.Entry<String,String> questData : questFolders.entrySet())
        {
            String questName = questData.getKey();
            LuaValue script = JsePlatform.standardGlobals();

            script.get("dofile").call(LuaValue.valueOf(questData.getValue()) + "/" + questData.getKey() + ".lua");

            int questId = script.get("QuestId").toint();
            int startNpcId = script.get("StartNpcId").toint();
            int questType = script.get("QuestType").toint();

            Quest quest = new Quest();
            quest.setQuestId(questId);
            quest.setStartNpcId(startNpcId);
            quest.setQuestType(questType);
            quest.setScript(script);

            quests.add(quest);

        }
        return quests;
    }
}
