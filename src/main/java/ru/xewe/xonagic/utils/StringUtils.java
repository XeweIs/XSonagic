package ru.xewe.xonagic.utils;

public class StringUtils {
    public String Elvis(String string, String els){
        return string != null ? string : els;
    }
}
