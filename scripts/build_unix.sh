echo Building Game Server Project

echo Cleaning build up folder
rm -rf ./build

echo Creating new out directory
mkdir build
mkdir build/GameServer

echo Building core with gradle
gradle build

echo Copying built lib to build
mkdir build/GameServer/libs
cp -r build/libs/* build/GameServer/libs/

echo Copying dist content
cp -r dist/* build/GameServer/

echo Build done

