/*
 * Author: Dego1n
 * 2.12.2019
 */

package com.gameserver.task.actortask;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.game2client.ActorInfo;
import com.gameserver.packet.game2client.StatusInfo;

import java.util.Timer;
import java.util.TimerTask;

public class RegenTask extends TimerTask {

    BaseActor actor;
    Timer timer;

    public RegenTask(BaseActor actor, Timer timer) {
        this.actor = actor;
        this.timer = timer;
    }

    @Override
    public void run() {
        System.out.println("Executed regen task for  "+actor.getName());
        if(actor.getCurrentHp() <= actor.getMaxHp())
        {
            ;
            double hp_regen = actor.getHpRegenRate() * BaseActor.REGEN_TASK_EVERY_SECONDS;
            actor.setCurrentHp(Math.min(actor.getCurrentHp() + hp_regen, actor.getMaxHp()));
        }
        if(actor.getCurrentMp() <= actor.getCurrentMp())
        {
            double mp_regen = actor.getMpRegenRate() * BaseActor.REGEN_TASK_EVERY_SECONDS;
            actor.setCurrentMp(Math.min(actor.getCurrentMp() + mp_regen, actor.getMaxMp()));
        }
        if(actor instanceof NPCActor)
        {
            actor.broadcastActorInfo(actor);
        }
        else if(actor instanceof PlayableCharacter)
        {
            ((PlayableCharacter) actor).sendPacket(new StatusInfo((PlayableCharacter) actor));
        }
        if(actor.getCurrentHp() >= actor.getMaxHp() && actor.getCurrentMp() >= actor.getCurrentMp())
        {
            actor.setHasRegenTask(false);
            timer.cancel();
        }
    }
}
