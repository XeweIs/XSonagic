package ru.xewe.xonagic.utils;

import java.util.HashMap;
import java.util.List;

public class HashUtils {

    public static void addValueHash(HashMap<Integer, List<String>> hashMap, Object key, Object value) {

        ((List) hashMap.get(key)).add(value);
//            hashMap.put(key, );

    }


}
