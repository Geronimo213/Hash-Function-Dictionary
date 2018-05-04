import java.util.ArrayList;


//custom object to return both word object and number of accesses

	class returnFoundSP<V, I>
	{
		V value;
		I access;
		
		public returnFoundSP(V value, I access)
		{
			this.value = value;
			this.access = access;
		}
	}
	//Node to actually put into the array list

	class HashNodeSP<K, V>
	{
	    K key;
	    V value;
	 
	    // Constructor
    public HashNodeSP(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}
	// Entire hash table

public class SingleProbing<K, V> 
{
	// bucketArray is used to store array of data
	private ArrayList<HashNodeSP<K, V>> bucketArray;

	// Current capacity
	private int numBuckets;

	// Current size
	private int size;

	// Constructor
	public SingleProbing() 
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
	//check if it's empty
	public boolean isEmpty() 
	{
		return size() == 0;
	}
	
	//hash function
	private int getHashCode(K key) 
	{
		int hashCode = key.hashCode();
		int index = Math.abs(hashCode % numBuckets);
		return index;
	}
	
	// Method to remove node using given key
	public V remove(K key) {
		// Apply hash function to find index for given key
		int bucketIndex = getHashCode(key);

		// Get head of chain
		// Search for key in its chain
		while (bucketArray.get(bucketIndex) != null) {
			// If Key found
			if (bucketArray.get(bucketIndex).key.equals(key))
				break;

			// Else keep moving
			bucketIndex++;
		}

		// If key was not there
		if (bucketArray.get(bucketIndex) == null)
			return null;

		// Reduce size
		size--;

		// Remove key
		bucketArray.set(bucketIndex, null);

		return bucketArray.get(bucketIndex).value;
	}
	
	public returnFoundSP<Object, Integer> get(K key) 
	{
		// Find head of chain for given key
		int bucketIndex = getHashCode(key);
		// Search key in chain
		int accessedSeperateChain = 0;
		while (bucketArray.get(bucketIndex) != null) {
			accessedSeperateChain++;
			if (bucketArray.get(bucketIndex).key.equals(key))
				return new returnFoundSP<>(bucketArray.get(bucketIndex).value, accessedSeperateChain);
			bucketIndex++;
		}

		// If key not found
		return new returnFoundSP<>(null, null);
	}
	
	// Adds a key value pair
	public void add(K key, V value) {
		// Find head of chain for given key
		int bucketIndex = getHashCode(key);

		// Check if key is already there
		while ((bucketArray.get(bucketIndex) != null)) {
			if ((bucketArray.get(bucketIndex).key.equals(key))) {
				bucketArray.get(bucketIndex).value = value;
				return;
			}
			bucketIndex++;
		}

		// Insert key
		size++;
		HashNodeSP<K, V> newNode = new HashNodeSP<K, V>(key, value);
		bucketArray.set(bucketIndex, newNode);
	}

}


