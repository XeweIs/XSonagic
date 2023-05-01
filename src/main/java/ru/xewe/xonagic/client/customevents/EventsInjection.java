package ru.xewe.xonagic.client.customevents;

import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import ru.xewe.xonagic.XeweXonagic;
import ru.xewe.xonagic.common.enums.TypeCast;
import ru.xewe.xonagic.client.gui.TextGui;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = XeweXonagic.MODID)
public class EventsInjection {
    static TypeCast typeCast;
    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand().equals(EnumHand.OFF_HAND) || TextGui.comboText.isEmpty() || event.getSide().isServer()) return;
        EventFactory.onAbilityCast(TypeCast.RightBlock);
        typeCast = TypeCast.RightBlock;
    }

    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.RightClickEmpty event) {
        if (event.getHand().equals(EnumHand.OFF_HAND) || TextGui.comboText.isEmpty()) return;
        EventFactory.onAbilityCast(TypeCast.RightEmpty);
        typeCast = TypeCast.RightEmpty;
    }

    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.RightClickItem event) {
        if (event.getHand().equals(EnumHand.OFF_HAND) || TextGui.comboText.isEmpty()) return;
        EventFactory.onAbilityCast(TypeCast.RightItem);
        typeCast = TypeCast.RightItem;
    }

    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.getHand().equals(EnumHand.OFF_HAND) || TextGui.comboText.isEmpty()) return;
        EventFactory.onAbilityCast(TypeCast.RightEntity);
        typeCast = TypeCast.RightEntity;
    }

    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.LeftClickBlock event) {
        if(TextGui.comboText.isEmpty()) return;
        EventFactory.onAbilityCast(TypeCast.LeftBlock);
        typeCast = TypeCast.LeftBlock;
    }

    @SubscribeEvent
    public static void onAbilityCast(PlayerInteractEvent.LeftClickEmpty event) {
        if(TextGui.comboText.isEmpty()) return;
        EventFactory.onAbilityCast(TypeCast.LeftEmpty);
        typeCast = TypeCast.LeftEmpty;
    }

    @SubscribeEvent
    public static void Jump(LivingEvent.LivingJumpEvent event) {
        if(TextGui.comboText.isEmpty()) return;
        EventFactory.onAbilityCast(TypeCast.Jump);
    }

    @SubscribeEvent
    public static void Attack(AttackEntityEvent event){
        if (TextGui.comboText.isEmpty()) return;
        EventFactory.onAbilityCast(TypeCast.Left);
    }
}
