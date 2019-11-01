package com.gameserver.model.actor;

import com.gameserver.database.staticdata.Race;
import com.gameserver.factory.idfactory.ActorIdFactory;
import com.gameserver.model.World;
import com.gameserver.model.actor.ai.base.ActorIntention;
import com.gameserver.model.actor.ai.base.intention.IntentionAttack;
import com.gameserver.model.actor.ai.base.intention.IntentionIdle;
import com.gameserver.model.actor.ai.type.AbstractAI;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.game2client.*;
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

    private boolean isDead = false;

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

    private int _level;

    private List<Task> _tasks;

    private AbstractAI ai;

    private MoveData _moveData;


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

    public int getLevel() {
        return _level;
    }

    public void setLevel(int level) {
        this._level = level;
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
        if(target instanceof NPCActor) {
            target.getAi().onAttacked(this);
        }
        setCanAttack(false);
        if(target.getCurrentHp() > 0 ) {
            float damage = calculateAttackDamageToTarget(target);
            target.setCurrentHp(target.getCurrentHp() - damage);
            ((PlayableCharacter) this).sendPacket(new SystemMessage(target.name + " received " + damage + " damage!"));
            if(target.getCurrentHp() < 0)
            {
                this.getActorIntention().setIntention(new IntentionIdle());
                ((PlayableCharacter) this).sendPacket(new SystemMessage(target.name + " died!"));
                this.setTarget(null);
                ((PlayableCharacter) this).sendPacketAndBroadcastToNearbyPlayers(new ActorDied(target));
                if(target instanceof NPCActor) {
                    ((PlayableCharacter) this).addExperience(((NPCActor) target).getBaseExperience());
                    ((PlayableCharacter) this).sendPacket(new SystemMessage("You received "+((NPCActor) target).getBaseExperience()+" experience"));
                    ((PlayableCharacter) this).sendPacket(new SystemMessage("You current EXP:  "+((PlayableCharacter) this).getCurrentExperience()));

                    new Task(new SpawnActorTask(((NPCActor) target)), (((NPCActor) target).getRespawnTime()) * 1000);
                    target.setDead(true);
                    target.getActorIntention().setIntention(new IntentionIdle());
                    World.getInstance().removeActorFromWorld(target);
                }
            }
            else {
                ((PlayableCharacter) this).sendPacketAndBroadcastToNearbyPlayers(new ActorInfo(target));
            }
        }
        if(this instanceof PlayableCharacter)
        {
            ((PlayableCharacter) this).sendPacketAndBroadcastToNearbyPlayers(new Attack(this, target));
        }
        else
        {
            this.broadcastPacket(new Attack(this,target));
        }
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

    public void broadcastPacket(AbstractSendablePacket packet)
    {
        for(PlayableCharacter pc : nearbyPlayers())
        {
            pc.sendPacket(packet);
        }
    }

    public List<PlayableCharacter> nearbyPlayers()
    {
        return World.getInstance().getPlayableCharactersInRadius(this,100000);
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

    public AbstractAI getAi() {
        return ai;
    }

    public void setAi(AbstractAI ai) {
        this.ai = ai;
    }

    public boolean updatePosition() {
        System.out.println("updatingPosition");
        MoveData moveData = getMoveData();
        if(moveData == null)
        {
            return true;
        }

        if(moveData.lastUpdate == 0) //Первый раз обновляем позицию
        {
            moveData.lastUpdate = moveData.startTime;
        }

        int gameTicks = GameTickController.getInstance().getGameTicks();

        int prevX = getLocationX();
        int prevY = getLocationY();
        int prevZ = getLocationZ();


        int dx = moveData.x_destination - prevX;
        int dy = moveData.y_destination - prevY;
        int dz = moveData.z_destination - prevZ;
        System.out.println("dx: "+dx);
        System.out.println("dy: "+dy);
        double delta = (dx * dx) + (dy*dy);
        delta = Math.sqrt(delta);
        double distFraction = Double.MAX_VALUE;
        System.out.println("Delta: "+delta);
        if(moveData.offset >= delta)
        {
            return true;
        }
        if(delta > 1)
        {
            final double distPassed = (300 * (gameTicks - moveData.lastUpdate)) / GameTickController.TICKS_PER_SECOND;
            distFraction = distPassed / delta;
        }
        else
        System.out.println("distFraction: "+distFraction);
        if (distFraction > 1) {
            // Set the position of the BaseActor to the destination
            this.setLocationX(moveData.x_destination);
            this.setLocationY(moveData.y_destination);
            this.setLocationZ(moveData.z_destination);
            return true;
        } else {
            System.out.println("PREV {"+prevX+":"+prevY+":"+prevZ+"} NEW {"+(prevX + (int) (dx * distFraction))+":"+(prevY + (int) (dy * distFraction))+":"+(prevZ + (int) (dz * distFraction))+"}");
            setLocationX(prevX + (int) (dx * distFraction));
            setLocationY(prevY + (int) (dy * distFraction));
            setLocationZ(prevZ + (int) (dz * distFraction));
        }
        this.broadcastPacket(new DebugDrawSphere(getLocationX(),getLocationY(),getLocationZ()));
        moveData.lastUpdate = gameTicks;
        return false;
    }

    public void onRespawn()
    {
        if(this instanceof NPCActor) {
            setCurrentHp(getMaxHp());
            getAi().resetAi();
            setDead(false);
        }

    }

    public static class MoveData {
        public int x_destination;
        public int y_destination;
        public int z_destination;

        public int startTime;
        public int lastUpdate;
        public int offset;
        public MoveData()
        {
            startTime = GameTickController.getInstance().getGameTicks();
            lastUpdate = 0;
            offset = 0;
        }
    }

    public MoveData getMoveData() {
        return _moveData;
    }

    public void setMoveData(MoveData _moveData) {
        this._moveData = _moveData;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
