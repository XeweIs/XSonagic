package ru.xewe.xonagic.common.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.xewe.xonagic.common.ability.Ability;

import java.io.*;

public class CPacketSpell extends AbstractPacket<CPacketSpell>{
    Ability clazz;
    public CPacketSpell(){}
    public CPacketSpell(Ability clazz){
        this.clazz = clazz;
    }
    @Override
    public void handleClient(CPacketSpell message, EntityPlayerSP player) {

    }

    @Override
    public void handleServer(CPacketSpell msg, EntityPlayerMP player) {
        msg.clazz.execute(player);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        byte[] bytes = new byte[buf.readableBytes()];
        buf.duplicate().readBytes(bytes);

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            clazz = (Ability)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            objectOutputStream.writeObject(clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] bytes = byteArrayOutputStream.toByteArray();

        buf.writeBytes(bytes);
    }
}
