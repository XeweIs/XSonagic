package ru.xewe.xonagic.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.xewe.xonagic.client.RenderPlayerEvents;
import ru.xewe.xonagic.client.gui.RenderGui;
import ru.xewe.xonagic.client.keyboard.KeyBinds;
import ru.xewe.xonagic.client.keyboard.KeyPressed;
import ru.xewe.xonagic.common.ability.CastAbility;
import ru.xewe.xonagic.common.registry.BlocksRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        KeyBinds.register();
        MinecraftForge.EVENT_BUS.register(new KeyPressed());
        MinecraftForge.EVENT_BUS.register(new RenderPlayerEvents());
        MinecraftForge.EVENT_BUS.register(new CastAbility());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        BlocksRegistry.registerRender();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        MinecraftForge.EVENT_BUS.register(new RenderGui());
    }

    public static EntityPlayer getEntityPlayer() {
        return Minecraft.getMinecraft().player;
    }
}
