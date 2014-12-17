package mdcs.csc440.chocan.Beans.Controller;

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

import mdcs.csc440.chocan.Beans.Person;
import mdcs.csc440.chocan.Beans.Provider;

/** This class represents a collection of Provider objects.  Provider objects 
 *  can be added to the collection, deleted from the collection, 
 *  updated and searched for in the collection.
 *  @author Jean Naude
 *  @version 1.0 March 2009
 */ 
public class Providers extends Persons
{

	/**
	 * Creates a new empty Providers object
	 */
	public Providers()
	{
	}//default constructor

	/** Reads all the providers in the FILE_NAME text file into the collection.
	 *  @throws NumberFormatException if the provider number is not a valid 
	 *          integer
	 *  @throws IllegalArgumentException if any of the values are invalid.
	 *  @throws ArrayIndexOutOfBoundsException if there are not enough values 
	 *             in the string
	 */
	public void open()
	{
		//Initialize the next number if this has not already been done
		if (!(Person.getNextNumber() > 0))
			super.open();       

		Scanner inFile = null;
		try
		{
			FileReader reader = new FileReader(FILE_NAME);
			inFile = new Scanner(reader);

			//each line of the text file represents one provider
			while(inFile.hasNextLine())
			{
				//read the next line
				String line = inFile.nextLine();

				//create a provider with this data
				Provider aProvider = new Provider(line);

				//add provider to collection
				add(aProvider);
			}
		}
		catch (FileNotFoundException ex)    
		{
			//No providers saved
		}
		finally
		{
			if (inFile != null)inFile.close();
		}     
	}//open

	/** Writes all the providers in the collection to the FILE_NAME text file.
	 *  @throws FileNotFoundException if the file cannot be created
	 */   
	public void close() throws FileNotFoundException
	{
		super.close();
		saveToFile(FILE_NAME);
	}//close

	/** Searches for a provider with the given provider number in the collection.
	 *  @param providerNumber the number for which to search
	 *  @return the provider if found, otherwise null
	 */
	public Provider find(long providerNumber)
	{
		return (Provider)search(providerNumber);
	}//find


	//******************class variable
	private static final String FILE_NAME = "Providers.txt";  //default store

}//class Providers
