package com.gameserver.database.staticdata;

import java.util.HashMap;
import java.util.Map;

public enum Race {
    Human(0),
    Elf(1);

    private final int value;
    private static final Map map = new HashMap<>();

    Race(int value) {
        this.value = value;
    }

    static {
        for (Race pageType : Race.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static Race valueOf(int pageType) {
        return (Race) map.get(pageType);
    }

    public int getValue() {
        return value;
    }
}
