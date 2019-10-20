package com.gameserver.config;

import com.gameserver.util.PropertiesParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    private static final String configDir = "./config";

    private static String gameSocketProperties = configDir + "/network/gamesocket.ini";
    public static String GAME_SOCKET_LISTEN_ADDRESS;
    public static short GAME_SOCKET_LISTEN_PORT;
    public static String AUTH_SERVER_ADDRESS;
    public static short AUTH_SERVER_GAME_LISTEN_PORT;


    private static String databaseProperties = configDir + "/database/database.ini";
    public static String DATABASE_DRIVER;
    public static String DATABASE_DIALECT;
    public static String DATABASE_URL;
    public static String DATABASE_USER;
    public static String DATABASE_PASSWORD;
    public static String DATABASE_SHOW_SQL;
    public static String DATABASE_HBM2DLL;

    private static String generalProperties = configDir + "/general/general.ini";
    public static String GAME_SERVER_NAME;

    private static String datapackProperties = configDir + "/general/datapack.ini";
    public static String DATAPACK_PATH;

    public static void Load()
    {
        log.info("Loading configuration files");

        //Если запускаем из IDE
        if(Files.isDirectory(Paths.get("./dist/config")) || Files.isDirectory(Paths.get("./authserver")))
        {
            log.info("Seems like game server is running from IDE. Fixing config directories");
            gameSocketProperties = "./dist/config/network/gamesocket.ini";
            databaseProperties = "./dist/config/database/database.ini";
            generalProperties = "./dist/config/general/general.ini";
            datapackProperties = "./dist/config/general/datapack.ini";
        }

        PropertiesParser configParser = new PropertiesParser(gameSocketProperties);
        GAME_SOCKET_LISTEN_ADDRESS = configParser.getString("ListenAddress", "127.0.0.1");
        GAME_SOCKET_LISTEN_PORT = configParser.getShort("ListenPort", (short)6987);
        AUTH_SERVER_ADDRESS = configParser.getString("AuthServerAddress", "127.0.0.1");
        AUTH_SERVER_GAME_LISTEN_PORT = configParser.getShort("AuthServerGameListenPort", (short)1234);

        configParser = new PropertiesParser(databaseProperties);
        DATABASE_DRIVER = configParser.getString("DatabaseDriver","org.postgresql.Driver");
        DATABASE_DIALECT = configParser.getString("DatabaseDialect","org.hibernate.dialect.PostgreSQL95Dialect");
        DATABASE_URL = configParser.getString("DatabaseURL","jdbc:postgresql://localhost:5432/postgres?useSSL=false");
        DATABASE_USER = configParser.getString("DatabaseUser","postgres");
        DATABASE_PASSWORD = configParser.getString("DatabasePassword","root");
        DATABASE_SHOW_SQL = configParser.getString("DatabaseShowSql","true");
        DATABASE_HBM2DLL = configParser.getString("DatabaseHBM2DLL","update");

        configParser = new PropertiesParser(generalProperties);
        GAME_SERVER_NAME = configParser.getString("ServerName", "NoNameServer");

        configParser = new PropertiesParser(datapackProperties);

        DATAPACK_PATH = configParser.getString("DatapackPath", "/data/");
        //Если из IDE - перезаписываем путь
        if(Files.isDirectory(Paths.get("./dist/data")) || Files.isDirectory(Paths.get("./authserver")))
        {
            DATAPACK_PATH = "./dist/data/";
        }
    }

}
