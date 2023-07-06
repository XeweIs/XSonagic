package ru.xewe.xonagic.common.ability.fire;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import ru.xewe.xonagic.client.particles.CustomParticle;
import ru.xewe.xonagic.client.particles.Flame;
import ru.xewe.xonagic.client.particles.Smoke;
import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.AbilityInfo;
import ru.xewe.xonagic.common.enums.ElementEnum;

@AbilityInfo(
        name = "FireRocket",
        displayName = "Fire Rocket",
        element = ElementEnum.Fire ,
        coolDown = 0,
        repeat = 0,
        activations = {},
        color = TextFormatting.WHITE,
        combo = ""
)
public class FireRocket extends Ability {
    Vec3d lastPos;
    CustomParticle flame;

    @Override
    public void execute(EntityPlayer player) {
        lastPos = player.getLookVec();
        Vec3d pos = lastPos.add(player.getPositionVector()).addVector(0, player.getEyeHeight(), 0);
        if (player.world.isRemote) {
            flame = new Flame(player.world, pos.x, pos.y, pos.z);

            flame.spawn();
        }else{
            player.world.playSound(null, pos.x, pos.y, pos.z, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1, 3f);
        }

        super.execute(player);
    }


    @Override
    public boolean onUpdate() {


        if(player.world.isRemote) {
            CustomParticle smoke = new Smoke(player.world, flame.getVec().x, flame.getVec().y, flame.getVec().z);

            smoke.spawn();

            flame.setVelocity(player.getLookVec().scale(0.8f));
        }


        return false;
    }

    @Override
    protected void onExit() {
        super.onExit();
    }
}
