package ru.xewe.xonagic.common.ability;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class AbilityManager {
    public static List<Ability> coolDownAbilities = new ArrayList<>();
    public static List<Ability> activateAbilities = new ArrayList<>();
    public static HashMap<UUID, List<Ability>> activateAbilitiesServer = new HashMap<>();
    public static HashMap<UUID, AbilityManager> aManagers = new HashMap<>();

    @SubscribeEvent
    @SideOnly(value = Side.CLIENT)
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().isGamePaused()) return;
        EntityPlayer player = Minecraft.getMinecraft().player;
        //CoolDown for Client
        for (int i = 0; i < coolDownAbilities.size(); i++) {
            Ability ability = coolDownAbilities.get(i);

            if (ability.coolDown <= 0) {
                AbilityManager.cancelCoolDown(ability);
            } else {
                ability.coolDown--;
            }

        }

        //Update for Client
        for (int i = 0; i < activateAbilities.size(); i++) {
            Ability ability = activateAbilities.get(i);

            if (player.isDead) {
                ability.onExit(player);
            }

            if (ability.onUpdateDefault(player)) {
                ability.onExit(player);
            }
        }
    }

    @SubscribeEvent
    public void onUpdateServer(TickEvent.ServerTickEvent event) {
        Set<UUID> keys = activateAbilitiesServer.keySet();
        for (UUID key : keys) {
            EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(key);
            for (int a = 0; a < activateAbilitiesServer.get(key).size(); a++) {
                Ability ability = activateAbilitiesServer.get(key).get(a);

                if (ability.onUpdateDefault(player)) {
                    ability.onExit(player);
                }
            }
        }
    }

    public static void activateAbility(Ability ability) {
        EntityPlayer player = ability.player;
        if (player.world.isRemote) {
            AbilityManager.activateAbilities.add(ability);
        } else {
            if (AbilityManager.activateAbilitiesServer.get(player.getUniqueID()) == null) {
                List<Ability> abilities = new ArrayList<>();
                abilities.add(ability);
                AbilityManager.activateAbilitiesServer.put(player.getUniqueID(), abilities);
            } else {
                AbilityManager.activateAbilitiesServer.get(player.getUniqueID()).add(ability);
            }
        }
    }

    public static void deactivateAbility(Ability ability) {
        EntityPlayer player = ability.player;
        if (player.world.isRemote)
            AbilityManager.activateAbilities.remove(ability);
        else {
            AbilityManager.activateAbilitiesServer.get(player.getUniqueID()).remove(ability);
        }
    }

    public static void imposeCoolDown(Ability ability) {
        AbilityInfo info = ability.getClass().getAnnotation(AbilityInfo.class);
        ability.setCoolDown(info.coolDown());
        AbilityManager.coolDownAbilities.add(ability);
    }

    public static void cancelCoolDown(Ability ability) {
        AbilityManager.coolDownAbilities.remove(ability);
    }
}
