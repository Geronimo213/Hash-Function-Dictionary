import java.util.ArrayList;

//custom object to return both word object and number of accesses
class returnFound<V, I>
{
	V value;
	I access;
	
	//constructor
	public returnFound(V value, I access)
	{
		this.value = value;
		this.access = access;
	}
}
//Node to actually put into the array list
class HashNode<K, V>
{
    K key;
    V value;
 
    // Reference to next node
    HashNode<K, V> next;
 
    // Constructor
    public HashNode(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}
// Entire hash table
public class SeperateChaining<K, V> {
	// bucketArray is an array of nodes
	private ArrayList<HashNode<K, V>> bucketArray;

	// Current capacity
	private int numBuckets;

	// Current size
	private int size;

	// Constructor
	public SeperateChaining() {
		bucketArray = new ArrayList<>();
		numBuckets = 10;
		size = 0;

		// Create empty nodes
		for (int i = 0; i < numBuckets; i++)
			bucketArray.add(null);
	}

	//get the size
	public int size() {
		return size;
	}
	
	//get the capacity
	public int capacity()
	{
		return numBuckets;
	}

	//check if it's empty
	public boolean isEmpty() {
		return size() == 0;
	}

	// Hash function
	private int getBucketIndex(K key) {
		int hashCode = key.hashCode();
		int index = Math.abs(hashCode % numBuckets);
		return index;
	}

	// Method to remove node
	public V remove(K key) {
		// Apply hash function to find index for given key
		int bucketIndex = getBucketIndex(key);

		// Get head of chain
		HashNode<K, V> head = bucketArray.get(bucketIndex);

		// Search for key in its chain
		HashNode<K, V> prev = null;
		while (head != null) {
			// If Key found
			if (head.key.equals(key))
				break;

			// Else keep moving in chain
			prev = head;
			head = head.next;
		}

		// If key was not there
		if (head == null)
			return null;

		// Reduce size
		size--;

		// Remove node
		if (prev != null)
			prev.next = head.next;
		else
			bucketArray.set(bucketIndex, head.next);

		return head.value;
	}

	// Returns value for a key
	public returnFound<Object, Integer> get(K key) {
		// Find head of chain for given key
		int bucketIndex = getBucketIndex(key);
		HashNode<K, V> head = bucketArray.get(bucketIndex);

		// Search key in chain
		int accessedSeperateChain = 0;
		while (head != null) {
			accessedSeperateChain++;
			if (head.key.equals(key))
				return new returnFound<>(head.value, accessedSeperateChain);
			head = head.next;
		}

		// If key not found
		return new returnFound<>(null, null);
	}

	// Adds a key value pair to hash
	public void add(K key, V value) {
		// Find head of chain for given key
		int bucketIndex = getBucketIndex(key);
		HashNode<K, V> head = bucketArray.get(bucketIndex);

		// Check if key is already present
		while (head != null) {
			if (head.key.equals(key)) {
				head.value = value;
				return;
			}
			head = head.next;
		}

		// Insert key in chain
		size++;
		head = bucketArray.get(bucketIndex);
		HashNode<K, V> newNode = new HashNode<K, V>(key, value);
		newNode.next = head;
		bucketArray.set(bucketIndex, newNode);

		// If load factor goes beyond threshold, then
		// double hash table size
		if ((1.0 * size) / numBuckets >= 0.7) {
			ArrayList<HashNode<K, V>> temp = bucketArray;
			bucketArray = new ArrayList<>();
			numBuckets = 2 * numBuckets;
			size = 0;
			for (int i = 0; i < numBuckets; i++)
				bucketArray.add(null);

			for (HashNode<K, V> headNode : temp) {
				while (headNode != null) {
					add(headNode.key, headNode.value);
					headNode = headNode.next;
				}
			}
		}
	}

	

}