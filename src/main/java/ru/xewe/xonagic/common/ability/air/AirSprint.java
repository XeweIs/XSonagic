package ru.xewe.xonagic.common.ability.air;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.AbilityInfo;
import ru.xewe.xonagic.common.enums.TypeCast;
import ru.xewe.xonagic.utils.UtilVector;

import java.util.Random;

@AbilityInfo(
        name = "AirSprint",
        displayName = "Air Sprint",
        coolDown = 2,
        repeat = 30,
        activations = {TypeCast.RightEmpty, TypeCast.RightBlock, TypeCast.RightEntity},
        color = TextFormatting.WHITE,
        combo = "XXXX"
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
    protected boolean onUpdate(EntityPlayer player) {
        if (player.onGround) {
            player.motionX = UtilVector.getVectorForRotation(0, player.rotationYaw).x;
            player.motionZ = UtilVector.getVectorForRotation(0, player.rotationYaw).z;
            player.setInvisible(true);
            if (!player.world.isRemote) {
                int a = repeat % 2 == 0 ? 1 : -1;
                Vec3d vec = UtilVector.getVectorForRotation(0, player.rotationYaw).scale(a).rotateYaw(1.5707963705062866f).add(player.getPositionVector());
                player.world.playSound(null, vec.x, player.posY, vec.z, SoundEvents.BLOCK_CLOTH_STEP, SoundCategory.PLAYERS, 1, new Random().nextInt(2));
                for (byte i = 0; i < 10; i++) {
                    float x = (float) (vec.x + (Math.sin(i * (Math.PI / 5))) * 0.5);
                    float y = (float) player.posY;
                    float z = (float) (vec.z + (Math.cos(i * (Math.PI / 5))) * 0.5);
                    ((WorldServer) player.world).spawnParticle(EnumParticleTypes.SNOWBALL, x, y, z, 3, 0, 0, 0, 0f);
                    ((WorldServer) player.world).spawnParticle(EnumParticleTypes.SPELL, x, y, z, 3, 0, 0, 0, 0f);
                }
            }
        }else{
            if(!player.isPotionActive(Potion.getPotionFromResourceLocation("invisibility"))) {
                player.setInvisible(false);
            }
        }
        return false;
    }

    @Override
    protected void onExit(EntityPlayer player) {
        super.onExit(player);
        if(!player.isPotionActive(Potion.getPotionFromResourceLocation("invisibility"))) {
            player.setInvisible(false);
        }
    }
}
