package com.gameserver.model.actor;

import com.gameserver.database.staticdata.Race;
import com.gameserver.factory.idfactory.ActorIdFactory;
import com.gameserver.model.World;
import com.gameserver.model.actor.ai.base.ActorIntention;
import com.gameserver.model.actor.ai.base.IntentionType;
import com.gameserver.model.actor.ai.base.intention.IntentionAttack;
import com.gameserver.model.actor.ai.base.intention.IntentionIdle;
import com.gameserver.packet.game2client.*;
import com.gameserver.scripting.api.WorldInstanceApi;
import com.gameserver.task.Task;
import com.gameserver.task.actortask.ResetAttackCooldown;
import com.gameserver.task.actortask.SpawnActorTask;
import com.gameserver.tick.GameTickController;
import com.gameserver.tick.job.IntentionThinkJob;

import java.util.*;

public abstract class BaseActor {

    private int objectId;
    protected int id;
    private int locationX;
    private int locationY;
    private int locationZ;

    private boolean isMoving = false;
    private boolean canAttack = true;
    private boolean isFriendly = true;

    private String name;

    private Race race;

    private double currentHp;
    private double maxHp;

    private int templateId;

    private int collisionHeight;
    private int collisionRadius;

    BaseActor target;

    ActorIntention _actorIntention;

    IntentionThinkJob _intentionThinkJob;

    private List<Task> _tasks;



    public BaseActor()
    {
        objectId = ActorIdFactory.getInstance().getFreeId();
        _tasks = new ArrayList<>();
        _actorIntention = new ActorIntention(this);
    }

    public int getObjectId() {
        return objectId;
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

    public boolean isFriendly() {
        return isFriendly;
    }

    void setFriendly(boolean friendly) {
        isFriendly = friendly;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    void setRace(Race race) {
        this.race = race;
    }

    public int getTemplateId() {
        return templateId;
    }

    void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getCollisionHeight() {
        return collisionHeight;
    }

    void setCollisionHeight(int collisionHeight) {
        this.collisionHeight = collisionHeight;
    }

    public int getCollisionRadius() {
        return collisionRadius;
    }

    void setCollisionRadius(int collisionRadius) {
        this.collisionRadius = collisionRadius;
    }

    public BaseActor getTarget() {
        return target;
    }

    public void setTarget(BaseActor target) {
        this.target = target;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setIsMoving(boolean moving) {
        isMoving = moving;
    }


    public ActorIntention getActorIntention() {
        return _actorIntention;
    }

    public void requestAttack()
    {
        if(target == null)
            return;

        _actorIntention.setIntention(new IntentionAttack(target,true));
    }
    public void attack(BaseActor target)
    {
        setCanAttack(false);
        if(target.getCurrentHp() > 0 ) {
            float damage = calculateAttackDamageToTarget(target);
            target.setCurrentHp(target.getCurrentHp() - damage);
            ((PlayableCharacter) this).sendPacket(new SystemMessage(target.name + " received " + damage + " damage!"));
            if(target.getCurrentHp() < 0)
            {
                this.getActorIntention().setIntention(new IntentionIdle());
                ((PlayableCharacter) this).sendPacket(new SystemMessage(target.name + " died!"));
                ((PlayableCharacter) this).sendPacketAndBroadcastToNearbyPlayers(new ActorDied(target));
                if(target instanceof NPCActor) {

                    new Task(new SpawnActorTask(((NPCActor) target).getSpawn()), (int) ((((NPCActor) target).getRespawnTime()) * 1000));

                    World.getInstance().removeActorFromWorld(target);
                }
            }
            else {
                ((PlayableCharacter) this).sendPacketAndBroadcastToNearbyPlayers(new ActorInfo(target));
            }
        }
        ((PlayableCharacter) this).sendPacketAndBroadcastToNearbyPlayers(new Attack(this, target));
        new Task(new ResetAttackCooldown(this), (int) ((1 / 0.8f) * 1000)); //TODO: 0.8f - attack speed, get from stats instead of const
    }

    public float calculateAttackDamageToTarget(BaseActor target)
    {
        Random rnd = new Random();
        return 10 + rnd.nextFloat() * (20 - 10);
    }

    public void say(String message)
    {
        System.out.println("client "+getName()+" trying to say: "+message);
        ActorSay actorSayPacket = new ActorSay(this,this.getName(),message);
        if(this instanceof PlayableCharacter)
        {
            ((PlayableCharacter) this).sendPacketAndBroadcastToNearbyPlayers(actorSayPacket);
        }
        else
        {
            for(PlayableCharacter pc : World.getInstance().getPlayableCharactersInRadius(this,10000))
            {
                pc.sendPacket(actorSayPacket);
            }
        }
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public double getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(double currentHp) {
        this.currentHp = currentHp;
    }

    public double getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(double maxHp) {
        this.maxHp = maxHp;
    }
}
