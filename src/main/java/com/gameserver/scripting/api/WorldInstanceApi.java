package com.gameserver.scripting.api;

import com.gameserver.model.World;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class WorldInstanceApi extends ZeroArgFunction {

    @Override
    public LuaValue call() {
            return CoerceJavaToLua.coerce(World.getInstance());
    }
}
