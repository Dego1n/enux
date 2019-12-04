/*
 * Author: Dego1n
 * 3.12.2019
 */

package com.gameserver.template.stats;

import com.gameserver.template.stats.enums.StatModifierOperation;
import com.gameserver.template.stats.enums.StatModifierType;

public class StatModifier {
    StatModifierOperation operation;
    StatModifierType type;
    double value;

    public StatModifier(StatModifierOperation operation, StatModifierType type, double value) {
        this.operation = operation;
        this.type = type;
        this.value = value;
    }

    public StatModifierOperation getOperation() {
        return operation;
    }

    public StatModifierType getType() {
        return type;
    }

    public double getValue() {
        return value;
    }
}
