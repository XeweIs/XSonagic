package ru.xewe.xonagic.common.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;

public class CPacketParticle extends AbstractPacket<CPacketParticle> {
    EnumParticleTypes particle;
    float x, y, z, xv, yv, zv, speed;
    int count;

    public CPacketParticle() {
    }
    public CPacketParticle(EnumParticleTypes particle, float x, float y, float z){
        this(particle, x, y, z, 0, 0, 0, 0, 1);
    }
    public CPacketParticle(EnumParticleTypes particle, float x, float y, float z, int count){
        this(particle, x, y, z, 0, 0, 0, 0, count);
    }

    public CPacketParticle(EnumParticleTypes particle, float x, float y, float z, float xv, float yv, float zv, float speed, int count){
        this.particle = particle;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xv = xv;
        this.yv = yv;
        this.zv = zv;
        this.speed = speed;
        this.count = count;
    }

    @Override
    public void handleClient(CPacketParticle msg, EntityPlayerSP player) {

    }

    @Override
    public void handleServer(CPacketParticle msg, EntityPlayerMP player) {
        ((WorldServer)player.world).spawnParticle(msg.particle, msg.x, msg.y, msg.z, msg.count, msg.xv, msg.yv, msg.zv, msg.speed);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.particle = EnumParticleTypes.values()[buf.readInt()];
        this.x = buf.readFloat();
        this.y = buf.readFloat();
        this.z = buf.readFloat();
        this.xv = buf.readFloat();
        this.yv = buf.readFloat();
        this.zv = buf.readFloat();
        this.speed = buf.readFloat();
        this.count = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(particle.ordinal());
        buf.writeFloat(x);
        buf.writeFloat(y);
        buf.writeFloat(z);
        buf.writeFloat(xv);
        buf.writeFloat(yv);
        buf.writeFloat(zv);
        buf.writeFloat(speed);
        buf.writeInt(count);
    }
}
