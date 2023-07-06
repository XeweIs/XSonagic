package ru.xewe.xonagic.common.ability;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.xewe.xonagic.client.customevents.AbilityCast;
import ru.xewe.xonagic.client.gui.TextGui;
import ru.xewe.xonagic.client.keyboard.Key;
import ru.xewe.xonagic.common.data.AbilitiesData;

import java.util.Arrays;


public abstract class CastAbility {

    protected abstract Ability[] getAbilities();

    @SubscribeEvent
    public void abilityCast(AbilityCast event) {
        if (TextGui.comboText.isEmpty()) return;

        for(Ability ability : getAbilities()){
            AbilityInfo info = ability.getClass().getAnnotation(AbilityInfo.class);
            String combo = info.combo().toLowerCase()
                    .replace("z", Key.getZ()).replace("x", Key.getX())
                    .replace("c", Key.getC()).replace("v", Key.getV());

            if(TextGui.comboText.equals(combo) && Arrays.asList(info.activations()).contains(event.getTypeCast())){
                if(ability.allowedExecute()) {
//                    ability.execute(Minecraft.getMinecraft().player);
//                    NetworkHandler.NETWORK.sendToServer(new CPacketCast(ability.getClass().getName()));
//                    TextGui.comboText = "";
//                    KeyPressed.time = 0;
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString(""+ AbilitiesData.get(Minecraft.getMinecraft().player)));
                }
                break;
            }
        }

        //Перекинуть это в пакет
    }
}
