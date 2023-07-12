package ru.xewe.xonagic.common.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import ru.xewe.xonagic.client.gui.TextGui;
import ru.xewe.xonagic.client.keyboard.KeyPressed;
import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.AbilityManager;

public class SPacketCastAbility extends AbstractPacket<SPacketCastAbility> {
    String nameAbility;
    public SPacketCastAbility(){}
    public SPacketCastAbility(String nameAbility){
        this.nameAbility = nameAbility;
    }
    @Override
    public void handleClient(SPacketCastAbility msg, EntityPlayer player) {
        for(Ability ability : AbilityManager.getAbilityManagerSP().abilities){
            if(!ability.getInfo().name().equals(msg.nameAbility)) continue;

            ability.execute(player);
            TextGui.comboText = "";
            KeyPressed.time = 0;
        }
    }

    @Override
    public void handleServer(SPacketCastAbility msg, EntityPlayerMP player) {}

    @Override
    public void fromBytes(ByteBuf buf) {
        nameAbility = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, nameAbility);
    }
}
