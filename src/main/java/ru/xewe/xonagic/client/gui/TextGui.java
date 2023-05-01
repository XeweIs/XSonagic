package ru.xewe.xonagic.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.text.TextFormatting;
import ru.xewe.xonagic.client.keyboard.KeyBinds;

public class TextGui extends Gui {
    public static String coolDownText = "";
    public static String comboText = "";

    public TextGui(Minecraft mc) {
        ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();
        String one = KeyBinds.OneButton.getDisplayName();
        String two = KeyBinds.TwoButton.getDisplayName();
        String three = KeyBinds.ThreeButton.getDisplayName();
        String four = KeyBinds.FourButton.getDisplayName();
        String combo = comboText
                .replace(one, String.format("%s%s"+one, TextFormatting.RED, TextFormatting.BOLD))
                .replace(two, String.format("%s%s"+two, TextFormatting.GREEN, TextFormatting.BOLD))
                .replace(three, String.format("%s%s"+three, TextFormatting.BLUE, TextFormatting.BOLD))
                .replace(four, String.format("%s%s"+four, TextFormatting.LIGHT_PURPLE, TextFormatting.BOLD));

        //Выводим текст кулдуна
        if(!coolDownText.isEmpty()) drawCenteredString(mc.fontRenderer, coolDownText,
                width / 2, height - 80, 0);

        //Выводим комбо
        if(!comboText.isEmpty()) drawCenteredString(mc.fontRenderer, combo,
                width / 2, height - 100, 0);

    }
}
