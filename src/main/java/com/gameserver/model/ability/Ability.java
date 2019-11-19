package com.gameserver.model.ability;

import java.util.List;

public class Ability {
    private int _id;

    private List<AbilityLevel> _levels;

    public Ability(int id, List<AbilityLevel> levels) {
        this._id = id;
        this._levels = levels;
    }

    public AbilityLevel getAbilityByLevel(Integer level) {
        for(AbilityLevel abilityLevel : _levels)
        {
            if(abilityLevel.getLevel() == level)
                return abilityLevel;
        }
        return null;
    }

    public static class AbilityLevel {

        private int _level;
        private int _manaCost;
        private float _castTime;
        private float _range;

        public AbilityLevel(int level, int manaCost, float castTime, float range) {
            this._level = level;
            this._manaCost = manaCost;
            this._castTime = castTime;
            this._range = range;
        }

        public int getManaCost() {
            return _manaCost;
        }

        public int getLevel() {
            return _level;
        }

        public float getCastTime() {
            return _castTime;
        }

        public float getRange() { return _range; }
    }

    public int getId() {
        return _id;
    }

    public List<AbilityLevel> getLevels() {
        return _levels;
    }
}
