package ru.xewe.xonagic.common.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import ru.xewe.xonagic.common.ability.AbilityManager;

public class SPacketAbilityManagerReset extends AbstractPacket<SPacketAbilityManagerReset> {
    public SPacketAbilityManagerReset(){}
    @Override
    public void handleClient(SPacketAbilityManagerReset msg, EntityPlayer player) {
        AbilityManager.abilityManagerSP = new AbilityManager();
    }

    @Override
    public void handleServer(SPacketAbilityManagerReset msg, EntityPlayerMP player) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }
}
