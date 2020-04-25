package com.gameserver.scripting.ai.npc;

import com.gameserver.config.Config;
import com.gameserver.model.World;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.template.quest.Quest;
import com.gameserver.template.quest.QuestProgression;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class NpcAi {

    private int npc_id;
    protected int object_id;

    public NpcAi(int npc_id)
    {
        this.npc_id = npc_id;
    }


    public void onTalk(PlayableCharacter character) {
        String dialog = getDialog("index.dialog");
        if (dialog.length() == 0)
        {
            dialog = "I have nothing to say to you";
        }
        dialog += "\n<button type=\"npc_dialog\" object_id=\""+object_id+"\" ref=\"quest\">Quest</>";
        prepareDialogAndSend(character, dialog);;
    }
    public String getDialog(String name)
    {
        String filePath = System.getProperty("user.dir") + "/" + Config.DATAPACK_PATH + "scripts/ai/npc/" + npc_id + "/dialogs/"+name;
        Path path = Paths.get(filePath);
        if(Files.exists(path)) {
            try {
                return new String(Files.readAllBytes(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    public void onTalk(PlayableCharacter character, String dialog)
    {
        switch (dialog)
        {
            case "index":
                onTalk(character);
                break;
            case "quest":
                onQuestTalk(character);
                break;
        }
    }

    public void prepareDialogAndSend(PlayableCharacter pc, String dialog)
    {
        dialog = dialog.replace("$npc_id", String.valueOf(object_id));
        pc.sendDialog(dialog);
    }

    public void requestDialog(PlayableCharacter character, String dialog)
    {
        onTalk(character,dialog);
    }

    public void setObjectId(int object_id) {
        this.object_id = object_id;
    }

    public void onQuestTalk(PlayableCharacter character)
    {
        NPCActor npcActor = (NPCActor) World.getInstance().getActorByObjectId(object_id);
        StringBuilder dialog = new StringBuilder();
        List<Quest> quests = new ArrayList<>(npcActor.getQuests());
        for(QuestProgression qp : character.getQuestProgressions())
        {
            if(!quests.contains(qp.getQuest()) && IntStream.of(qp.getNpcIds()).anyMatch(npcId -> npcId == this.npc_id))
                quests.add(qp.getQuest());
        }
        for(Quest quest : quests)
        {
            dialog.append("<button type=\"quest\" object_id=\""+object_id+"\" quest_id=\""+quest.getQuestId()+"\" ref=\"index\">"+quest.getQuestName()+"</>\n");
        }
        prepareDialogAndSend(character,dialog.toString());
    }

    public boolean hasQuestsForCharacter(PlayableCharacter pc)
    {
        NPCActor npcActor = (NPCActor) World.getInstance().getActorByObjectId(object_id);
        List<Quest> quests = npcActor.getQuests();
        for(Quest quest : quests)
        {
            if(pc.getQuestProgression(quest.getQuestId()) == null)
                return true;
        }
        return false;
    }

    public boolean hasLastStepForCharacter(PlayableCharacter pc)
    {
        for(QuestProgression qp : pc.getQuestProgressions())
        {
            if(qp.getQuest().isLastStep(pc,npc_id))
                return true;
        }
        return false;
    }
}
