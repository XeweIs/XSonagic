package ru.xewe.xonagic.common.ability.air;

import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.CastAbility;

public class CastAirAbility extends CastAbility {
    public static CastAirAbility instance = new CastAirAbility();
    Ability[] abilities = {new AirDash(), new AirSprint(), new AirSuction(), new AirJump(), new AirWave()};
    @Override
    public Ability[] getAbilities() {
        return abilities;
    }
}
