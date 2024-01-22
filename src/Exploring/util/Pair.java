package Exploring.util;

public class Pair<K, V> {
    private final K key;
    private final V value;

    public Pair(K k, V v) {
        key = k;
        value = v;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
