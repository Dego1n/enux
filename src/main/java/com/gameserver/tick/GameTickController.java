package com.gameserver.tick;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.tick.job.AbstractTickJob;
import com.gameserver.tick.job.IntentionThinkJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GameTickController extends Thread {

    private static final Logger log = LoggerFactory.getLogger(GameTickController.class);

    private static GameTickController _instance;

    public static final int TICKS_PER_SECOND = 10;
    private static final int MILLIS_IN_TICK = 1000 / TICKS_PER_SECOND;

    private IntentionThinkJob _intentionThinkJob;
    private List<AbstractTickJob> _jobs;

    private final Set<BaseActor> _movingObjects = ConcurrentHashMap.newKeySet();

    private final long _startTime;

    private GameTickController() {
        super("GameTickController");
        super.setDaemon(true);
        super.setPriority(MAX_PRIORITY);
        super.start();
        log.info("Initializing GameTickController");
        _startTime = System.currentTimeMillis();
    }

    public static void init() {
        _instance = new GameTickController();
        _instance._jobs = new ArrayList<>();
    }

    private void moveObjects()
    {
        _movingObjects.removeIf(BaseActor::updatePosition);
    }
    public void registerMovingObject(BaseActor actor)
    {
        _movingObjects.add(actor);
    }

    @Override
    public final void run() {
        log.info("{}: Thread created.", getClass().getSimpleName());
        _intentionThinkJob = new IntentionThinkJob();
        _jobs.add(_intentionThinkJob);

        log.info("Intention think job created");

        while (true) {
            try{
                moveObjects();
                for(AbstractTickJob job : _jobs)
                {
                    job.runJob();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            long nextTickTime, sleepTime;
            nextTickTime = ((System.currentTimeMillis() / MILLIS_IN_TICK) * MILLIS_IN_TICK) + 100;

            sleepTime = nextTickTime - System.currentTimeMillis();
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (final InterruptedException ignored) {

                }
            }
        }
    }

    public static GameTickController getInstance() {
        return _instance;
    }

    public IntentionThinkJob getIntentionThinkJob()
    {
        return _intentionThinkJob;
    }

    public final int getGameTicks() {
        return (int) ((System.currentTimeMillis() - _startTime) / MILLIS_IN_TICK);
    }

    public void onPlayerDisconnect(PlayableCharacter character)
    {
        _movingObjects.remove(character);
    }
}
