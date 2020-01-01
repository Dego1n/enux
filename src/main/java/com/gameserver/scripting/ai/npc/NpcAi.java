package com.gameserver.scripting.ai.npc;

import com.gameserver.config.Config;
import com.gameserver.model.World;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.template.Quest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NpcAi {

    private int npc_id;
    protected int object_id;

    public NpcAi(int npc_id)
    {
        this.npc_id = npc_id;
    }


    public void onTalk(PlayableCharacter character) {
        String dialog = getDialog("index.dialog");
        NPCActor npcActor = (NPCActor) World.getInstance().getActorByObjectId(object_id);
        if (npcActor.getQuests().size() > 0)
        {
            dialog += "\n<button type=\"npc_dialog\" object_id=\""+object_id+"\" ref=\"quest\">Quest</>";
        }
        prepareDialogAndSend(character, dialog);;
    }
    public String getDialog(String name)
    {
        try {
            return new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/" + Config.DATAPACK_PATH + "scripts/ai/npc/" + npc_id + "/dialogs/"+name)));
        } catch (IOException e) {
            e.printStackTrace();
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
        System.out.println("requestedDialog");
        onTalk(character,dialog);
    }

    public void setObjectId(int object_id) {
        this.object_id = object_id;
    }

    public void onQuestTalk(PlayableCharacter character)
    {
        NPCActor npcActor = (NPCActor) World.getInstance().getActorByObjectId(object_id);
        StringBuilder dialog = new StringBuilder();
        for(Quest quest : npcActor.getQuests())
        {
            dialog.append("<button type=\"quest\" object_id=\""+object_id+"\" quest_id=\""+quest.getQuestId()+"\" ref=\"index\">"+quest.getQuestId()+"</>\n");
        }
        prepareDialogAndSend(character,dialog.toString());
    }
}
