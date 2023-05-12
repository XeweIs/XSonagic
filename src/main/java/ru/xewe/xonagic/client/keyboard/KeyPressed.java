package ru.xewe.xonagic.client.keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import ru.xewe.xonagic.client.gui.TextGui;

public class KeyPressed {
    public static boolean ALT;
    boolean isActive = false;

    @SubscribeEvent
    public void PressOne(InputEvent.KeyInputEvent event) {
        if (!(KeyBinds.OneButton.isPressed() && isActive)) return;
        TextGui.comboText += KeyBinds.OneButton.getDisplayName();
        playSound(0.4f, 0f);
    }

    @SubscribeEvent
    public void PressTwo(InputEvent.KeyInputEvent event) {
        if (!(KeyBinds.TwoButton.isPressed() && isActive)) return;
        TextGui.comboText += KeyBinds.TwoButton.getDisplayName();
        playSound(0.8f, 1f);
    }

    @SubscribeEvent
    public void PressThree(InputEvent.KeyInputEvent event) {
        if (!(KeyBinds.ThreeButton.isPressed() && isActive)) return;
        TextGui.comboText += KeyBinds.ThreeButton.getDisplayName();
        playSound(2f, 1f);
    }

    @SubscribeEvent
    public void PressFour(InputEvent.KeyInputEvent event) {
        if (!(KeyBinds.FourButton.isPressed() && isActive)) return;
        TextGui.comboText += KeyBinds.FourButton.getDisplayName();
        playSound(4f, 2f);
    }

    @SubscribeEvent
    public void PressModify(InputEvent.KeyInputEvent event) {
        if(!isActive) return;

        ALT = Keyboard.isKeyDown(Keyboard.KEY_LMENU);

    }

    public static short time = 0;

    @SubscribeEvent
    public void onTickClearKey(TickEvent.ClientTickEvent event) {
        if (event.side.isServer()  || !isActive) return;
        if (time >= 60) {
            TextGui.comboText = "";
            time = 0;
        } else if (!TextGui.comboText.isEmpty()) {
            time++;
        }
    }


    @SubscribeEvent
    public void onChangeMode(InputEvent.KeyInputEvent event) {
        if (!KeyBinds.Mode_change_button.isPressed()) return;

        if (isActive) {
            Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(String.format("%s" + I18n.format("message.mode.deactivated"), TextFormatting.DARK_RED)), true);
            isActive = false;
            TextGui.comboText = "";
            Minecraft.getMinecraft().player.playSound(SoundEvents.UI_TOAST_IN, 500f, 0f);
        } else {
            Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(String.format("%s" + I18n.format("message.mode.activated"), TextFormatting.GREEN)), true);
            time = 0;
            isActive = true;
            Minecraft.getMinecraft().player.playSound(SoundEvents.UI_TOAST_IN, 120f, 2f);
        }
    }

    private void playSound(float volume, float pitch) {
        Minecraft.getMinecraft().player.playSound(SoundEvents.ENTITY_CREEPER_DEATH, volume, pitch);
    }

}
