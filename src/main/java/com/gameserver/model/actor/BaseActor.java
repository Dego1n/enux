package com.gameserver.model.actor;

import com.gameserver.database.staticdata.Race;
import com.gameserver.factory.idfactory.ActorIdFactory;
import com.gameserver.model.World;
import com.gameserver.model.ability.Ability;
import com.gameserver.model.actor.ai.base.ActorIntention;
import com.gameserver.model.actor.ai.base.intention.IntentionAttack;
import com.gameserver.model.actor.ai.type.AbstractAI;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.game2client.*;
import com.gameserver.task.actortask.AbilityCastEnd;
import com.gameserver.task.actortask.RegenTask;
import com.gameserver.template.stats.Stats;
import com.gameserver.tick.GameTickController;

import java.util.*;

public abstract class BaseActor {
    public static final int REGEN_TASK_EVERY_SECONDS = 5;

    private final int objectId;
    protected String id;
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
    private double currentMp;

    private int templateId;

    private int collisionHeight;
    private int collisionRadius;

    BaseActor target;

    final ActorIntention _actorIntention;

    private int _level;

    private AbstractAI ai;

    private MoveData _moveData;

    protected List<TimerTask> _tasks;

    private boolean hasRegenTask = false;

    protected Stats stats;

    BaseActor() {
        objectId = ActorIdFactory.getInstance().getFreeId();
        _actorIntention = new ActorIntention(this);
        _tasks = new ArrayList<>();
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

    void setTarget(BaseActor target) {
        this.target = target;
    }

    public void setIsMoving(boolean moving) {
        isMoving = moving;
    }

    public int getLevel() {
        return _level;
    }

    void setLevel(int level) {
        this._level = level;
    }

    public ActorIntention getActorIntention() {
        return _actorIntention;
    }

    public void requestAttack() {
        if (target == null)
            return;

        _actorIntention.setIntention(new IntentionAttack(target));
    }

    public abstract void attack(BaseActor target);
    public abstract void useAbility(BaseActor target, Ability ability);
    public abstract void doDamage(BaseActor target, double damage);
    public abstract void onAbilityCastEnd(AbilityCastEnd abilityCastEnd);
    public abstract double getMaxHp();
    public abstract double getMaxMp();
    public abstract double getHpRegenRate();
    public abstract double getMpRegenRate();

    void recalculateStats() {
        recalculateStats(false);
    };

    public abstract void recalculateStats(boolean sendToClient);

    double calculateAttackDamageToTarget(BaseActor target, boolean isCritical) {
        Random rnd = new Random();
        double physDefence = target.getStats().getPhysicalDefence();
        //Чтобы не было деления на 0
        physDefence = physDefence <= 0 ? 1 : physDefence;
        double damage = ((76 * stats.getPhysicalAttack()) / physDefence) * (rnd.nextFloat() * (1.1 - 0.9) + 0.9);
        return isCritical ? damage * 2 : damage;
    }

    public abstract void say(String message);

    public void broadcastPacket(AbstractSendablePacket packet) {
        for (PlayableCharacter pc : nearbyPlayers()) {
            pc.sendPacket(packet);
        }
    }

    List<PlayableCharacter> nearbyPlayers() {
        return World.getInstance().getPlayableCharactersInRadius(this, 100000);
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

    public double getCurrentMp() {
        return currentMp;
    }

    public void setCurrentMp(double currentMp)
    {
        this.currentMp = currentMp;
    }

    public AbstractAI getAi() {
        return ai;
    }

    void setAi(AbstractAI ai) {
        this.ai = ai;
    }

    public boolean updatePosition() {
        System.out.println("updatingPosition");
        MoveData moveData = getMoveData();
        if (moveData == null) {
            return true;
        }

        if (moveData.lastUpdate == 0) //Первый раз обновляем позицию
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
        System.out.println("dx: " + dx);
        System.out.println("dy: " + dy);
        double delta = (dx * dx) + (dy * dy);
        delta = Math.sqrt(delta);
        double distFraction = Double.MAX_VALUE;
        System.out.println("Delta: " + delta);
        if (moveData.offset >= delta) {
            return true;
        }
        if (delta > 1) {
            final double distPassed = (getStats().getMoveSpeed() * (gameTicks - moveData.lastUpdate)) / (double) GameTickController.TICKS_PER_SECOND;
            distFraction = distPassed / delta;
        } else
            System.out.println("distFraction: " + distFraction);
        if (distFraction > 1) {
            // Set the position of the BaseActor to the destination
            setLocationX(moveData.x_destination);
            setLocationY(moveData.y_destination);
            setLocationZ(moveData.z_destination);
            return true;
        } else {
            System.out.println("PREV {" + prevX + ":" + prevY + ":" + prevZ + "} NEW {" + (prevX + (int) (dx * distFraction)) + ":" + (prevY + (int) (dy * distFraction)) + ":" + (prevZ + (int) (dz * distFraction)) + "}");
            setLocationX(prevX + (int) (dx * distFraction));
            setLocationY(prevY + (int) (dy * distFraction));
            setLocationZ(prevZ + (int) (dz * distFraction));
        }
        broadcastPacket(new DebugDrawSphere(getLocationX(), getLocationY(), getLocationZ()));
        moveData.lastUpdate = gameTicks;
        return false;
    }

    public abstract void onRespawn();

    public boolean hasRegenTask() {
        return hasRegenTask;
    }

    public void setHasRegenTask(boolean hasRegenTask) {
        this.hasRegenTask = hasRegenTask;
    }

    public static class MoveData {
        public int x_destination;
        public int y_destination;
        public int z_destination;

        final int startTime;
        int lastUpdate;
        public int offset;

        public MoveData() {
            startTime = GameTickController.getInstance().getGameTicks();
            lastUpdate = 0;
            offset = 0;
        }
    }

    private MoveData getMoveData() {
        return _moveData;
    }

    public void setMoveData(MoveData _moveData) {
        this._moveData = _moveData;
    }

    public boolean isDead() {
        return isDead;
    }

    void setDead(boolean dead) {
        isDead = dead;
    }

    public void removeTask(TimerTask task)
    {
        _tasks.remove(task);
    }

    public void doRegenTaskIfNeeded()
    {
        if(getCurrentHp() < getMaxHp() || getCurrentMp() < getMaxMp())
        {
            if(!hasRegenTask())
            {
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new RegenTask(this, timer), REGEN_TASK_EVERY_SECONDS * 1000, REGEN_TASK_EVERY_SECONDS * 1000);
                setHasRegenTask(true);
            }
        }
    }

    public Stats getStats() {
        return stats;
    }

    /**
     * Минимальный шанс попасть - 20%
     * Максимальный шанс попасть - 98%
     * @param target
     * @return
     */
    public boolean actorHitTarget(BaseActor target)
    {
        double chance =
                Math.min(
                        980,
                        Math.max(
                                (
                                        80+(2*(getStats().getAccuracy()-getStats().getEvasion())))*10,
                                200
                        )
                );
        Random rnd = new Random();
        return rnd.nextInt(1000) <= chance;
    }

    public boolean actorHitCritical()
    {
        Random rnd = new Random();
        return rnd.nextInt(1000) <= stats.getCritical();
    }

    public String getTitle()
    {
        //TODO: titles
        return "todo: implement titles";
    }

    public void broadcastActorInfo(BaseActor actor)
    {
        for(PlayableCharacter pc : nearbyPlayers())
        {
            pc.sendPacket(new ActorInfo(actor, pc));
        }
    }
}
