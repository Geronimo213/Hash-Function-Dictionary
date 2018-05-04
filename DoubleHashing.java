import java.util.ArrayList;

	
//custom object to return both word object and number of accesses

	class returnFoundDH<V, I>
	{
		V value;
		I access;
		
		//constructor
		public returnFoundDH(V value, I access)
		{
			this.value = value;
			this.access = access;
		}
	}
	//Node to actually put into the array list
	class HashNodeDH<K, V>
	{
	    K key;
	    V value;
	 
	    // Constructor
    public HashNodeDH(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}

public class DoubleHashing<K, V> 
{
	// bucketArray is used to store array of chains
	private ArrayList<HashNodeDH<K, V>> bucketArray;

	// Current capacity of array list
	private int numBuckets;

	// Current size of array list
	private int size;

	// Constructor (Initializes capacity, size and
	// empty chains.
	public DoubleHashing() 
	{
		bucketArray = new ArrayList<>();
		numBuckets = 310000;
		size = 0;

		// Create empty chains
		for (int i = 0; i < numBuckets; i++)
			bucketArray.add(null);
	}
	
	//get size
	public int size() 
	{
		return size;
	}
	
	//get capacity
	public int capacity()
	{
		return numBuckets;
	}
	
	//check if empty or not
	public boolean isEmpty() 
	{
		return size() == 0;
	}
	
	//primary hashing function
	private int getHashCode(K key) 
	{
		int hashCode = key.hashCode();
		int index = Math.abs(hashCode % numBuckets);
		return index;
	}
	//get secondary hash code
	private int getHashCode2(K key)
	{
		int hashCode = secondaryHash(key);
		int index = Math.abs(hashCode % numBuckets);
		return index;
	}
	
	// Method to remove a key
	public V remove(K key) {
		// Apply hash function to find index for given key
		int bucketIndex = getHashCode(key);

		// Search for key
		while (bucketArray.get(bucketIndex) != null) {
			// If Key found
			if (bucketArray.get(bucketIndex).key.equals(key))
				break;

			// Else keep moving
			bucketIndex += getHashCode2(key);
			bucketIndex %= numBuckets;
		}

		// If key not there
		if (bucketArray.get(bucketIndex) == null)
			return null;

		// Reduce size
		size--;

		// Remove key
		bucketArray.set(bucketIndex, null);

		return bucketArray.get(bucketIndex).value;
	}
	
	public returnFoundDH<Object, Integer> get(K key) 
	{
		// Find index
		int bucketIndex = getHashCode(key);
		// Search key
		int accessedSeperateChain = 0;
		while (bucketArray.get(bucketIndex) != null) {
			accessedSeperateChain++;
			if (bucketArray.get(bucketIndex).key.equals(key))
				return new returnFoundDH<>(bucketArray.get(bucketIndex).value, accessedSeperateChain);
			bucketIndex += getHashCode2(key);
			bucketIndex %= numBuckets;
		}

		// If key not found
		return new returnFoundDH<>(null, null);
	}
	
	// Adds a key value pair
	public void add(K key, V value) {
		// Find index of chain
		int bucketIndex = getHashCode(key);

		// Check if key is present
		while ((bucketArray.get(bucketIndex) != null)) {
			if ((bucketArray.get(bucketIndex).key.equals(key))) {
				bucketArray.get(bucketIndex).value = value;
				return;
			}
			bucketIndex += getHashCode2(key);
			bucketIndex %= numBuckets;
		}

		// Insert key
		size++;
		HashNodeDH<K, V> newNode = new HashNodeDH<K, V>(key, value);
		bucketArray.set(bucketIndex, newNode);
	}
	
	//secondary hashing function.
	
	private int secondaryHash(K initialKey)
	{
		int hashVal = initialKey.hashCode();
        hashVal %= numBuckets;
        if (hashVal < 0)
            hashVal += numBuckets;
        return 254993 - hashVal % 254993;
	}

}


