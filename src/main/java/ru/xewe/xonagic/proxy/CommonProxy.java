package ru.xewe.xonagic.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.xewe.xonagic.common.ability.AbilityManagerEvents;
import ru.xewe.xonagic.common.registry.BlocksRegistry;
import ru.xewe.xonagic.common.registry.NetworkHandler;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        BlocksRegistry.register();

        MinecraftForge.EVENT_BUS.register(new AbilityManagerEvents());
    }

    public void init(FMLInitializationEvent event) {
        NetworkHandler.init();
        //EntityRegistry.registerModEntity(new ResourceLocation(XeweXonagic.MODID, "bullet"), CustomBullet.class, "Bullet", 0, XeweXonagic.INSTANCE, 64, 10, true);
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public EntityPlayer getEntityPlayer (MessageContext ctx) {
        return ctx.getServerHandler().player;
    }

}
