package ru.xewe.xonagic.client.particles;

import net.minecraft.world.World;

public class Smoke extends CustomParticle{
    public Smoke(World world, double x, double y, double z) {
        super(world, x, y, z);

        this.setParticleTextureIndex(7);
        this.setMaxAge(30);
        this.setRBGColorF(64f + rand.nextInt(10), 64f + rand.nextInt(10), 64f + rand.nextInt(10));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.particleScale = (particleMaxAge - particleAge)/10f - 4f;
        this.setParticleTextureIndex((int) ((particleMaxAge - particleAge) / (particleMaxAge / 8f)));
    }
}
