package ru.xewe.xonagic.client.ability.air;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import ru.xewe.xonagic.client.ability.Ability;
import ru.xewe.xonagic.client.ability.AbilityInfo;
import ru.xewe.xonagic.common.enums.TypeCast;

@AbilityInfo(
        name = "AirDash",
        displayName = "Air Dash",
        coolDown = 3,
        activations = {TypeCast.RightEmpty, TypeCast.RightBlock, TypeCast.RightEntity},
        color = TextFormatting.WHITE,
        combo = "XX"
)
public class AirDash extends Ability {

    public final static AirDash instance = new AirDash();

    @Override
    public Ability getInstance() {
        return AirDash.instance;
    }

    public void execute(EntityPlayer player) {
        super.execute(player);

        player.motionX = player.getLookVec().x;
        player.motionY = 0.2;
        player.motionZ = player.getLookVec().z;

        if (!player.world.isRemote) {
            for (byte i = 0; i <= 10; i++)
                ((WorldServer) player.world).spawnParticle(EnumParticleTypes.SPELL, (player.posX + (Math.sin(i * (Math.PI / 5))) * 0.5), player.posY + 0.3f, (player.posZ + (Math.cos(i * (Math.PI / 5))) * 0.5), 5, 0, 0, 0, 0f);
            player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_CREEPER_DEATH, SoundCategory.PLAYERS, 1, 3f);
            player.fallDistance = 0;
        }

    }

    @Override
    public boolean onUpdate(EntityPlayer player) {
        player.sendMessage(new TextComponentString(player.world.isRemote + ""));
        return false;
    }

}
