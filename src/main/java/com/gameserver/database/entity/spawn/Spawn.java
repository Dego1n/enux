package com.gameserver.database.entity.spawn;

import javax.persistence.*;

@Entity
@Table(name = "Spawn")
public class Spawn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "actor_id")
    private int actorId;

    @Column(name = "location_x")
    private int locationX;

    @Column(name = "location_y")
    private int locationY;

    @Column(name = "location_z")
    private int locationZ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
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
}
