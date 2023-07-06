package ru.xewe.xonagic.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.text.TextFormatting;
import ru.xewe.xonagic.client.keyboard.KeyBinds;
import ru.xewe.xonagic.common.ability.AbilityManager;

import java.util.List;
import java.util.stream.Collectors;

public class TextGui extends Gui {
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

        List<String> list = AbilityManager.getAbilityManagerSP().coolDownAbilities.stream().map(ability -> new StringBuilder()
                .append(ability.color)
                .append(TextFormatting.STRIKETHROUGH)
                .append(ability.displayName)
                .append(" ")
                .append(ability.coolDown / 20 + 1)
                .append(TextFormatting.RESET).toString()).collect(Collectors.toList());

        drawCenteredString(mc.fontRenderer, String.join(TextFormatting.BOLD+"| |"+TextFormatting.RESET, list),
                width / 2, height - 80, 0);

        if(!comboText.isEmpty()) drawCenteredString(mc.fontRenderer, combo,
                width / 2, height - 100, 0);

    }
}
