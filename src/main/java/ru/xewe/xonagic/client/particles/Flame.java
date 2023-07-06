package ru.xewe.xonagic.client.particles;

import net.minecraft.world.World;

public class Flame extends CustomParticle{
    public Flame(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.setParticleTextureIndex(48);
        this.setMaxAge(20);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.particleScale = (particleMaxAge - particleAge)/10f;
    }
}
