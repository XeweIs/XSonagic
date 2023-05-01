package ru.xewe.xonagic.client.ability.water;

import ru.xewe.xonagic.client.ability.Ability;
import ru.xewe.xonagic.client.ability.CastAbility;

public class CastWaterAbility extends CastAbility {
    public static CastWaterAbility instance = new CastWaterAbility();
    @Override
    protected Ability[] getAbilities() {
        return new Ability[]{};
    }
}
