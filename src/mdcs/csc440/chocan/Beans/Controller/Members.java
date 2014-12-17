package mdcs.csc440.chocan.Beans.Controller;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileReader;

import mdcs.csc440.chocan.Beans.Member;
import mdcs.csc440.chocan.Beans.Person;

/** This class models a collection of Member objects.  
 *  Member objects can be added to the collection, deleted from the collection,
 *  updated and searched for in the collection.
 *  @author Jean Naude
 *  @version 1.0 March 2009  
 */ 
public class Members extends Persons
{

	/** Creates a new empty Members object.
	 */ 
	public Members()
	{
	}//constructor


	/** Reads all the members in text file FILE_NAME into the collection.
	 *  @throws NumberFormatException if the member number is not a valid 
	 *          integer
	 *  @throws IllegalArgumentException if any of the values are invalid.
	 *  @throws ArrayIndexOutOfBoundsException if there are not enough values 
	 *          in the string
	 */
	public void open()
	{
		//open persons collection if not already open
		if (!(Person.getNextNumber() > 0))
			super.open();

		Scanner inFile = null;
		try
		{
			FileReader reader = new FileReader(FILE_NAME);
			inFile = new Scanner(reader);

			//Each line of the text file represents one member
			while(inFile.hasNextLine())
			{
				//read line
				String line = inFile.nextLine();

				//create a member from this data
				Member aMember = new Member(line);

				//add to member collection
				add(aMember);
			}
		}
		catch (FileNotFoundException ex)    
		{
			//No members saved
		}
		finally
		{
			if (inFile != null)inFile.close();
		}     
	}//open

	/** Writes all the members in the collection to the text file FILE_NAME.
	 *  @throws FileNotFoundException if the file cannot be created
	 */   
	public void close() throws FileNotFoundException
	{
		super.close();
		saveToFile(FILE_NAME);
	}//close

	/** Searches for a member with the given member number in the collection.
	 *  @param memberNumber the number for which to search
	 *  @return the member if found, otherwise null
	 */
	public Member find(long memberNumber)
	{
		return (Member)search(memberNumber);
	}//find

	//********************class variable
	private static final String FILE_NAME = "Members.txt";  //default collection


}//class Members
