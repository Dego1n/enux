@echo off
title MMO Game Server

echo Starting Game Server
echo.
java -Xms64m -Xmx128m -cp "libs/gameserver-DEVELOPMENT.jar;config/log/" com.gameserver.GameServer

pause