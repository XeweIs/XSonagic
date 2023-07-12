package ru.xewe.xonagic.common.ability;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.xewe.xonagic.client.customevents.AbilityCast;
import ru.xewe.xonagic.client.gui.TextGui;
import ru.xewe.xonagic.client.keyboard.Key;
import ru.xewe.xonagic.common.packets.CPacketCastAbility;
import ru.xewe.xonagic.common.registry.NetworkHandler;

import java.util.Arrays;
import java.util.List;


public class CastAbility {

    @SideOnly(Side.CLIENT)
    public List<Ability> getAbilities(){
        return AbilityManager.abilityManagerSP.abilities;
    }

    @SubscribeEvent
    public void abilityCastPre(AbilityCast event) {
        if (TextGui.comboText.isEmpty()) return;

        for(Ability ability : getAbilities()){
            String combo = ability.getInfo().combo().toLowerCase()
                    .replace("z", Key.getZ()).replace("x", Key.getX())
                    .replace("c", Key.getC()).replace("v", Key.getV());

            if(TextGui.comboText.equals(combo) && Arrays.asList(ability.getInfo().activations()).contains(event.getTypeCast())){
                //Вы подумаете, что это условие бессмысленное, ибо такое же есть в пакете, но нет.
                //Это условие здесь как второй этап избежания спама пакетом.
                if(ability.allowedExecute()) {
                    NetworkHandler.NETWORK.sendToServer(new CPacketCastAbility(ability.getInfo().name()));
                }
                break;
            }
        }
    }
}
