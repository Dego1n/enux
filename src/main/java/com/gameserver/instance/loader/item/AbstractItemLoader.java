/*
 * Author: Dego1n
 * 3.12.2019
 */

package com.gameserver.instance.loader.item;

import com.gameserver.template.stats.StatModifier;
import com.gameserver.template.stats.enums.StatModifierOperation;
import com.gameserver.template.stats.enums.StatModifierType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractItemLoader {
    protected static List<StatModifier> loadStatModifiers(Map<String, Object> item)
    {
        List<Map<String,Object>> stats = (List<Map<String, Object>>) item.get("stats");
        if(stats == null)
            return new ArrayList<>();

        List<StatModifier> statModifiers = new ArrayList<>();
        for (Map<String, Object> stat : stats) {
            StatModifierType statModifierType = null;
            StatModifierOperation statModifierOperation = null;
            switch ((String) stat.get("type")) {
                case "pAtk":
                    statModifierType = StatModifierType.PHYSICAL_ATTACK;
                    break;
                case "pDef":
                    statModifierType = StatModifierType.PHYSICAL_DEFENCE;
                    break;
                case "critRate":
                    statModifierType = StatModifierType.CRIT_RATE;
                    break;
            }
            switch ((String) stat.get("modifier_type")) {
                case "add":
                    statModifierOperation = StatModifierOperation.ADD;
                    break;
                case "multiply":
                    statModifierOperation = StatModifierOperation.MULTIPLY;
                    break;
            }
            double value = (double) stat.get("modifier");
            statModifiers.add(new StatModifier(statModifierOperation, statModifierType, value));
        }
        return statModifiers;
    }
}
