import java.util.ArrayList;
public class HashTable<K, V> {
    public ArrayList<Pair<Integer, V>> table = new ArrayList<>();

    public void addPair(K key, V value){
        table.add(new Pair<>(key.hashCode(), value));
    }

    public void removePair(K key){
        table.remove(key.hashCode());
    }

    public V getValue(K key){
        Integer hashCode = key.toString().hashCode();
        for (Pair<Integer, V> pair: table) {
            if (pair.fst().equals(hashCode)) return pair.snd();
        }
        return null;
    }
}
