package ru.xewe.xonagic.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class CustomBullet extends EntityThrowable {
    public CustomBullet(World worldIn) {
        super(worldIn);
    }

    public CustomBullet(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public CustomBullet(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null) {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 5.0f); // нанести урон
        }

        if (!this.world.isRemote) {
            this.setDead();
        }
    }
}
