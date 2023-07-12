package ru.xewe.xonagic.common.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;

public class SPacketPlayerMotion extends AbstractPacket<SPacketPlayerMotion>{
    float x;
    float y;
    float z;
    public SPacketPlayerMotion(){
    }

    public SPacketPlayerMotion(Vec3d vec){
        this.x = (float)vec.x;
        this.y = (float)vec.y;
        this.z = (float)vec.z;
    }

    public SPacketPlayerMotion(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void handleClient(SPacketPlayerMotion msg, EntityPlayer player) {
        player.motionX = msg.x;
        player.motionY = msg.y;
        player.motionZ = msg.z;
    }

    @Override
    public void handleServer(SPacketPlayerMotion msg, EntityPlayerMP player) {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readFloat();
        y = buf.readFloat();
        z = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(x);
        buf.writeFloat(y);
        buf.writeFloat(z);
    }
}
