package ru.xewe.xonagic.common.ability;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.xewe.xonagic.common.data.AbilitiesData;
import ru.xewe.xonagic.common.enums.AbilitiesEnum;
import ru.xewe.xonagic.common.packets.SPacketAbilityManagerReset;
import ru.xewe.xonagic.common.packets.SPacketSynchAbilityManager;
import ru.xewe.xonagic.common.registry.NetworkHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class AbilityManagerEvents {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if(event.phase.equals(TickEvent.Phase.END)) return;

        if (Minecraft.getMinecraft().isGamePaused() || Minecraft.getMinecraft().player == null ) return;
        AbilityManager.getAbilityManager(Minecraft.getMinecraft().player).onUpdate();
    }

    @SubscribeEvent
    public void onUpdateServer(TickEvent.ServerTickEvent event) {
        if(event.phase.equals(TickEvent.Phase.END)) return;

        Set<UUID> keys = AbilityManager.abilityManagerMP.keySet();
        for (UUID key : keys) {
            AbilityManager.getAbilityManagerMP(key).onUpdateServer();
        }
    }

    public static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        if(!event.player.world.isRemote){
            AbilityManager.abilityManagerMP.put(event.player.getUniqueID(), new AbilityManager());
            NetworkHandler.NETWORK.sendTo(new SPacketAbilityManagerReset(), (EntityPlayerMP) event.player);

            if(AbilitiesData.get(event.player).size() != 0) {
                List<Ability> abilities = new ArrayList<>();
                for (String abilityName : AbilitiesData.get(event.player)) {
                    Ability ability = AbilitiesEnum.valueOf(abilityName).getInstance();
                    abilities.add(ability);
                }
                AbilityManager.getAbilityManagerMP(event.player.getUniqueID()).abilities.addAll(abilities);
                NetworkHandler.NETWORK.sendTo(new SPacketSynchAbilityManager(SPacketSynchAbilityManager.Option.add, AbilitiesData.get(event.player).toArray(new String[0])), (EntityPlayerMP) event.player);
            }
        }
    }

}

