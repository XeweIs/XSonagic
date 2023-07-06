package ru.xewe.xonagic.common.enums;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public enum ElementEnum {
    Air("Air"), Earth("Earth"), Water("Water"), Fire("Fire");
    final String element;
    ElementEnum(String element){
        this.element = element;
    }

    @Override
    public String toString() {
        return element;
    }

    // Для получения обработанного ITextComponent под каждый элемент
    public ITextComponent getProcessed() {
        ITextComponent elementComponent = getLocalized();
        elementComponent.getStyle().setItalic(true).setBold(true);

        switch (this) {
            case Air:
                elementComponent.getStyle().setColor(TextFormatting.WHITE);
                break;
            case Earth:
                elementComponent.getStyle().setColor(TextFormatting.DARK_GREEN);
                break;
            case Water:
                elementComponent.getStyle().setColor(TextFormatting.BLUE);
                break;
            case Fire:
                elementComponent.getStyle().setColor(TextFormatting.DARK_RED);
                break;
        }

        return elementComponent;
    }

    public ITextComponent getLocalized(){
        return new TextComponentTranslation("element." + this.toString().toLowerCase());
    }


}
