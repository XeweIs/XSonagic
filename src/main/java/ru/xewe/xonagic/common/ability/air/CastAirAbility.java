package ru.xewe.xonagic.common.ability.air;

import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.CastAbility;

public class CastAirAbility extends CastAbility {
    public static CastAirAbility instance = new CastAirAbility();
    @Override
    public Ability[] getAbilities() {
        return new Ability[]{AirDash.instance, AirSprint.instance};
    }
}
