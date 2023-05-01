package ru.xewe.xonagic.client.ability.fire;

import ru.xewe.xonagic.client.ability.Ability;
import ru.xewe.xonagic.client.ability.CastAbility;

public class CastFireAbility extends CastAbility {
    public static CastFireAbility instance = new CastFireAbility();
    @Override
    protected Ability[] getAbilities() {
        return new Ability[]{};
    }
}
