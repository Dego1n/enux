package com.gameserver.model.ability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbilityTree {

    public int class_id;

    public Map<Integer, List<AcquirableAbility>> treeAbilities;

    public AbilityTree() {
        treeAbilities = new HashMap<>();
    }

    public static class AcquirableAbility {
        public int ability_id;
        public int level;
        public int required_sp;
    }

    public void addTreeAbilities(int level, List<AcquirableAbility> abilities){
        treeAbilities.put(level, abilities);
    }
}
