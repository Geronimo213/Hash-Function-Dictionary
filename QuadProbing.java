import java.util.ArrayList;


//custom object to return both word object and number of accesses
	class returnFoundQP<V, I>
	{
		V value;
		I access;
		
		public returnFoundQP(V value, I access)
		{
			this.value = value;
			this.access = access;
		}
	}
	//Node to actually put into the array list
	class HashNodeQP<K, V>
	{
	    K key;
	    V value;
	 
	    // Constructor
    public HashNodeQP(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}

	//entire hash table
public class QuadProbing<K, V> 
{
	// bucketArray stores array of nodes
	private ArrayList<HashNodeQP<K, V>> bucketArray;

	// Current capacity
	private int numBuckets;

	// Current size
	private int size;

	// Constructor
	public QuadProbing() 
	{
		bucketArray = new ArrayList<>();
		numBuckets = 310000;
		size = 0;

		// Create empty nodes
		for (int i = 0; i < numBuckets; i++)
			bucketArray.add(null);
	}
	
	//get the size
	public int size() 
	{
		return size;
	}
	
	//get the capacity
	public int capacity()
	{
		return numBuckets;
	}
	//check if empty
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
	
	// Method to remove a given key
	public V remove(K key) {
		// Apply hash function to find index for given key
		int bucketIndex = getHashCode(key), h = 0;

		// Get head of chain
		// Search for key in its chain
		while (bucketArray.get(bucketIndex) != null) {
			// If Key found
			if (bucketArray.get(bucketIndex).key.equals(key))
				break;

			// Else keep moving
			bucketIndex = (bucketIndex + h * h++) % numBuckets;
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
	//find function
	public returnFoundQP<Object, Integer> get(K key) 
	{
		// Find index
		int bucketIndex = getHashCode(key), h = 0;
		// Check if empty or if key already exists in array
		int accessedSeperateChain = 0;
		while (bucketArray.get(bucketIndex) != null) {
			accessedSeperateChain++;
			if (bucketArray.get(bucketIndex).key.equals(key))
				return new returnFoundQP<>(bucketArray.get(bucketIndex).value, accessedSeperateChain);
			bucketIndex = (bucketIndex + h * h++) % numBuckets;
		}

		// If key not found
		return new returnFoundQP<>(null, null);
	}
	
	// Adds a key value pair to hash
	public void add(K key, V value) {
		// Find head of chain for given key
		int bucketIndex = getHashCode(key), h = 0;

		// Check if key is already present
		while ((bucketArray.get(bucketIndex) != null)) {
			if ((bucketArray.get(bucketIndex).key.equals(key))) {
				bucketArray.get(bucketIndex).value = value;
				return;
			}
			bucketIndex = (bucketIndex + h * h++) % numBuckets;
		}

		// Insert key in chain
		size++;
		HashNodeQP<K, V> newNode = new HashNodeQP<K, V>(key, value);
		bucketArray.set(bucketIndex, newNode);
	}

}


