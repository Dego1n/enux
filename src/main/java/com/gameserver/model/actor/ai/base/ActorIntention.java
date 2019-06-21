package com.gameserver.model.actor.ai.base;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.model.actor.ai.base.intention.*;
import com.gameserver.packet.game2client.Attack;
import com.gameserver.task.Task;
import com.gameserver.task.actortask.AttackTask;
import com.gameserver.task.actortask.ResetAttackCooldown;
import com.gameserver.tick.GameTickController;
import com.gameserver.util.math.xy.Math2d;
import com.gameserver.util.math.xyz.Math3d;

public class ActorIntention {

    private BaseActor _actor;

    private AbstractIntention _intention;

    public ActorIntention(BaseActor actor)
    {
        _actor = actor;
        _intention = new IntentionIdle();
    }

    public void intentionThink()
    {
        if(_actor instanceof PlayableCharacter)
        {
            if(((PlayableCharacter) _actor).getClientListenerThread() == null)
                return;
        }

        switch(_intention.intentionType)
        {
            case INTENTION_IDLE:
                onIntentionIdle();
                break;
            case INTENTION_ACTION:
                onIntentionAction();
                break;
            case INTENTION_MOVE_TO: //TODO: Unused
                onIntentionMoveTo();
                break;
            case INTENTION_ATTACK:
                onIntentionAttack();
                break;
            default:
                onIntentionIdle();
        }
    }

    private void onIntentionIdle()
    {
        //Do nothing
    }

    public void setIntention(AbstractIntention intention) {

        if(_intention.intentionType == IntentionType.INTENTION_ATTACK && intention.intentionType != IntentionType.INTENTION_ATTACK)
        {
            GameTickController.getInstance().getIntentionThinkJob().deleteFromThink(_actor);
        }
        else if(intention.intentionType == IntentionType.INTENTION_ATTACK)
        {
            GameTickController.getInstance().getIntentionThinkJob().deleteFromThink(_actor);
            GameTickController.getInstance().getIntentionThinkJob().addToThink(_actor);
        }
        _intention = intention;
    }

    public AbstractIntention getIntention() {
        return _intention;
    }

    private void onIntentionAction()
    {
        if(_actor.isAttacking())
            return;

        IntentionAction _int = (IntentionAction) _intention;
        if(Math2d.calculateBetweenTwoActorsIn2d(_actor,_int.Target,true) > 200)
        {
//            if(!_actor.isMoving() || _int.ForceMoveToPawn) {
//                _int.ForceMoveToPawn = false;
//                if (_actor instanceof PlayableCharacter) {
//                    ((PlayableCharacter) _actor).moveToActor(_int.Target, 150);
//                }
//            }
        }
        else
        {
            _intention = new IntentionIdle();
            if(_int.Target instanceof NPCActor && _actor instanceof PlayableCharacter)
            {
                ((NPCActor) _int.Target).getNpcAi().onTalk((PlayableCharacter) _actor);
                //((PlayableCharacter) _actor).stopMoving();
            }
        }
    }

    private void onIntentionMoveTo()
    {
        IntentionMoveTo _int = (IntentionMoveTo) _intention;
        if(_actor.isAttacking())
            return;
        _intention = new IntentionIdle();
        if(_actor instanceof PlayableCharacter)
        {
            ((PlayableCharacter) _actor).moveToLocation(_int.LocationX, _int.LocationY, _int.LocationZ);
        }
    }

    private void onIntentionAttack() {
        int weaponAttackDistance = 150; //TODO: change this const to var
        IntentionAttack _int = (IntentionAttack) _intention;
        if (Math3d.calculateBetweenTwoActors(_actor, _int.Target, true) > weaponAttackDistance) {
            //not need anymore?
//            if (!_actor.isMoving() || _int.ForceMoveToPawn) {
//                _int.ForceMoveToPawn = false;
//                if (_actor instanceof PlayableCharacter) {
//                    ((PlayableCharacter) _actor).moveToActor(_int.Target,weaponAttackDistance-50);
//                }
//            }
        }
        else
        {
            //TODO: зачем здесь проверка на PlayableCharacter
            if(_actor instanceof PlayableCharacter)
            {
                if(_actor.isCanAttack()) {
                    //not need anymore?
//                _actor.setIsAttacking(true);
//                if(_actor.isMoving()) {
//                    ((PlayableCharacter) _actor).stopMoving();
//                }
                    _actor.setCanAttack(false);
                    ((PlayableCharacter) _actor).sendPacketAndBroadcastToNearbyPlayers(new Attack(_actor, _int.Target));
                    //No need anymore?
//                new Task(new AttackTask(_actor,_int.Target), (int) ((1 / 0.8f)*1000)); //TODO: 0.8f - attack speed, get from stats instead of const

                    new Task(new ResetAttackCooldown(_actor), (int) ((1 / 0.8f) * 1000)); //TODO: 0.8f - attack speed, get from stats instead of const
                }
            }
        }

    }
}
