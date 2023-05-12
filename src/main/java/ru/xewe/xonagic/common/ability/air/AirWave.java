package ru.xewe.xonagic.common.ability.air;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.AbilityInfo;
import ru.xewe.xonagic.common.enums.TypeCast;

@AbilityInfo(
        name = "AirWave",
        displayName = "Air Wave",
        coolDown = 0,
        repeat = 0,
        activations = {TypeCast.RightEmpty, TypeCast.RightBlock, TypeCast.RightEntity},
        color = TextFormatting.WHITE,
        combo = "ZX"
)
public class AirWave extends Ability {
    @Override
    public void execute(EntityPlayer player) {
        if (!player.world.isRemote) {
            int radius = 2;
            for(float i = 0; i <= 6.2; i += 0.4) {
                double x = player.posX + radius * Math.sin(i);
                double y = player.posY;
                double z = player.posZ + radius * Math.cos(i);

                ((WorldServer) player.world).spawnParticle(EnumParticleTypes.SPELL, x, y, z, 1, 0, 0, 0, 0f);

            }
        }
        super.execute(player);
    }

    @Override
    protected boolean onUpdate(EntityPlayer player) {
        return false;
    }
}
