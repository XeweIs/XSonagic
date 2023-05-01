package ru.xewe.xonagic.common.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class CPacketSound extends AbstractPacket<CPacketSound>{
    SoundEvent sound;
    SoundCategory soundCategory;
    float x, y, z, volume, pitch;

    public CPacketSound() {
    }

    public CPacketSound(SoundEvent sound, SoundCategory soundCategory, float x, float y, float z, float volume, float pitch){
        this.sound = sound;
        this.soundCategory = soundCategory;
        this.x = x;
        this.y = y;
        this.z = z;
        this.volume = volume;
        this.pitch = pitch;
    }
    @Override
    public void handleClient(CPacketSound msg, EntityPlayerSP player) {
    }

    @Override
    public void handleServer(CPacketSound msg, EntityPlayerMP player) {
        player.world.playSound(null, msg.x, msg.y, msg.z, msg.sound, msg.soundCategory, msg.volume, msg.pitch);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.sound = SoundEvent.REGISTRY.getObjectById(buf.readInt());
        this.soundCategory = SoundCategory.values()[buf.readInt()];
        this.x = buf.readFloat();
        this.y = buf.readFloat();
        this.z = buf.readFloat();
        this.volume = buf.readFloat();
        this.pitch = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(SoundEvent.REGISTRY.getIDForObject(sound));
        buf.writeInt(soundCategory.ordinal());
        buf.writeFloat(x);
        buf.writeFloat(y);
        buf.writeFloat(z);
        buf.writeFloat(volume);
        buf.writeFloat(pitch);
    }
}
