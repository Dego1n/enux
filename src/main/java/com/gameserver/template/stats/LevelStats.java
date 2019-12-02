package com.gameserver.template.stats;

public class LevelStats {
    private float base_hp;
    private float base_mp;
    private float base_hp_regen;
    private float base_mp_regen;

    public LevelStats(float base_hp, float base_mp, float base_hp_regen, float base_mp_regen) {
        this.base_hp = base_hp;
        this.base_mp = base_mp;
        this.base_hp_regen = base_hp_regen;
        this.base_mp_regen = base_mp_regen;
    }

    public float getBaseHp() {
        return base_hp;
    }

    public void setBaseHp(float base_hp) {
        this.base_hp = base_hp;
    }

    public float getBaseMp() {
        return base_mp;
    }

    public void setBaseMp(float base_mp) {
        this.base_mp = base_mp;
    }

    public float getBaseHpRegen() {
        return base_hp_regen;
    }

    public void setBaseHpRegen(float base_hp_regen) {
        this.base_hp_regen = base_hp_regen;
    }

    public float getBaseMpRegen() {
        return base_mp_regen;
    }

    public void setBaseMpRegen(float base_mp_regen) {
        this.base_mp_regen = base_mp_regen;
    }
}
