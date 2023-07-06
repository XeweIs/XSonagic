package ru.xewe.xonagic.common.ability.air;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.AbilityInfo;
import ru.xewe.xonagic.common.enums.ElementEnum;
import ru.xewe.xonagic.common.enums.TypeCast;

@AbilityInfo(
        name = "AirWave",
        displayName = "Air Wave",
        element = ElementEnum.Air,
        coolDown = 0,
        repeat = 1,
        activations = {TypeCast.RightEmpty, TypeCast.RightBlock, TypeCast.RightEntity},
        color = TextFormatting.WHITE,
        combo = "ZX"
)
public class AirWave extends Ability {

    @Override
    public boolean onUpdate() {
        if (!player.world.isRemote) {
            for (byte i = 0; i <= 10; i++) {
                float x = (float)(player.posX + (Math.sin(i * (Math.PI / 5))) * 0.5);
                float y = (float)player.posY + 0.3f;
                float z = (float)(player.posZ + (Math.cos(i * (Math.PI / 5))) * 0.5);

                ((WorldServer) player.world).spawnParticle(EnumParticleTypes.SPELL, x, y, z,
                        5, 0, 0, 0, 0f);
            }
        }

        return false;
    }
}
