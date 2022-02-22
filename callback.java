package implementation;

/**
 * Interface implemented by classes that will perform processing over a tree
 * node. 
 *
 * @param <K>
 * @param <V>
 */
public interface Callback<K, V> {
	public void process(K key, V value);
}
