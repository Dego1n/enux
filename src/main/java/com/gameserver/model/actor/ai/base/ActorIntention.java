package com.gameserver.model.actor.ai.base;

import com.gameserver.model.actor.BaseActor;
import com.gameserver.model.actor.NPCActor;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.model.actor.ai.base.intention.*;
import com.gameserver.tick.GameTickController;
import com.gameserver.util.math.xy.Math2d;
import com.gameserver.util.math.xyz.Math3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActorIntention {
    private static final Logger log = LoggerFactory.getLogger(ActorIntention.class);
    private final BaseActor _actor;

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
            if(!((PlayableCharacter) _actor).isConnected())
            {
                log.warn("Trying to do intentionThink on disconnected character: "+_actor.getName());
                return;
            }
        }
        switch(_intention.intentionType)
        {
            case INTENTION_ACTION:
                onIntentionAction();
                break;
            case INTENTION_MOVE_TO: //TODO: Unused
                onIntentionMoveTo();
                break;
            case INTENTION_ATTACK:
                onIntentionAttack();
                break;
            case INTENTION_CAST:
                onIntentionCast();
                break;
            default:
                onIntentionIdle();
        }
    }

    @SuppressWarnings("EmptyMethod")
    private void onIntentionIdle()
    {
        //Do nothing
    }

    public void setIntention(AbstractIntention intention) {
        if(intention == null)
        {
            GameTickController.getInstance().getIntentionThinkJob().deleteFromThink(_actor);
            return;
        }
        if(_intention.intentionType == IntentionType.INTENTION_ATTACK && intention.intentionType != IntentionType.INTENTION_ATTACK)
        {
            GameTickController.getInstance().getIntentionThinkJob().deleteFromThink(_actor);
        }
        else if(intention.intentionType == IntentionType.INTENTION_ATTACK || intention.intentionType == IntentionType.INTENTION_CAST)
        {
            GameTickController.getInstance().getIntentionThinkJob().deleteFromThink(_actor);
            GameTickController.getInstance().getIntentionThinkJob().addToThink(_actor);
        }
        else if(intention.intentionType == IntentionType.INTENTION_IDLE)
        {
            GameTickController.getInstance().getIntentionThinkJob().deleteFromThink(_actor);
        }
        _intention = intention;
    }

    public AbstractIntention getIntention() {
        return _intention;
    }

    private void onIntentionAction()
    {
        IntentionAction _int = (IntentionAction) _intention;
        if(Math2d.calculateBetweenTwoActorsIn2d(_actor,_int.Target,true) < 200)
        {
            _intention = new IntentionIdle();
            if(_int.Target instanceof NPCActor && _actor instanceof PlayableCharacter)
            {
                ((NPCActor) _int.Target).getNpcAi().onTalk((PlayableCharacter) _actor);
            }
        }
    }

    private void onIntentionMoveTo()
    {
        IntentionMoveTo _int = (IntentionMoveTo) _intention;
        _intention = new IntentionIdle();
        if(_actor instanceof PlayableCharacter)
        {
            ((PlayableCharacter) _actor).moveToLocation(_int.LocationX, _int.LocationY, _int.LocationZ);
        }
    }

    private void onIntentionAttack() {
        IntentionAttack _int = (IntentionAttack) _intention;
        if(_int.Target.isDead())
        {
            setIntention(new IntentionIdle());
            return;
        }
        int weaponAttackDistance = 150; //TODO: change this const to var
        if (_actor instanceof PlayableCharacter) {
            if (Math3d.calculateBetweenTwoActors(_actor, _int.Target, true) < weaponAttackDistance) {

                if (_actor.isCanAttack()) {
                    _actor.attack(_int.Target);
                }
            }
        }
        else if(_actor instanceof NPCActor)
        {
            _actor.getAi().intentionAttackThink();
        }
    }
    private void onIntentionCast() {
        IntentionCast _int = (IntentionCast) _intention;
        if(_int.Target.isDead())
        {
            setIntention(new IntentionIdle());
            return;
        }
        if(_actor instanceof PlayableCharacter)
        {
            if(_actor.isCanAttack())
            {
                _actor.useAbility(_int.Target,_int.ability);
            }
        }
    }
}
