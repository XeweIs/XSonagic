package ru.xewe.xonagic.common.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.AbilityManager;
import ru.xewe.xonagic.common.registry.NetworkHandler;

public class CPacketCastAbility extends AbstractPacket<CPacketCastAbility> {
    String abilityName;

    public CPacketCastAbility() {
    }

    public CPacketCastAbility(String abilityName) {
        this.abilityName = abilityName;
    }

    @Override
    public void handleClient(CPacketCastAbility msg, EntityPlayer player) {
    }

    @Override
    public void handleServer(CPacketCastAbility msg, EntityPlayerMP player) {
        for(Ability ability : AbilityManager.getAbilityManagerMP(player.getUniqueID()).abilities){
            if(!ability.getInfo().name().equals(msg.abilityName)) continue;

            if(ability.allowedExecute()){
                NetworkHandler.NETWORK.sendTo(new SPacketCastAbility(msg.abilityName), player);
                ability.execute(player);
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        abilityName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, abilityName);
    }
}
