package ru.xewe.xonagic.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CustomParticle extends Particle {
    private long creationTime;
    public CustomParticle(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public void setGravity(boolean gravity){
        this.particleGravity = gravity ? 1 : 0;
    }

    public void setCollision(boolean collision){
        this.canCollide = collision;
    }

    public void setVelocity(double x, double y, double z){
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }

    public void setVelocity(Vec3d vec){
        this.motionX = vec.x;
        this.motionY = vec.y;
        this.motionZ = vec.z;
    }

    public Vec3d getVec(){
        return new Vec3d(prevPosX, prevPosY, prevPosZ);
    }
    public int getAge(){return this.particleAge; }
    public long getCreationTime(){return this.creationTime;}

    public void spawn(){
        Minecraft.getMinecraft().effectRenderer.addEffect(this);
        this.creationTime = System.currentTimeMillis();
    }
}
