package com.gameserver.database.staticdata;

public enum Race {
    Human(0),
    Elf(1);

    private final int value;
    Race(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
