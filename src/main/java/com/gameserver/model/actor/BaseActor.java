package com.gameserver.model.actor;

import com.gameserver.database.staticdata.Race;
import com.gameserver.factory.idfactory.ActorIdFactory;
import com.gameserver.model.actor.ai.AiState;
import com.gameserver.model.actor.ai.BaseAI;
import com.gameserver.task.Task;
import com.gameserver.task.actortask.AttackTask;

import java.util.*;

public abstract class BaseActor {

    protected int objectId;
    protected int id;
    private int locationX;
    private int locationY;
    private int locationZ;

    private boolean isFriendly = true;

    private String name;

    private Race race;

    private int templateId;

    protected BaseActor target;

    protected BaseAI _ai;

    protected List<Task> _tasks;

    private float attackSpeed = 1.0f;

    public BaseActor()
    {
        objectId = ActorIdFactory.getInstance().getFreeId();
        _ai = new BaseAI();
        _tasks = new ArrayList<>();
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

    public BaseAI getAi() {
        return _ai;
    }

    public void requestAttack()
    {
        if(target == null)
            return;

        //TODO: Проверка пожилого расстояния
        _ai.changeStatus(AiState.ATTACKING);
        _tasks.add(new Task(new AttackTask(this,target),0, (long)(attackSpeed*1000)));
    }
    public void attack(BaseActor target)
    {
        System.out.println("Attack tick from: "+getName()+" to "+target.getName());
    }

    public List<Task> getTasks() {
        return _tasks;
    }

    public void stopTask(Task task) {
        task.timer.cancel();
        _tasks.remove(task);
    }
}
