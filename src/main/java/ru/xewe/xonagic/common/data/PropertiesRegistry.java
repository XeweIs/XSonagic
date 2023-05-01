package ru.xewe.xonagic.common.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.xewe.xonagic.XeweXonagic;
import ru.xewe.xonagic.common.enums.ElementEnum;

@Mod.EventBusSubscriber(modid = XeweXonagic.MODID)
public class PropertiesRegistry {

    public static final DataParameter<String> ELEMENT = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.STRING);

    @SubscribeEvent
    public static void onPlayerConstructing(EntityEvent.EntityConstructing event) {

        if (event.getEntity() instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) event.getEntity();

            player.getDataManager().register(ELEMENT, ElementEnum.None.toString());
        }
    }
}
