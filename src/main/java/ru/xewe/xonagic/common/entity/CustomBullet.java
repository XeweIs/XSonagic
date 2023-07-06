package ru.xewe.xonagic.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class CustomBullet extends EntityFireball {
    public CustomBullet(World world) {
        super(world);
    }

    public CustomBullet(World world, EntityLivingBase shooter) {
        super(world);
        this.shootingEntity = shooter;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null) {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.shootingEntity), 5.0f); // нанести урон
        }

        if (!this.world.isRemote) {
            this.setDead();
        }
    }
}
