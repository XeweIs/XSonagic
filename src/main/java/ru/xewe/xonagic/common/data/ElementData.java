package ru.xewe.xonagic.common.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import ru.xewe.xonagic.XeweXonagic;
import ru.xewe.xonagic.common.enums.ElementEnum;

@Mod.EventBusSubscriber(modid = XeweXonagic.MODID)
public class ElementData {
    public static ElementEnum getElement(EntityPlayer player) {
        return ElementEnum.valueOf(player.getDataManager().get(PropertiesRegistry.ELEMENT));
    }

    public static void setElement(EntityPlayer player, ElementEnum value) {

        player.getDataManager().set(PropertiesRegistry.ELEMENT, value.toString());
        player.getEntityData().setString(XeweXonagic.MODID + ":element", getElement(player).toString());
    }

    private static void saveElementToNBT(EntityPlayer player) {
        player.getEntityData().setString(XeweXonagic.MODID + ":element", getElement(player).toString());
    }

    private static String loadElementFromNBT(EntityPlayer player) {
        return player.getEntityData().hasKey(XeweXonagic.MODID + ":element") ? player.getEntityData().getString(XeweXonagic.MODID + ":element") : ElementEnum.None.toString();
    }

    @SubscribeEvent
    public static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        setElement(event.player, ElementEnum.valueOf(loadElementFromNBT(event.player)));
    }

    @SubscribeEvent
    public static void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        saveElementToNBT(event.player);
    }

    @SubscribeEvent
    public static void playerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        EntityPlayer oldPlayer = event.getOriginal();

        ElementData.setElement(player, ElementData.getElement(oldPlayer));
    }
}
