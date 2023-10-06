package Exploring.util;

import java.util.HashMap;

public class HashMapMaker<K, V> {
    public HashMap<K, V> make(Object... objects) throws RuntimeException {
        if (objects.length % 2 == 1)
            throw new RuntimeException("There can only be an even number of parameters!");
        var map = new HashMap<K, V>();
        int c = 0;
        K k = null;
        for (var e : objects) {
            if (c % 2 == 0) {
                k = (K) e;
            } else {
                map.put(k, (V) e);
            }
            c++;
            c %= 2;
        }
        return map;
    }
}
