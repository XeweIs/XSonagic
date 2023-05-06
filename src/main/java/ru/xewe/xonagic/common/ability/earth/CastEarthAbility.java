package ru.xewe.xonagic.common.ability.earth;

import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.CastAbility;

public class CastEarthAbility extends CastAbility {
    public static CastEarthAbility instance = new CastEarthAbility();
    @Override
    protected Ability[] getAbilities() {
        return new Ability[]{};
    }
}
