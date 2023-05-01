package ru.xewe.xonagic.client.ability;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.xewe.xonagic.client.gui.TextGui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UpdateAbilities {

    public static List<Ability> coolDownAbilities = new ArrayList<>();
    public static List<Ability> activateAbilities = new ArrayList<>();
    public static HashMap<UUID, List<Ability>> activateAbilitiesServer = new HashMap<>();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().isGamePaused()) return;
        EntityPlayer player = Minecraft.getMinecraft().player;
        //CoolDown for Client
        for (int i = 0; i < coolDownAbilities.size(); i++) {
            Ability ability = coolDownAbilities.get(i);

            ability.coolDown--;
            StringBuilder pretext = new StringBuilder()
                    .append(ability.color)
                    .append(TextFormatting.STRIKETHROUGH)
                    .append(ability.displayName)
                    .append(" ")
                    .append(((ability.coolDown / 40) + 2)).append(TextFormatting.RESET).append("  ");
            StringBuilder initext = new StringBuilder()
                    .append(ability.color)
                    .append(TextFormatting.STRIKETHROUGH)
                    .append(ability.displayName)
                    .append(" ")
                    .append(((ability.coolDown / 40) + 1))
                    .append(TextFormatting.RESET)
                    .append("  ");

            if (!TextGui.coolDownText.contains(initext)) {
                TextGui.coolDownText = TextGui.coolDownText.replace(pretext, initext);
            }

            if (ability.coolDown == 0) {
                TextGui.coolDownText = TextGui.coolDownText.replace(initext, "");

                ability.cancelCoolDown();
            }

        }

        //Update for Client
        for (int i = 0; i < activateAbilities.size(); i++) {
            Ability ability = activateAbilities.get(i);

            if (player.isDead)
                ability.onExit(player);

            if (!ability.onUpdate(player)) {
                ability.onExit(player);
            }
        }
    }

    static List<EntityPlayerMP> listPlayer = new ArrayList<>();
    @SubscribeEvent
    public void onUpdateServer(TickEvent.ServerTickEvent event) {
        //Update Server
//        List<EntityPlayerMP> listPlayer = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
        for (int i = 0; i < listPlayer.size(); i++) {
            EntityPlayerMP player = listPlayer.get(i);
            for (int a = 0; a < activateAbilitiesServer.get(player.getUniqueID()).size(); a++) {
                Ability ability = activateAbilitiesServer.get(player.getUniqueID()).get(a);
                if (!ability.onUpdate(player))
                    ability.onExit(player);
            }
            if(activateAbilitiesServer.get(player.getUniqueID()).isEmpty()) listPlayer.remove(player);
        }
    }

}
