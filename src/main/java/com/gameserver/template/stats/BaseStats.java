package com.gameserver.template.stats;

/*
 * @author Dego1n
 * Copyright (c) 22.03.2019
 */

import com.gameserver.database.staticdata.Race;

import java.util.Map;

public class BaseStats {

    private final Race race;

    private int collisionHeight;
    private int collisionRadius;

    private int _int;
    private int _con;
    private int _men;
    private int _dex;
    private int _wit;
    private int _str;

    private Map<Integer, LevelStats> levelStats;

    public BaseStats(Race race)
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

    public int getInt() {
        return _int;
    }

    public void setInt(int _int) {
        this._int = _int;
    }

    public int getCon() {
        return _con;
    }

    public void setCon(int _con) {
        this._con = _con;
    }

    public int getMen() {
        return _men;
    }

    public void setMen(int _men) {
        this._men = _men;
    }

    public int getDex() {
        return _dex;
    }

    public void setDex(int _dex) {
        this._dex = _dex;
    }

    public int getWit() {
        return _wit;
    }

    public void setWit(int _wit) {
        this._wit = _wit;
    }

    public int getStr() {
        return _str;
    }

    public void setStr(int _str) {
        this._str = _str;
    }

    public Map<Integer, LevelStats> getLevelStats() {
        return levelStats;
    }

    public void setLevelStats(Map<Integer, LevelStats> levelStats) {
        this.levelStats = levelStats;
    }
}
