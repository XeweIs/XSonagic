package ru.xewe.xonagic.client;

import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderPlayerEvents {
    @SubscribeEvent
    public void onUpdates(RenderPlayerEvent.Pre event) {
        if (event.getEntityPlayer().isInvisible()) {
            event.setCanceled(true);
        }
    }
}
