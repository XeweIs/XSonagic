package ru.xewe.xonagic.common.ability.air;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.AbilityInfo;
import ru.xewe.xonagic.common.enums.ElementEnum;
import ru.xewe.xonagic.common.enums.TypeCast;
import ru.xewe.xonagic.common.packets.SPacketPlayerMotion;
import ru.xewe.xonagic.common.registry.NetworkHandler;

@AbilityInfo(
        name = "AirDash",
        displayName = "Air Dash",
        coolDown = 3,
        repeat = 0,
        activations = {TypeCast.RightEmpty, TypeCast.RightBlock, TypeCast.RightEntity},
        color = TextFormatting.WHITE,
        combo = "X",
        element = ElementEnum.Air
)
public class AirDash extends Ability {

    public void execute(EntityPlayer player) {
        if (!player.world.isRemote) {
            for (byte i = 0; i <= 10; i++) {
                ((WorldServer) player.world).spawnParticle(EnumParticleTypes.SPELL,
                        (player.posX + (Math.sin(i * (Math.PI / 5))) * 0.5),
                        player.posY + 0.3f,
                        (player.posZ + (Math.cos(i * (Math.PI / 5))) * 0.5), 5, 0, 0, 0, 0f);
            }
            player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_CREEPER_DEATH, SoundCategory.PLAYERS, 1, 3f);
            NetworkHandler.NETWORK.sendTo(
                    new SPacketPlayerMotion((float)player.getLookVec().x, 0.2f, (float)player.getLookVec().z), (EntityPlayerMP) player);
        }
        player.fallDistance = 0;

        super.execute(player);
    }

    @Override
    public boolean onUpdate() {
        return false;
    }

}
