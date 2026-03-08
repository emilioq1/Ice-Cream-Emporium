package emporium;

import java.util.HashMap;
import java.util.HashSet;


public class MultiMap<K, V> {
    public void put(K key, V value) {
        HashSet<V> newSet = null;
        if(map.get(key) == null) {
            newSet = new HashSet<V>();
            map.put(key, newSet);
        }
        // map.put(key,V.add(value));
        map.get(key).add(value);
        // map.put(key,V.add(value));
    }

    public Object[] get(K key) {
        if(map.get(key) == null) {
            return new Object[0];
        }

        return map.get(key).toArray();
    }

    private HashMap<K, HashSet<V>> map;
}
