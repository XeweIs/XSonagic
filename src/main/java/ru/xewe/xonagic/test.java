package ru.xewe.xonagic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class test {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Lol 1");

        list = list.stream().map(c -> c.contains("Lol") ? "NeLol 2" : c).collect(Collectors.toList());

        System.out.println(list);
    }
}
