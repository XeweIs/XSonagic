package ru.xewe.xonagic.common;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderPlayerEvents {
    @SubscribeEvent
    public void onUpdates(RenderPlayerEvent.Pre event) {
        if (event.getEntityPlayer().isInvisible()) {
            ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
            event.setCanceled(true);
        }
    }
}
