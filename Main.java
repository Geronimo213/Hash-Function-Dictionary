// Java program to demonstrate implementation of our
// own hash table with chaining for collision detection
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class definition
{
	String word;
	String type;
	String def;
	
	public definition(String x, String y, String z)
	{
		this.word = x;
		this.type = y;
		this.def = z;
	}
}

//Just a class for the main file
 class Main 
 {

//Main function
public static void main(String[] args) {
	
	//Go ahead and initialize four Hash Tables
	SeperateChaining<String, definition> SeperateChain = new SeperateChaining<>();
	SingleProbing<String, definition> SingleHT = new SingleProbing<>();
	QuadProbing<String, definition> QuadHT = new QuadProbing<>();
	DoubleHashing<String, definition> DoubleHT = new DoubleHashing<>();
	
	//open dictionary text file
	File dictionary = new File("dictionary.txt");
	//initiate scanner with null
	Scanner s = null;
	//try to initiate scanner with dictionary file
	try {
		s = new Scanner(dictionary);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	//use '|' and newLine as delimiters (That's how the text file is)
	 s.useDelimiter("\\||\\n");
	 
	 //Initialize variables for total words(lines) scanned and set Success booleans to false.
	 int totalWords = 0;
	 boolean SeperateChainSuccess = false;
	 boolean SingleHTSuccess = false;
	 boolean QuadHTSuccess = false;
	 boolean DoubleHTSuccess = false;
	 
	 //Read until End of File
	 while(s.hasNext()){
		 
		 	  //Temp variables that get reset every loop.
	          String wordTemp = s.next();
	          String typeTemp = s.next();
	          String defTemp = s.next();
	          
	          //Add words to four different hashTables using four different add functions.
	          SeperateChain.add(wordTemp,new definition(wordTemp, typeTemp, defTemp));
	          SingleHT.add(wordTemp,new definition(wordTemp, typeTemp, defTemp));
	          QuadHT.add(wordTemp, new definition(wordTemp,typeTemp,defTemp));
	          DoubleHT.add(wordTemp, new definition(wordTemp,typeTemp,defTemp));
	          
	          //Increment total words(lines)
	          totalWords ++;

	 }
	//scanner for reading user input
	 Scanner reader = new Scanner(System.in);
	 
	 while (true)
	 {
	//Successful until proven null
	SeperateChainSuccess = true;
	SingleHTSuccess = true;
	QuadHTSuccess = true;
	DoubleHTSuccess = true;
	
	//Prompt for user input. Replace user input with lower case version (That's how text file is) and replace spaces with underscores (again, that's how the text file is).
	System.out.println("What word would you like to search for? (-1 to exit)");
	String usrInput = reader.nextLine().replaceAll(" ", "_").toLowerCase();
	
	//User exits
	if (usrInput.compareTo("-1") == 0)
	{
		System.out.println("Goodbye.");
		//close scanners because memory leaks are bad.
		s.close();
		reader.close();
		System.exit(0);
	}
	
	//Find the word using user input as key
	returnFound<Object, Integer> a = SeperateChain.get(usrInput);
	returnFoundSP<Object, Integer> b = SingleHT.get(usrInput);
	returnFoundQP<Object, Integer> c = QuadHT.get(usrInput);
	returnFoundDH<Object, Integer> d = DoubleHT.get(usrInput);
	
	//convert generic objects into definition objects
	definition a2 = (definition) a.value;
	definition b2 = (definition) b.value;
	definition c2 = (definition) c.value;
	definition d2 = (definition) d.value;
	
	
	//Check if results are null (Used for Table Output)
	if (a2 == null)
	{
		SeperateChainSuccess = false;
	}
	if (b2 == null)
	{
		SingleHTSuccess = false;
	}
	if (c2 == null)
	{
		QuadHTSuccess = false;
	}
	if (d2 == null)
	{
		DoubleHTSuccess = false;
	}
	
	//Check cases where some lookups fail and some succeed.
	if (!(SeperateChainSuccess || SingleHTSuccess))
	{
		System.out.println("Key was not found. Please check spelling and try again.");
	}
	if (SeperateChainSuccess)
	{
		System.out.println(a2.word.replaceAll("_", " ") + " (" + a2.type + "): " + a2.def);
	}
	if (!SeperateChainSuccess && SingleHTSuccess)
	{
		System.out.println(b2.word.replaceAll("_", " ") + " (" + b2.type + "): " + b2.def);
	}
	if (!SeperateChainSuccess && !SingleHTSuccess && QuadHTSuccess)
	{
		System.out.println(c2.word.replaceAll("_", " ") + " (" + c2.type + "): " + c2.def);
	}
	if (!SeperateChainSuccess && !SingleHTSuccess && !QuadHTSuccess && DoubleHTSuccess)
	{
		System.out.println(d2.word.replaceAll("_", " ") + " (" + d2.type + "): " + d2.def);
	}

	//print out total words
	System.out.println("Total Words: " + totalWords);
	
	//formatting string. useful for left-aligning, spacing, and also displaying max of 2 decimal places
	String leftAlignFormat = "| %-15s | %-11s | %-7.2f | %-8s | %-19s |%n";

	//Print out header for table
	System.out.format("+-----------------+-------------+---------+----------+---------------------+%n");
	System.out.format("| Data Structure  | Table Size  | Lambda  | Success  | Items Investigated  |%n");
	System.out.format("+-----------------+-------------+---------+----------+---------------------+%n");
	
	//Print out all necessary table information according to format string
	System.out.format(leftAlignFormat, "Chaining", SeperateChain.size(), ((double) SeperateChain.size())/SeperateChain.capacity(), SeperateChainSuccess, a.access );
	System.out.format(leftAlignFormat, "Single", SingleHT.size(), ((double) SingleHT.size())/SingleHT.capacity(), SingleHTSuccess, b.access );
	System.out.format(leftAlignFormat, "Quad", QuadHT.size(), ((double) QuadHT.size())/QuadHT.capacity(), QuadHTSuccess, c.access );
	System.out.format(leftAlignFormat, "Double", DoubleHT.size(), ((double) DoubleHT.size())/DoubleHT.capacity(), DoubleHTSuccess, d.access );
	
	//print out footer for table
	System.out.format("+-----------------+-------------+---------+----------+---------------------+%n");
		
		
	 }
	


}

 }
 

