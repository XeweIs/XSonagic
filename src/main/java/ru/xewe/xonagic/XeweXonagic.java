package ru.xewe.xonagic;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import ru.xewe.xonagic.proxy.CommonProxy;

@Mod(modid = XeweXonagic.MODID, name = XeweXonagic.NAME, version = XeweXonagic.VERSION)
public class XeweXonagic
{
    public static final String MODID = "xsonagic";
    public static final String NAME = "Xewe's Xonagic";
    public static final String VERSION = "1.0";
    @SidedProxy(
            clientSide = "ru.xewe.xonagic.proxy.ClientProxy",
            serverSide = "ru.xewe.xonagic.proxy.CommonProxy")
    public static CommonProxy proxy;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }
    @Mod.Instance
    public static XeweXonagic INSTANCE;
}
