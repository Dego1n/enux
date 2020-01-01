package com.gameserver.scripting.engine;

import com.gameserver.config.Config;
import com.gameserver.scripting.ai.npc.NpcAi;
import com.gameserver.template.Quest;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Dego1n
 * Компиляция java файла в runtime
 */
public class JavaScriptingEngine {

    private static final Logger log = LoggerFactory.getLogger(JavaScriptingEngine.class);

    private static JavaScriptingEngine _instance;

    public static JavaScriptingEngine getInstance()
    {
        System.out.println(System.getProperty("java.class.path"));
        if(_instance == null)
            _instance = new JavaScriptingEngine();

        return _instance;
    }

    private static final InMemoryJavaCompiler COMPILER = InMemoryJavaCompiler.newInstance() //
            .useOptions("-classpath", System.getProperty("java.class.path")) //
            .ignoreWarnings();

    private static final String pathToScriptsFolder = System.getProperty("user.dir") + "/" + Config.DATAPACK_PATH + "scripts/";

    public NpcAi compileNpcAiScript(int npc_id)
    {
        Class<?> compiledScript = compileScript("ai/npc/$npc_id/NpcAi_$npc_id.java".replace("$npc_id", String.valueOf(npc_id)));
        if(compiledScript != null)
        {
            try {
                return (NpcAi) compiledScript.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                return new NpcAi(npc_id);
            }
        }
        return new NpcAi(npc_id);
    }

    public Quest compileQuestScript(String questName)
    {
        String questPath = "quests/"+questName+"/"+questName+".java";
        Class<?> compiledScript = compileScript(questPath);
        if(compiledScript != null)
        {
            try {
                return (Quest) compiledScript.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        else
        {
            log.error("Couldn't load quest: "+questPath);
            System.exit(1);
        }
        return null;
    }

    public Class<?> compileScript(String filepath) {
        File file = new File(pathToScriptsFolder + filepath);
        if(!file.exists())
        {
            log.warn("Script does not exists: "+filepath);
            return null;
        }
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {
            return COMPILER.compile(getClassForFile(file), readerToString(reader));
        } catch (Exception ex) {
            System.out.println("Error executing script!" + ex);
            ex.printStackTrace();
        }
        return null;
    }
    private static String readerToString(Reader reader) throws ScriptException {
        try (BufferedReader in = new BufferedReader(reader)) {
            final StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line).append(System.lineSeparator());
            }
            return result.toString();
        } catch (IOException ex) {
            throw new ScriptException(ex);
        }
    }
    private static String getClassForFile(File script) {

        String path = script.getPath();

        final int start_index = path.contains("\\") ? path.lastIndexOf("\\") : path.lastIndexOf("/");
        final int last_index = path.lastIndexOf('.');

        return path.substring(start_index + 1, last_index);

    }
}
