package ru.xewe.xonagic.client.ability;

import net.minecraft.util.text.TextFormatting;
import ru.xewe.xonagic.common.enums.TypeCast;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AbilityInfo {
    String name();
    String displayName();
    TextFormatting color() default TextFormatting.WHITE;
    int coolDown();
    String combo();
    TypeCast[] activations();

}