package ru.xewe.xonagic.common.ability.air;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.AbilityInfo;
import ru.xewe.xonagic.common.enums.ElementEnum;
import ru.xewe.xonagic.common.enums.TypeCast;

@AbilityInfo(
        name = "AirJump",
        displayName = "Air Jump",
        element = ElementEnum.Air,
        coolDown = 4,
        repeat = 5,
        activations = {TypeCast.Jump},
        color = TextFormatting.WHITE,
        combo = "XX"
)
public class AirJump extends Ability {
    @Override
    public void execute(EntityPlayer player) {
        super.execute(player);
    }

    @Override
    public boolean onUpdate() {

        if (!player.world.isRemote) {
            ((WorldServer) player.world).spawnParticle(EnumParticleTypes.CLOUD,
                    player.posX, player.posY, player.posZ,
                    2, 0, 0, 0, 0f);
            if(repeat % 2 == 0) {
                player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BREATH, SoundCategory.PLAYERS, 1, 2f);
            }
        }

        return false;
    }

    @Override
    protected void onExit() {
        if(!player.world.isRemote){
            player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_CREEPER_DEATH, SoundCategory.PLAYERS, 1, 3f);
        }

        if(!player.isSneaking()) {
            player.motionY = 1;
        } else {
            player.motionY = 1.2;
        }

        super.onExit();
    }
}
