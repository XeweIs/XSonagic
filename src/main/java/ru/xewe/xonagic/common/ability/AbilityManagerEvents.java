package ru.xewe.xonagic.common.ability;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    @SubscribeEvent
    public void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        AbilityManager.abilityManagerMP.put(event.player.getUniqueID(), new AbilityManager());
    }

}

