package com.gameserver.scripting.api.system;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

public class SystemApi extends OneArgFunction {

    @Override
    public LuaValue call(LuaValue luaString) {
        String property = (String)CoerceLuaToJava.coerce(luaString, String.class);
        return CoerceJavaToLua.coerce(System.getProperty(property));
    }
}
