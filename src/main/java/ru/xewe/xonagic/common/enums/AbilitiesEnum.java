package ru.xewe.xonagic.common.enums;

import net.minecraft.entity.player.EntityPlayer;
import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.AbilityInfo;
import ru.xewe.xonagic.common.data.AbilitiesData;
import ru.xewe.xonagic.common.data.ElementData;

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

    public AbilityInfo getInfo(){
        return abilityClass.getAnnotation(AbilityInfo.class);
    }

    public Ability getInstance(){
        Ability ability;
        try {
            ability = abilityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return ability;
    }

    public static List<String> getAllWithElementToString(ElementEnum element){
        List<String> abilities = new ArrayList<>();
        for(AbilitiesEnum ability : AbilitiesEnum.values()){
            if(ability.abilityClass.getAnnotation(AbilityInfo.class).element() == element){
                abilities.add(ability.getInfo().name());
            }
        }
        return abilities;
    }

    public static List<Ability> getAllWithPlayer(EntityPlayer player){
        List<Ability> abilities = new ArrayList<>();
        ElementEnum element = ElementData.getElement(player);
        for(AbilitiesEnum ability : AbilitiesEnum.values()){
            if(ability.abilityClass.getAnnotation(AbilityInfo.class).element() == element
            && AbilitiesData.get(player).contains(ability.getInfo().name())){
                abilities.add(ability.getInstance());
            }
        }
        return abilities;
    }

    public static AbilitiesEnum valueOfCaseLess(String name) {
        for (AbilitiesEnum enumValue : AbilitiesEnum.values()) {
            if (enumValue.name().equalsIgnoreCase(name)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("No enum constant with name: " + name);
    }
}
