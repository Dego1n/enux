package com.gameserver.packet.game2client;

import com.gameserver.model.ability.AbilityTree;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.IServerPacket;
import com.gameserver.packet.ServerPackets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AcquireSkillList extends AbstractSendablePacket implements IServerPacket {

    private AbilityTree abilityTree;
    private PlayableCharacter playableCharacter;

    public AcquireSkillList(PlayableCharacter pc, AbilityTree abilityTreeByClassId)
    {
        super();
        this.playableCharacter = pc;
        this.abilityTree = abilityTreeByClassId;
        build();
    }
    private void build()
    {
        List<AbilityTree.AcquirableAbility> acquirableAbilities = new ArrayList<>();
        for(Map.Entry<Integer, List<AbilityTree.AcquirableAbility>> tree : abilityTree.treeAbilities.entrySet())
        {
            if(tree.getKey() <= playableCharacter.getLevel())
            {
                for(AbilityTree.AcquirableAbility ability : tree.getValue())
                {
                    int learnedLevel = playableCharacter.getAbilityLevel(ability.ability_id);
                    if(learnedLevel == -1 && ability.level == 1)
                    {
                        acquirableAbilities.add(ability);
                    } else if(ability.level - learnedLevel == 1)
                    {
                        acquirableAbilities.add(ability);
                    }
                }
            }
        }
        writeH(ServerPackets.ACQUIRE_SKILL_LIST);
        writeD(acquirableAbilities.size()); //size
        for(AbilityTree.AcquirableAbility ability : acquirableAbilities)
        {
            writeD(ability.ability_id); //skill id
            writeD(ability.level); //skill Level
            writeD(ability.required_sp); //required sp
        }
    }
}
