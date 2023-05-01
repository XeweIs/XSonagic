package ru.xewe.xonagic.client.ability.air;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import ru.xewe.xonagic.client.ability.Ability;
import ru.xewe.xonagic.client.ability.AbilityInfo;

@AbilityInfo(
        name = "AirSprint",
        displayName = "Air Sprint",
        coolDown = 2,
        activations = {},
        color = TextFormatting.WHITE,
        combo = "X"
)
public class AirSprint extends Ability {
    public static AirSprint instance = new AirSprint();
    @Override
    public Ability getInstance() {
        return AirSprint.instance;
    }
    @Override
    public void execute(EntityPlayer player) {
        super.execute(player);
    }
    @Override
    public boolean onUpdate(EntityPlayer player) {
        return false;
    }
}
