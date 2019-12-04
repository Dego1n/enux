package com.gameserver.model.actor.playable.equip;

import com.gameserver.model.item.Item;
import com.gameserver.template.stats.StatModifier;

import java.util.ArrayList;
import java.util.List;

public class EquipInfo {
    private Item rightHand;

    private Item leftHand;

    private Item helmet;

    private Item upperArmor;

    private Item lowerArmor;

    private Item gloves;

    private Item boots;

    private Item belt;

    private Item earringFirst;

    private Item earringSecond;

    private Item ringFirst;

    private Item ringSecond;

    private Item necklace;

    public Item getRightHand() {
        return rightHand;
    }

    public void setRightHand(Item rightHand) {
        this.rightHand = rightHand;
    }

    public Item getLeftHand() {
        return leftHand;
    }

    public void setLeftHand(Item leftHand) {
        this.leftHand = leftHand;
    }

    public Item getHelmet() {
        return helmet;
    }

    public void setHelmet(Item helmet) {
        this.helmet = helmet;
    }

    public Item getUpperArmor() {
        return upperArmor;
    }

    public void setUpperArmor(Item upperArmor) {
        this.upperArmor = upperArmor;
    }

    public Item getLowerArmor() {
        return lowerArmor;
    }

    public void setLowerArmor(Item lowerArmor) {
        this.lowerArmor = lowerArmor;
    }

    public Item getGloves() {
        return gloves;
    }

    public void setGloves(Item gloves) {
        this.gloves = gloves;
    }

    public Item getBoots() {
        return boots;
    }

    public void setBoots(Item boots) {
        this.boots = boots;
    }

    public Item getBelt() {
        return belt;
    }

    public void setBelt(Item belt) {
        this.belt = belt;
    }

    public Item getEarringFirst() {
        return earringFirst;
    }

    public void setEarringFirst(Item earringFirst) {
        this.earringFirst = earringFirst;
    }

    public Item getEarringSecond() {
        return earringSecond;
    }

    public void setEarringSecond(Item earringSecond) {
        this.earringSecond = earringSecond;
    }

    public Item getRingFirst() {
        return ringFirst;
    }

    public void setRingFirst(Item ringFirst) {
        this.ringFirst = ringFirst;
    }

    public Item getRingSecond() {
        return ringSecond;
    }

    public void setRingSecond(Item ringSecond) {
        this.ringSecond = ringSecond;
    }

    public Item getNecklace() {
        return necklace;
    }

    public void setNecklace(Item necklace) {
        this.necklace = necklace;
    }

    public List<StatModifier> getAllEquipedStatsModifiers() {
        List<StatModifier> statModifiers = new ArrayList<>();
        if(rightHand != null) statModifiers.addAll(rightHand.getBaseItem().getStatModifiers());
        if(leftHand != null) statModifiers.addAll(leftHand.getBaseItem().getStatModifiers());
        if(helmet != null) statModifiers.addAll(helmet.getBaseItem().getStatModifiers());
        if(upperArmor != null) statModifiers.addAll(upperArmor.getBaseItem().getStatModifiers());
        if(lowerArmor != null) statModifiers.addAll(lowerArmor.getBaseItem().getStatModifiers());
        if(boots != null) statModifiers.addAll(boots.getBaseItem().getStatModifiers());
        if(belt != null) statModifiers.addAll(belt.getBaseItem().getStatModifiers());
        if(earringFirst != null) statModifiers.addAll(earringFirst.getBaseItem().getStatModifiers());
        if(earringSecond != null) statModifiers.addAll(earringSecond.getBaseItem().getStatModifiers());
        if(ringFirst != null) statModifiers.addAll(ringFirst.getBaseItem().getStatModifiers());
        if(ringSecond != null) statModifiers.addAll(ringSecond.getBaseItem().getStatModifiers());
        if(necklace != null) statModifiers.addAll(necklace.getBaseItem().getStatModifiers());

        return statModifiers;
    }
}
