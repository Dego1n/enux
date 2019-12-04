package com.gameserver.packet;

public class ServerPackets {
    public final static short CHARACTER_LIST = 0x02;
    public final static short CHARACTER_SELECTED_OK = 0x03;
    public final static short MOVE_ACTOR_TO_LOCATION = 0x05;
    public final static short USER_INFO = 0x06;
    public final static short PLAYABLE_ACTOR_INFO = 0x07;
    public final static short DESTROY_ACTOR = 0x08;
    public final static short ACTOR_INFO = 0x09;
    public final static short TARGET_SELECTED = 0x0a;
    public final static short DIALOG = 0x0b;
    public final static short MOVE_TO_PAWN = 0x0c;
    public final static short STOP_MOVING = 0x0d;
    public final static short ATTACK = 0x0e;
    public final static short SYSTEM_MESSAGE = 0x0f;
    public final static short ACTOR_SAY = 0x10;
    public final static short ACTOR_DIED = 0x11;
    public final static short INVENTORY = 0x12;
    public final static short ABILITIES_LIST = 0x13;
    public final static short USE_ABILITY = 0x14;
    public final static short BUYLIST = 0x15;
    public final static short STATUS_INFO = 0x16;
    public final static short PC_ACTOR_INFO = 0x23;
    public final static short DEBUG_DRAW_SPHERE = 0x24;
    public final static short LOOT_DATA_UPDATED = 0x25;
    public final static short SPECIAL_ACTOR_INFO = 0x26;

}
