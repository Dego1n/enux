package com.gameserver.database.staticdata;

public enum CharacterClass {
    Fighter(0),
    Mystic(1);

    private final int value;
    CharacterClass(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
