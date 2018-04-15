package assignment8;

public interface IKVStore<K, V>
{
	void put(K key, V value);
	
	V get(K key);
}