package com.gameserver.packet;

import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.client2game.*;

public class ClientPackets {

    private final static short REQUEST_CONNECT_TO_GAME_SERVER = 0x01;
    private final static short CHARACTER_SELECTED = 0x02;
    private final static short REQUEST_CREATE_CHARACTER = 0x03;


    private final static short MOVE_TO_LOCATION = 0x05;
    private final static short ENTER_WORLD = 0x06;
    private final static short ACTION = 0x07;
    private final static short REQUEST_DIALOG = 0x08;
    private final static short REQUEST_ATTACK = 0x09;
    private final static short VALIDATE_POSITION = 0x0a;

    private final static short APPEARING = 0x0b;
    private final static short LOGOUT = 0x0d;
    private final static short REQUEST_ACQUIRE_SKILL = 0x0e;
    private final static short REQUEST_BUY_ITEM = 0x0f;
    private final static short REQUEST_DROP_ITEM = 0x10;
    private final static short REQUEST_DESTROY_ITEM = 0x11;
    private final static short REQUEST_MAGIC_SKILL_USE = 0x12;
    private final static short REQUEST_QUEST_ABORT = 0x13;
    private final static short REQUEST_RESTART = 0x14;
    private final static short REQUEST_SELL_ITEM = 0x15;
    private final static short REQUEST_REGISTER_SHORT_CUT = 0x16;
    private final static short REQUEST_REMOVE_SHORT_CUT = 0x17;
    private final static short REQUEST_UNEQUIP_ITEM = 0x18;
    private final static short SAY = 0x19;
    private final static short SAY_PRIVATE = 0x1a;
    private final static short USE_ITEM = 0x1c;
    private final static short REQUEST_INVENTORY = 0x1d;
    private final static short REQUEST_COMMAND = 0x1e;
    private final static short REQUEST_TAKE_LOOT = 0x1f;

    public static void HandlePacket(ClientListenerThread clientListenerThread, byte [] packet)
    {
        short packetID = (short)(((packet[1] & 0xFF) << 8) | (packet[0] & 0xFF));
        switch (packetID)
        {
            case REQUEST_CONNECT_TO_GAME_SERVER:
                new RequestConnectToGameServer(clientListenerThread,packet);
                break;
            case CHARACTER_SELECTED:
                new CharacterSelected(clientListenerThread,packet);
                break;
            case REQUEST_CREATE_CHARACTER:
                new RequestCreateCharacter(clientListenerThread,packet);
                break;
            case MOVE_TO_LOCATION:
                new MoveToLocation(clientListenerThread,packet);
                break;
            case ENTER_WORLD:
                new EnterWorld(clientListenerThread, packet);
                break;
            case ACTION:
                new Action(clientListenerThread,packet);
                break;
            case REQUEST_DIALOG:
                new RequestDialog(clientListenerThread,packet);
                break;
            case REQUEST_ATTACK:
                new RequestAttack(clientListenerThread,packet);
                break;
            case VALIDATE_POSITION:
                new ValidatePosition(clientListenerThread,packet);
                break;
            case APPEARING:
                new Appearing(clientListenerThread,packet);
                break;
            case LOGOUT:
                new Logout(clientListenerThread,packet);
                break;
            case REQUEST_ACQUIRE_SKILL:
                new RequestAcquireSkill(clientListenerThread,packet);
                break;
            case REQUEST_BUY_ITEM:
                new RequestBuyItem(clientListenerThread,packet);
                break;
            case REQUEST_DROP_ITEM:
                new RequestDropItem(clientListenerThread,packet);
                break;
            case REQUEST_DESTROY_ITEM:
                new RequestDestroyItem(clientListenerThread,packet);
                break;
            case REQUEST_MAGIC_SKILL_USE:
                new RequestMagicSkillUse(clientListenerThread,packet);
                break;
            case REQUEST_QUEST_ABORT:
                new RequestQuestAbort(clientListenerThread,packet);
                break;
            case REQUEST_RESTART:
                new RequestRestart(clientListenerThread,packet);
                break;
            case REQUEST_SELL_ITEM:
                new RequestSellItem(clientListenerThread,packet);
                break;
            case REQUEST_REGISTER_SHORT_CUT:
                new RequestRegisterShortCut(clientListenerThread,packet);
                break;
            case REQUEST_REMOVE_SHORT_CUT:
                new RequestRemoveShortCut(clientListenerThread,packet);
                break;
            case REQUEST_UNEQUIP_ITEM:
                new RequestUnequipItem(clientListenerThread,packet);
                break;
            case SAY:
                new Say(clientListenerThread,packet);
                break;
            case SAY_PRIVATE:
                new SayPrivate(clientListenerThread,packet);
                break;
            case USE_ITEM:
                new UseItem(clientListenerThread,packet);
                break;
            case REQUEST_INVENTORY:
                new RequestInventory(clientListenerThread,packet);
                break;
            case REQUEST_COMMAND:
                new RequestCommand(clientListenerThread,packet);
                break;
            case REQUEST_TAKE_LOOT:
                new RequestTakeLoot(clientListenerThread,packet);
                break;
        }
    }
}
