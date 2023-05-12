package ru.xewe.xonagic.common.ability.earth;

import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.CastAbility;

public class CastEarthAbility extends CastAbility {
    public static CastEarthAbility instance = new CastEarthAbility();
    Ability[] abilities = {};
    @Override
    public Ability[] getAbilities() {
        return abilities;
    }
}
