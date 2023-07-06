package ru.xewe.xonagic.common.ability;

import net.minecraft.util.text.TextFormatting;
import ru.xewe.xonagic.common.enums.ElementEnum;
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
    ElementEnum element();
    TextFormatting color() default TextFormatting.WHITE;
    int coolDown();
    int repeat();
    String combo();
    TypeCast[] activations();
}
