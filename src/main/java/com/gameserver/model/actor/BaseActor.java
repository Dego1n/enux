package com.gameserver.model.actor;

import com.gameserver.database.staticdata.Race;
import com.gameserver.factory.idfactory.ActorIdFactory;
import com.gameserver.model.actor.ai.base.ActorIntention;
import com.gameserver.model.actor.ai.base.IntentionType;
import com.gameserver.model.actor.ai.base.intention.IntentionAttack;
import com.gameserver.packet.game2client.MoveToPawn;
import com.gameserver.packet.game2client.StateInfo;
import com.gameserver.task.Task;
import com.gameserver.task.actortask.AttackTask;
import com.gameserver.util.math.xyz.Math3d;

import java.util.*;

public abstract class BaseActor {

    protected int objectId;
    protected int id;
    private int locationX;
    private int locationY;
    private int locationZ;

    private boolean isMoving = false;
    private boolean isAttacking = false;

    private boolean isFriendly = true;

    private String name;

    private Race race;

    private int templateId;

    protected BaseActor target;

    ActorIntention _actorIntention;

    protected List<Task> _tasks;

    public BaseActor()
    {
        objectId = ActorIdFactory.getInstance().getFreeId();
        _tasks = new ArrayList<>();
        _actorIntention = new ActorIntention(this);
    }

    public int getObjectId() {
        return objectId;
    }

    public int getId() {
        return id;
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

    public void setFriendly(boolean friendly) {
        isFriendly = friendly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
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

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setIsAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public ActorIntention getActorIntention() {
        return _actorIntention;
    }

    public void requestAttack()
    {
        if(target == null)
            return;

        _actorIntention.setIntention(new IntentionAttack(target,true));
        _actorIntention.intentionThink();
        //_tasks.add(new Task(new AttackTask(this,target),0, (long)(attackSpeed*1000)));
    }
    public void attack(BaseActor target)
    {
        System.out.println("Attack tick from: "+getName()+" to "+target.getName());
        if(_actorIntention.getIntention().intentionType != IntentionType.INTENTION_ATTACK) {
            isAttacking = false;
            if(this instanceof PlayableCharacter)
            {
                ((PlayableCharacter) this).sendPacket(new StateInfo(this));
            }
        }
        _actorIntention.intentionThink();
    }

    public List<Task> getTasks() {
        return _tasks;
    }

    public void stopTask(Task task) {
        task.timer.cancel();
        _tasks.remove(task);
    }
}
