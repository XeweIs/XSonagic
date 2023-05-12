package ru.xewe.xonagic.proxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import ru.xewe.xonagic.XeweXonagic;
import ru.xewe.xonagic.common.ability.AbilityManager;
import ru.xewe.xonagic.common.entity.CustomBullet;
import ru.xewe.xonagic.common.packets.NetworkHandler;
import ru.xewe.xonagic.registry.BlocksRegistry;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        BlocksRegistry.register();
        NetworkHandler.init();


        MinecraftForge.EVENT_BUS.register(new AbilityManager());
    }

    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(new ResourceLocation(XeweXonagic.MODID, "bullet"), CustomBullet.class, "Bullet", 0, XeweXonagic.INSTANCE, 64, 10, true);
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
