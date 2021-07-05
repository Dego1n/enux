package com.gameserver.config;

import com.gameserver.util.PropertiesParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    private static boolean inIDE = false;

    private static final String configDir = "./config";

    private static String gameSocketProperties = configDir + "/network/gamesocket.ini";
    public static String GAME_SOCKET_LISTEN_ADDRESS;
    public static short GAME_SOCKET_LISTEN_PORT;
    public static String AUTH_SERVER_ADDRESS;
    public static short AUTH_SERVER_GAME_LISTEN_PORT;


    private static String databaseProperties = configDir + "/database/database.ini";
    public static String DATABASE_CONNECTION_STRING;
    public static String DATABASE_NAME;

    private static String generalProperties = configDir + "/general/general.ini";
    public static String GAME_SERVER_NAME;

    private static String datapackProperties = configDir + "/general/datapack.ini";
    public static String DATAPACK_PATH;

    private static String geodataProperties = configDir + "/geodata/geodata.ini";
    public static int WORLD_MAP_X_START;
    public static int WORLD_MAP_Y_START;
    public static float WORLD_MAP_X_SCALE;
    public static float WORLD_MAP_Y_SCALE;
    public static float WORLD_MAP_Z_SCALE;
    public static float WORLD_MAP_Z_POSITION;
    public static String HEIGHTMAP_PATH;


    public static void Load()
    {
        log.info("Loading configuration files");
        if(Files.isDirectory(Paths.get("dist/config")))
            inIDE = true;

        //Если запускаем из IDE
        if(inIDE)
        {
            log.info("Seems like game server is running from IDE. Fixing config directories");
            gameSocketProperties = "dist/config/network/gamesocket.ini";
            databaseProperties = "dist/config/database/database.ini";
            generalProperties = "dist/config/general/general.ini";
            datapackProperties = "dist/config/general/datapack.ini";
            geodataProperties = "dist/config/geodata/geodata.ini";
        }

        PropertiesParser configParser = new PropertiesParser(gameSocketProperties);
        GAME_SOCKET_LISTEN_ADDRESS = System.getenv("LISTEN_CLIENTS_ADDR") != null ? System.getenv("LISTEN_CLIENTS_ADDR") : configParser.getString("ListenAddress", "127.0.0.1");
        GAME_SOCKET_LISTEN_PORT = configParser.getShort("ListenPort", (short)6987);
        AUTH_SERVER_ADDRESS = System.getenv("AUTH_SERVER_ADDR") != null ? System.getenv("AUTH_SERVER_ADDR") : configParser.getString("AuthServerAddress", "127.0.0.1");
        AUTH_SERVER_GAME_LISTEN_PORT = configParser.getShort("AuthServerGameListenPort", (short)1234);

        configParser = new PropertiesParser(databaseProperties);
        DATABASE_CONNECTION_STRING = configParser.getString("DatabaseConnectionString","mongodb://localhost");
        DATABASE_NAME = configParser.getString("DatabaseName","enux");

        configParser = new PropertiesParser(generalProperties);
        GAME_SERVER_NAME = configParser.getString("ServerName", "NoNameServer");

        configParser = new PropertiesParser(datapackProperties);
        DATAPACK_PATH = configParser.getString("DatapackPath", "/data/");

        //Если из IDE - перезаписываем путь
        if(inIDE)
        {
            DATAPACK_PATH = "dist/data/";
        }

        //GEODATA
        configParser = new PropertiesParser(geodataProperties);
        WORLD_MAP_X_START = configParser.getInt("WorldMapXStart", -403200);
        WORLD_MAP_Y_START = configParser.getInt("WorldMapYStart", -403200);
        WORLD_MAP_X_SCALE = configParser.getFloat("WorldMapXScale", 100.0f);
        WORLD_MAP_Y_SCALE = configParser.getFloat("WorldMapYScale", 100.0f);
        WORLD_MAP_Z_SCALE = configParser.getFloat("WorldMapZScale", 27.0f);
        WORLD_MAP_Z_POSITION = configParser.getFloat("WorldMapZPosition", 0f);
        HEIGHTMAP_PATH = configParser.getString("HeightMapPath", "/geodata/Heightmap.png");

        //Если из IDE - добавляем /dist/
        if(inIDE)
        {
            HEIGHTMAP_PATH = "/dist"+HEIGHTMAP_PATH;
        }
    }

}
