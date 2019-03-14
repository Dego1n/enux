package com.gameserver.instance;

import com.gameserver.config.Config;
import com.gameserver.template.NPC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataEngine {

    private static final Logger log = LoggerFactory.getLogger(DataEngine.class);

    private static DataEngine _instance;

    public static DataEngine getInstance()
    {
        if(_instance == null)
            _instance = new DataEngine();

        return _instance;
    }

    private List<NPC> npcList;

    DataEngine()
    {
        npcList = new ArrayList<>();

        log.info("Loaded {} NPC Data",LoadNPCData());
    }

    private int LoadNPCData()
    {
        int count = 0;
        String npcYaml;
        try {
            npcYaml = new String(Files.readAllBytes(Paths.get(Config.DATAPACK_PATH + "npc/npc.yaml")), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Can't read npc yaml file");
            log.error(e.getMessage());
            return -1;
        };

        Yaml yaml = new Yaml();
        Map<String, Object> object = yaml.load(npcYaml);
        for(Map.Entry<String, Object> entry : object.entrySet())
        {
            @SuppressWarnings("unchecked")
            Map<String, Object> npc =  (Map<String, Object>)entry.getValue();

            int id = (int)npc.get("id");
            String name = (String)npc.get("name");
            int templateId = (int)npc.get("template_id");
            boolean isFriendly = (boolean)npc.get("is_friendly");

            npcList.add(new NPC(id,templateId,name,isFriendly));
            count++;
        }

        return count;
    }

    public NPC getNPCById(int id) {
        for(NPC npc : npcList)
        {
            if(npc.getId() == id)
                return npc;
        }
        return null;
    }
}
