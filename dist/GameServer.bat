@echo off
title MMO Game Server

echo Starting Game Server
echo.
java -Xms64m -Xmx128m -jar libs/gameserver-DEVELOPMENT.jar

pause