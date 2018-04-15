package assignment8;

import java.util.LinkedList;

public class MyHashMap<K, V> implements IKVStore<K, V>
{
	private static final int DEFAULT_NUM_BUCKETS = 13;

	//	an array of list of key-value pairs
	private LinkedList<KVP<K, V>>[] buckets;
	private int N;
	private int factor;

	public MyHashMap(int M, int factor)
	{
		this.factor = factor;
		this.buckets = new LinkedList[ M ];
		for (int i = 0; i < buckets.length; i++)
		{
			buckets[i] = new LinkedList<>();
		}
		N = 0;
	}

	public MyHashMap()
	{
		this(DEFAULT_NUM_BUCKETS, 2);
	}

	private int chooseBucket(K key)
	{
		int h = key.hashCode();
		return Math.abs(h) % buckets.length;
	}

	public V get(K key)
	{
		//	choose the bucket for the key
		int b = chooseBucket(key);
		LinkedList<KVP<K, V>> bucket = buckets[b];
		//	scan the b-th bucket for the key
		for (KVP<K, V> pair : bucket)
		{
			if (pair.key.equals(key))
			{
				return pair.value;
			}
		}

		//	the key is not in the bucket
		return null;
	}

	public void put(K key, V value)
	{
		if (N > this.factor * buckets.length)
		{
			//	THIS CODE DOES THE WRONG THING
			System.out.println("RESIZING");
			//	need more buckets
			LinkedList[] newBuckets = new LinkedList[ buckets.length * this.factor ];
			for (int i = 0; i < newBuckets.length; i++)
			{
				newBuckets[i] = new LinkedList<>();
			}
			for (int i = 0; i < buckets.length; i++)
			{
				newBuckets[i] = buckets[i];
			}
			this.buckets = newBuckets;
		}

		//	choose the bucket for the key
		int b = chooseBucket(key);
		LinkedList<KVP<K, V>> bucket = buckets[b];
		//	scan the b-th bucket for the key
		for (KVP<K, V> pair : bucket)
		{
			if (pair.key.equals(key))
			{
				//	change the value
				pair.value = value;
				return;
			}
		}

		//	the key is not in the bucket
		N++;
		bucket.add(new KVP<>(key, value));
	}

	public void printListLengths()
	{
		for (int i = 0; i < buckets.length; i++)
		{
			System.out.println(i + " = " + buckets[i].size());
		}
	}
}