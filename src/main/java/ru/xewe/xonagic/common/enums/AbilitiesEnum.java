package ru.xewe.xonagic.common.enums;

import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.AbilityInfo;

import java.util.ArrayList;
import java.util.List;

public enum AbilitiesEnum {
    AirDash(ru.xewe.xonagic.common.ability.air.AirDash.class), AirJump(ru.xewe.xonagic.common.ability.air.AirJump.class),
    AirSprint(ru.xewe.xonagic.common.ability.air.AirSprint.class), AirSuction(ru.xewe.xonagic.common.ability.air.AirSuction.class),
    AirWave(ru.xewe.xonagic.common.ability.air.AirWave.class),

    FireRocket(ru.xewe.xonagic.common.ability.fire.FireRocket.class);
    private final Class<? extends Ability> abilityClass;
    AbilitiesEnum(Class<? extends Ability> abilityClass){
        this.abilityClass = abilityClass;
    }

    public String getName() {
        return abilityClass.getAnnotation(AbilityInfo.class).name();
    }

    public Ability getInstance(){
        try {
            return abilityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static String[] getAllWithElement(ElementEnum element){
        List<String> abilities = new ArrayList<>();
        for(AbilitiesEnum ability : AbilitiesEnum.values()){
            if(ability.abilityClass.getAnnotation(AbilityInfo.class).element() == element){
                abilities.add(ability.getName());
            }
        }
        return abilities.toArray(new String[0]);
    }

    public static AbilitiesEnum valueOfCaseLess(String name) {
        for (AbilitiesEnum enumValue : values()) {
            if (enumValue.name().equalsIgnoreCase(name)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("No enum constant with name: " + name);
    }
}
