package ru.xewe.xonagic.client.ability.air;

import ru.xewe.xonagic.client.ability.Ability;
import ru.xewe.xonagic.client.ability.CastAbility;

public class CastAirAbility extends CastAbility {
    public static CastAirAbility instance = new CastAirAbility();
    @Override
    public Ability[] getAbilities() {
        return new Ability[]{AirDash.instance, AirSprint.instance};
    }
}
