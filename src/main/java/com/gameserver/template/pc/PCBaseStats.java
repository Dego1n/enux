package com.gameserver.template.pc;

/*
 * @author Dego1n
 * Copyright (c) 22.03.2019
 */

import com.gameserver.database.staticdata.Race;

public class PCBaseStats {

    private Race race;

    private int collisionHeight;
    private int collisionRadius;

    public PCBaseStats(Race race)
    {
        this.race = race;
    }

    public Race getRace() {
        return race;
    }

    public int getCollisionHeight() {
        return collisionHeight;
    }

    public void setCollisionHeight(int collisionHeight) {
        this.collisionHeight = collisionHeight;
    }

    public int getCollisionRadius() {
        return collisionRadius;
    }

    public void setCollisionRadius(int collisionRadius) {
        this.collisionRadius = collisionRadius;
    }
}
