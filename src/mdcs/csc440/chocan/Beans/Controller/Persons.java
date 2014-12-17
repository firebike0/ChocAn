package mdcs.csc440.chocan.Beans.Controller;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import mdcs.csc440.chocan.Beans.Person;


// Models a collection of Person objects, sorted by number.
public class Persons
{

	public Persons() 
	{
		personList = new ArrayList<Person>();
	}
	
	protected void open()
	{
		Scanner inFile = null;
		try
		{
			FileReader reader = new FileReader(FILE_NAME);
			inFile = new Scanner(reader);
			Person.setNextNumber(inFile.nextLong());
		}
		catch (FileNotFoundException ex)    
		{
			//Assume this is the first run of the program.
			Person.setNextNumber(100000000);
		}
		finally
		{
			if (inFile != null) inFile.close();
		}     
	}

	protected void close() throws FileNotFoundException
	{
		PrintWriter outFile = new PrintWriter(FILE_NAME);
		outFile.println(Person.getNextNumber());
		outFile.close();
	} 

	// Writes all the persons in the collection to the given text file.  
	protected void saveToFile(String fileName) throws FileNotFoundException
	{
		PrintWriter outFile = new PrintWriter(fileName);
		for (Person aPerson : personList)
			outFile.println(aPerson.toString());
		outFile.close();
	}

	// Searches for a person in the collection.
	protected Person search(long personNumber)
	{
		for (Person aPerson : personList)
			if (aPerson.getNumber() == personNumber)
				return aPerson;

		return null;
	}

	public void add(Person aPerson)
	{
		personList.add(aPerson);
	}

	public void update(Person aPerson)
	{
		//Unnecessary to do anything in the ArrayList implementation
		// -- aPerson is already in the list
		// Would be necessary to have this method if not using ArrayList in implementation
	}

	// Deletes the person with the given person number from the collection.
	public void delete(long personNumber)
	{
		for (int i =0; i < personList.size(); i++)
			if (personList.get(i).getNumber() == personNumber)
			{
				personList.remove(i);
				return;
			}
	}

	public ArrayList<Person> getAll()
	{
		return personList;
	}

	private ArrayList<Person> personList;

	private static final String FILE_NAME = "Persons.txt"; 

}