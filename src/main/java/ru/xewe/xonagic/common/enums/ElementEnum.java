package ru.xewe.xonagic.common.enums;

public enum ElementEnum {
    None("None"), Air("Air"), Earth("Earth"), Water("Water"), Fire("Fire");
    final String element;
    ElementEnum(String element){
        this.element = element;
    }

    @Override
    public String toString() {
        return element;
    }
}
