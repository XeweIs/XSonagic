package ru.xewe.xonagic.client.customevents;

import net.minecraftforge.common.MinecraftForge;
import ru.xewe.xonagic.common.enums.TypeCast;

public class EventFactory {
    public static void onAbilityCast(TypeCast typeCast) {
        MinecraftForge.EVENT_BUS.post(new AbilityCast(typeCast));
    }
}
