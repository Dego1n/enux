package com.gameserver.database.entity.actor;

import com.gameserver.database.staticdata.CharacterClass;
import com.gameserver.database.staticdata.Race;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;

@Entity("character")
public class Character {

    @Id
    private String id;

    @Property("account_id")
    private String accountId;

    @Property("name")
    private String name;

    @Property("race")
    private Race race;

    @Property("class")
    private CharacterClass characterClass;

    @Property("location_x")
    private int locationX;

    @Property("location_y")
    private int locationY;

    @Property("location_z")
    private int locationZ;

    @Property("level")
    private int level = 1;

    @Property("experience")
    private int experience = 0;

    public Character() {

    }
    public Character(String accountId, String name, Race race, CharacterClass characterClass, int locationX, int locationY, int locationZ) {
        this.accountId = accountId;
        this.name = name;
        this.race = race;
        this.characterClass = characterClass;
        this.locationX = locationX;
        this.locationY = locationY;
        this.locationZ = locationZ;
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public int getLocationZ() {
        return locationZ;
    }

    public void setLocationZ(int locationZ) {
        this.locationZ = locationZ;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
