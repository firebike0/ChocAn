package mdcs.csc440.chocan.Beans.Controller;

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

import mdcs.csc440.chocan.Beans.Person;
import mdcs.csc440.chocan.Beans.Provider;

// This class represents a collection of Provider objects.
public class ProviderController extends PersonController
{

	public ProviderController()
	{
	}
	
	// Reads all the providers in the FILE_NAME text file into the collection.
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

			while(inFile.hasNextLine())
			{
				String line = inFile.nextLine();

				//create a provider with this data
				Provider aProvider = new Provider(line);

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
	}

	// Writes all the providers in the collection to the FILE_NAME text file.
	public void close() throws FileNotFoundException
	{
		super.close();
		saveToFile(FILE_NAME);
	}

	// Searches for a provider with the given provider number in the collection.
	public Provider find(long providerNumber)
	{
		return (Provider)search(providerNumber);
	}
	
	private static final String FILE_NAME = "Providers.txt";
}