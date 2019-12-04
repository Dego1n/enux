/*
 * Author: Dego1n
 * 2.12.2019
 */

package com.gameserver.template.stats;

public class Stats {

    //Constants

    private double _int;
    private double str;
    private double con;
    private double men;
    private double dex;
    private double wit;

    private double maxHp;
    private double maxMp;

    private double hpRegen;
    private double mpRegen;

    private double physicalAttack;
    private double physicalDefence;
    private double evasion;
    private double accuracy;
    private double attackSpeed;
    private double moveSpeed;
    private double critical;

    public Stats()
    {

    }

    public Stats(BaseStats baseStats) {
        this._int = baseStats.getInt();
        this.str = baseStats.getStr();
        this.con = baseStats.getCon();
        this.men = baseStats.getMen();
        this.dex = baseStats.getDex();
        this.wit = baseStats.getWit();

    }

    public double getIntMod()
    {
        return Math.pow(1.02, _int-31.375);
    }

    public double getStrMod()
    {
        return Math.pow(1.036, str-34.845);
    }

    public double getConMod()
    {
        return Math.pow(1.03, con-27.632);
    }

    public double getMenMod()
    {
        return Math.pow(1.01, men+0.06);
    }

    public double getDexMod()
    {
        return Math.pow(1.009,dex-19.36);
    }

    public double getWitMod()
    {
        return Math.pow(1.05,wit-20);
    }

    public double getInt() {
        return _int;
    }

    public void setInt(double _int) {
        this._int = _int;
    }

    public double getStr() {
        return str;
    }

    public void setStr(double str) {
        this.str = str;
    }

    public double getCon() {
        return con;
    }

    public void setCon(double con) {
        this.con = con;
    }

    public double getMen() {
        return men;
    }

    public void setMen(double men) {
        this.men = men;
    }

    public double getDex() {
        return dex;
    }

    public void setDex(double dex) {
        this.dex = dex;
    }

    public double getWit() {
        return wit;
    }

    public void setWit(double wit) {
        this.wit = wit;
    }

    public double getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(double maxHp) {
        this.maxHp = maxHp;
    }

    public double getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(double maxMp) {
        this.maxMp = maxMp;
    }

    public double getHpRegen() {
        return hpRegen;
    }

    public void setHpRegen(double hpRegen) {
        this.hpRegen = hpRegen;
    }

    public double getMpRegen() {
        return mpRegen;
    }

    public void setMpRegen(double mpRegen) {
        this.mpRegen = mpRegen;
    }

    public double getPhysicalAttack() {
        return physicalAttack;
    }

    public void setPhysicalAttack(double physicalAttack) {
        this.physicalAttack = physicalAttack;
    }

    public double getPhysicalDefence() {
        return physicalDefence;
    }

    public void setPhysicalDefence(double physicalDefence) {
        this.physicalDefence = physicalDefence;
    }

    public double getEvasion() {
        return evasion;
    }

    public void setEvasion(double evasion) {
        this.evasion = evasion;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public double getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public double getCritical() {
        return critical;
    }

    public void setCritical(double critical) {
        this.critical = critical;
    }
}
