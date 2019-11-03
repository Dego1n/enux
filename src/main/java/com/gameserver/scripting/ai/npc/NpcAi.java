package com.gameserver.scripting.ai.npc;

import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.scripting.api.WorldInstanceApi;
import com.gameserver.scripting.api.io.ReadFileApi;
import com.gameserver.scripting.api.system.SystemApi;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class NpcAi {

    private final LuaValue script;
    private final String scriptRootDirectory;

    public NpcAi(NPCActor actor, String scriptPath)
    {
        scriptRootDirectory = scriptPath;
        script = JsePlatform.standardGlobals();
        script.get("dofile").call(LuaValue.valueOf(scriptPath) + "/npc_ai.lua");
        script.set("npc", CoerceJavaToLua.coerce(actor));
        registerGlobals();
    }

    private void registerGlobals()
    {
        script.set("self_path",scriptRootDirectory);
        script.set("SysProps", new SystemApi());
        script.set("ReadFile", new ReadFileApi());
        script.set("World", new WorldInstanceApi());
    }

    public LuaValue getScript() {
        return script;
    }

    public void onTalk(PlayableCharacter character)
    {
        if(script == null)
            return;
        LuaValue luaPc = CoerceJavaToLua.coerce(character);
        script.get("onTalk").call(luaPc);
    }

    public void requestDialog(PlayableCharacter character, String dialog)
    {
        if(script == null)
            return;
        LuaValue luaPc = CoerceJavaToLua.coerce(character);
        LuaValue luaDialog = CoerceJavaToLua.coerce(dialog);
        script.get("Dialog").call(luaPc, luaDialog);
    }
}
