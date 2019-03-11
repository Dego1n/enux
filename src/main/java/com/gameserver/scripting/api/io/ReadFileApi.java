package com.gameserver.scripting.api.io;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFileApi extends OneArgFunction {

    @Override
    public LuaValue call(LuaValue luaString) {
        try {
            return CoerceJavaToLua.coerce(new String(Files.readAllBytes(Paths.get((String)CoerceLuaToJava.coerce(luaString, String.class))), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}