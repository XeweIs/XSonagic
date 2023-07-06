package ru.xewe.xonagic.common.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.AbilityInfo;
import ru.xewe.xonagic.common.ability.AbilityManager;

public class CPacketCast extends AbstractPacket<CPacketCast> {
    String classPath;

    public CPacketCast() {
    }

    public CPacketCast(String classPath) {
        this.classPath = classPath;
    }

    @Override
    public void handleClient(CPacketCast message, EntityPlayerSP player) {
    }

    @Override
    public void handleServer(CPacketCast msg, EntityPlayerMP player) {
        Class<?> myClass;
        Ability newAbility;
        try {
            myClass = Class.forName(msg.classPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            newAbility = (Ability) myClass.newInstance();
            if(!AbilityManager.getAbilityManagerMP(player.getUniqueID()).coolDownAbilities
                    .toString().contains(newAbility.getClass().getAnnotation(AbilityInfo.class).name())){

                newAbility.execute(player);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        classPath = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, classPath);
    }
}
