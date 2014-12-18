package mdcs.csc440.chocan.Beans.Controller;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileReader;

import mdcs.csc440.chocan.Beans.Member;
import mdcs.csc440.chocan.Beans.Person;

// Models a collection of Member objects.  
public class MemberController extends PersonController
{

	public MemberController()
	{
	}

	// Reads all the members in text file FILE_NAME into the collection.
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
				String line = inFile.nextLine();

				//create a member from this data
				Member aMember = new Member(line);

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
	}

	// Writes all the members in the collection to the text file FILE_NAME.
	public void close() throws FileNotFoundException
	{
		super.close();
		saveToFile(FILE_NAME);
	}

	// Searches for a member in the collection.
	public Member find(long memberNumber)
	{
		return (Member)search(memberNumber);
	}

	private static final String FILE_NAME = "Members.txt";  //default collection
}