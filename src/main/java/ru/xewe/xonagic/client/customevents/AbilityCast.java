package ru.xewe.xonagic.client.customevents;

import net.minecraftforge.fml.common.eventhandler.Event;
import ru.xewe.xonagic.common.enums.TypeCast;

public class AbilityCast extends Event {
    private final TypeCast typeCast;

    public AbilityCast(TypeCast typeCast) {
        this.typeCast = typeCast;
    }

    public TypeCast getTypeCast() {
        return typeCast;
    }
}
