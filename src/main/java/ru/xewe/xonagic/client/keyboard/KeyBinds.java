package ru.xewe.xonagic.client.keyboard;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import org.lwjgl.input.Keyboard;

import ru.xewe.xonagic.XeweXonagic;

public class KeyBinds {
    private static final String CATEGORY = XeweXonagic.NAME;
    public static final KeyBinding Mode_change_button = new KeyBinding(I18n.format("key.mode"), Keyboard.KEY_R, CATEGORY);
    public static final KeyBinding OneButton = new KeyBinding(I18n.format("key.one"), Keyboard.KEY_Z, CATEGORY);
    public static final KeyBinding TwoButton = new KeyBinding(I18n.format("key.two"), Keyboard.KEY_X, CATEGORY);
    public static final KeyBinding ThreeButton = new KeyBinding(I18n.format("key.three"), Keyboard.KEY_C, CATEGORY);
    public static final KeyBinding FourButton = new KeyBinding(I18n.format("key.four"), Keyboard.KEY_V, CATEGORY);

    public static void register() {
        setRegister(Mode_change_button);
        setRegister(OneButton);
        setRegister(TwoButton);
        setRegister(ThreeButton);
        setRegister(FourButton);
    }

    private static void setRegister(KeyBinding binding) {
        ClientRegistry.registerKeyBinding(binding);
    }
}
