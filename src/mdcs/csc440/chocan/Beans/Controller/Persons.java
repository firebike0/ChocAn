package mdcs.csc440.chocan.Beans.Controller;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import mdcs.csc440.chocan.Beans.Person;


/** This class models a collection of Person objects, sorted by number.
 *  This implementation uses text files to save data between runs of the program.
 *  During program runs, the data is stored in Array Lists.
 *  @author Jean Naude
 *  @version 1.0 March 2009  
 */
public class Persons
{

	//*******************constructor
	/**
	 * Creates a new empty Persons object
	 */
	public Persons() 
	{
		personList = new ArrayList<Person>();
	}//default constructor

	//*******************utility methods

	/** Initializes the next number to be allocated to a Person with the 
	 *  number saved (in the FILE_NAME text file).
	 *  @throws NumberFormatException if the person number is not a valid integer
	 *  @throws IllegalArgumentException if the person number is invalid.
	 *  @throws ArrayIndexOutOfBoundsException if there is not at least one value 
	 *          in the string
	 */
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
			Person.setNextNumber(1);
		}
		finally
		{
			if (inFile != null) inFile.close();
		}     
	}//open

	/** Saves the next number to be allocated to a Person (to the FILE_NAME 
	 *  text file).
	 *  @throws FileNotFoundException if the file cannot be created
	 */   
	protected void close() throws FileNotFoundException
	{
		PrintWriter outFile = new PrintWriter(FILE_NAME);
		outFile.println(Person.getNextNumber());
		outFile.close();
	}//close 

	/** Writes all the persons in the collection to the given text file.
	 *  @param fileName the name of the text file
	 *  @throws FileNotFoundException if the file cannot be created.
	 */   
	protected void saveToFile(String fileName) throws FileNotFoundException
	{
		PrintWriter outFile = new PrintWriter(fileName);
		for (Person aPerson : personList)
			outFile.println(aPerson.toString());
		outFile.close();
	}//saveToFile

	/** Searches for a person with the given person number in the collection.
	 *  @param personNumber the number for which to search
	 *  @return the person if found, otherwise null
	 */ 
	protected Person search(long personNumber)
	{
		for (Person aPerson : personList)
			if (aPerson.getNumber() == personNumber)
				return aPerson;

		return null;
	}//search

	/** Adds the given person to the collection.
	 *  @param aPerson the person to be added
	 */
	public void add(Person aPerson)
	{
		personList.add(aPerson);
	}//add

	/** Updates the given person in the collection.
	 *  @param aPerson the person to be updated
	 */
	public void update(Person aPerson)
	{
		//Unnecessary to do anything in the ArrayList implementation
		//            -- aPerson is already in the list
		/*Person existingPerson = find(aPerson.getPersonNumber());
if (existingPerson == null)
throw new IllegalArgumentException("Invalid person number: " 
		+ aPerson.getPersonNumber());
existingPerson = aPerson.clone();
		 */
	}//update

	/** Deletes the person with the given person number from the collection.
	 *  @param personNumber the number of the person to delete
	 */
	public void delete(long personNumber)
	{
		for (int i =0; i < personList.size(); i++)
			if (personList.get(i).getNumber() == personNumber)
			{
				personList.remove(i);
				return;
			}
	}//delete

	/** Returns all the persons in the collection
	 *  @return all persons
	 */
	public ArrayList<Person> getAll()
	{
		return personList;
	}//getAll

	//*******************instance variable 
	private ArrayList<Person> personList;

	//*******************class variable 
	private static final String FILE_NAME = "Persons.txt"; 

}//class Persons