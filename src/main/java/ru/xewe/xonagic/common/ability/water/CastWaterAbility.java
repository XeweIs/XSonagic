package ru.xewe.xonagic.common.ability.water;

import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.CastAbility;

public class CastWaterAbility extends CastAbility {
    public static CastWaterAbility instance = new CastWaterAbility();
    Ability[] abilities = {};
    @Override
    public Ability[] getAbilities() {
        return abilities;
    }
}
