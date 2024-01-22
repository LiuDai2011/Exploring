package Exploring.util;

public class Tuple<K, V, V2> {
    private final K key;
    private final V value;
    private final V2 value2;

    public Tuple(K k, V v, V2 v2) {
        key = k;
        value = v;
        value2 = v2;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V2 getValue2() {
        return value2;
    }
}
