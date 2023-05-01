package ru.xewe.xonagic.common.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import ru.xewe.xonagic.XeweXonagic;
import ru.xewe.xonagic.client.ability.air.CastAirAbility;
import ru.xewe.xonagic.client.ability.earth.CastEarthAbility;
import ru.xewe.xonagic.client.ability.fire.CastFireAbility;
import ru.xewe.xonagic.client.ability.water.CastWaterAbility;
import ru.xewe.xonagic.common.enums.ElementEnum;

@Mod.EventBusSubscriber(modid = XeweXonagic.MODID)
public class Element {

    public static ElementEnum getElement(EntityPlayer player) {
        return ElementEnum.valueOf(player.getDataManager().get(PropertiesRegistry.ELEMENT));
    }

    public static void setElement(EntityPlayer player, ElementEnum value) {

        player.getDataManager().set(PropertiesRegistry.ELEMENT, value.toString());
        player.getEntityData().setString(XeweXonagic.MODID + ":element", getElement(player).toString());

        if(player.world.isRemote) {
            MinecraftForge.EVENT_BUS.unregister(CastAirAbility.instance);
            MinecraftForge.EVENT_BUS.unregister(CastFireAbility.instance);
            MinecraftForge.EVENT_BUS.unregister(CastWaterAbility.instance);
            MinecraftForge.EVENT_BUS.unregister(CastEarthAbility.instance);
            switch (value) {
                case Air:
                    MinecraftForge.EVENT_BUS.register(CastAirAbility.instance);
                    break;
                case Fire:
                    MinecraftForge.EVENT_BUS.register(CastFireAbility.instance);
                    break;
                case Earth:
                    MinecraftForge.EVENT_BUS.register(CastEarthAbility.instance);
                    break;
                case Water:
                    MinecraftForge.EVENT_BUS.register(CastWaterAbility.instance);
                    break;
            }
        }
    }

    public static void refillElement(EntityPlayer player) {

        player.getDataManager().set(PropertiesRegistry.ELEMENT, ElementEnum.None.toString());
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

        Element.setElement(player, Element.getElement(oldPlayer));
    }
}
