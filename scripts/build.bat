@echo off
title Building Game Server Project

echo Cleaning build up folder
rd /s /q build

echo Creating new out directory
md build
md build\GameServer

echo Building core with gradle
call gradle build

echo Copying built lib to build
ROBOCOPY  build\libs build\GameServer\libs /E /njh /njs /ndl /nc /ns

echo Copying dist content
ROBOCOPY  dist\ build\GameServer\ /E /njh /njs /ndl /nc /ns

echo Build done
