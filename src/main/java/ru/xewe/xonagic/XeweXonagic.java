package ru.xewe.xonagic;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import ru.xewe.xonagic.proxy.CommonProxy;
import ru.xewe.xonagic.server.commands.CommandEditAbilities;
import ru.xewe.xonagic.server.commands.CommandElementChoose;
import ru.xewe.xonagic.server.commands.CommandGetInfo;

@Mod(modid = XeweXonagic.MODID, name = XeweXonagic.NAME, version = XeweXonagic.VERSION)
public class XeweXonagic
{
    public static final String MODID = "xsonagic";
    public static final String NAME = "Xewe's Xonagic";
    public static final String VERSION = "1.0";
    public static Logger logger;
    @SidedProxy(
            clientSide = "ru.xewe.xonagic.proxy.ClientProxy",
            serverSide = "ru.xewe.xonagic.proxy.CommonProxy")
    public static CommonProxy proxy;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
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
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandElementChoose());
        event.registerServerCommand(new CommandGetInfo());
        event.registerServerCommand(new CommandEditAbilities());
    }
    @Mod.Instance
    public static XeweXonagic INSTANCE;
}
