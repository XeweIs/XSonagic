package ru.xewe.xonagic.common.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import ru.xewe.xonagic.common.ability.Ability;
import ru.xewe.xonagic.common.ability.AbilityManager;
import ru.xewe.xonagic.common.enums.AbilitiesEnum;

import java.util.ArrayList;
import java.util.List;

public class SPacketSynchAbilityManager extends AbstractPacket<SPacketSynchAbilityManager> {
    Option option;
    String[] abilityNames = new String[]{};

    public enum Option {
        add, remove, reset
    }

    public SPacketSynchAbilityManager() {
    }

    public SPacketSynchAbilityManager(Option option, String... abilityNames) {
        this.option = option;
        this.abilityNames = abilityNames;
    }

    @Override
    public void handleClient(SPacketSynchAbilityManager msg, EntityPlayer player) {
        List<Ability> mesAbility = new ArrayList<>();
        AbilityManager abilityManager = AbilityManager.getAbilityManagerSP();
        switch (msg.option) {
            case add:
                for(String abilityName : msg.abilityNames) {
                    mesAbility.add(AbilitiesEnum.valueOfCaseLess(abilityName).getInstance());
                }
                abilityManager.abilities.addAll(mesAbility);
                break;
            case remove:
                for(String abilityName : msg.abilityNames) {
                    mesAbility.add(AbilitiesEnum.valueOfCaseLess(abilityName).getInstance());
                }
                abilityManager.abilities.removeIf(ability -> mesAbility.stream()
                        .map(a -> a.getInfo().name())
                        .anyMatch(name -> name.equals(ability.getInfo().name())));
                break;
            case reset:
                abilityManager.abilities.clear();
        }
    }

    @Override
    public void handleServer(SPacketSynchAbilityManager msg, EntityPlayerMP player) {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        option = Option.values()[buf.readInt()];
        if(option != Option.reset) {
            byte lengthAbilityName = buf.readByte();
            abilityNames = new String[lengthAbilityName];
            for (int i = 0; i < lengthAbilityName; i++) {
                this.abilityNames[i] = ByteBufUtils.readUTF8String(buf);
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(option.ordinal());

        if(option != Option.reset) {
            buf.writeByte(abilityNames.length);
            for (String name : this.abilityNames) {
                ByteBufUtils.writeUTF8String(buf, name);
            }
        }
    }
}
