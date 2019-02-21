package com.gameserver.config;

import com.gameserver.util.PropertiesParser;

public class Config {
    private static final String configDir = "./config";

    private static final String gameSocketProperties = configDir + "/network/gamesocket.ini";
    public static String GAME_SOCKET_LISTEN_ADDRESS;
    public static short GAME_SOCKET_LISTEN_PORT;
    public static String AUTH_SERVER_ADDRESS;
    public static short AUTH_SERVER_GAME_LISTEN_PORT;


    private static final String databaseProperties = configDir + "/database/database.ini";
    public static String DATABASE_DRIVER;
    public static String DATABASE_DIALECT;
    public static String DATABASE_URL;
    public static String DATABASE_USER;
    public static String DATABASE_PASSWORD;
    public static String DATABASE_SHOW_SQL;
    public static String DATABASE_HBM2DLL;

    private static final String generalProperties = configDir + "/general.ini";
    public static String GAME_SERVER_NAME;

    public static void Load()
    {
        System.out.println("Reading configuration files");

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
    }

}
