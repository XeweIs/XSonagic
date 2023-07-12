package ru.xewe.xonagic.common;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class TestEve {
    @SubscribeEvent
    public static void Right(PlayerInteractEvent.RightClickBlock event){
    }
}
