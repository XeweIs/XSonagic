package ru.xewe.xonagic.common.ability.fire;

import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.CastAbility;

public class CastFireAbility extends CastAbility {
    public static CastFireAbility instance = new CastFireAbility();
    Ability[] abilities = {new FireRocket()};
    @Override
    public Ability[] getAbilities() {
        return abilities;
    }
}
