package mdcs.csc440.chocan.Beans.Controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;

import mdcs.csc440.chocan.Beans.Service;

/** This class models a collection of Service objects.
 *  @author Jean Naud
 *  @version 1.0 March 2009
 */
public class Services
{
	//***************instance variable	
	private ArrayList<Service> serviceList;	//the collection of services
	//***************constructor

	/**
	 * Creates a new empty Services object
	 */
	public Services() 
	{
		serviceList = new ArrayList<Service>();
	}//default constructor

	/** Reads all the services in the FILE_NAME text file into the collection.
	 *  @throws ParseException if the fee is an not a valid double
	 *  @throws IllegalArgumentException if the code, name or fee are invalid.
	 */
	public void open() throws ParseException
	{
		Scanner inFile = null;
		try
		{
			FileReader reader = new FileReader(FILE_NAME);
			inFile = new Scanner(reader);

			//Each line represents a service
			while(inFile.hasNextLine())
			{
				//read line
				String line = inFile.nextLine();

				//create service with this data
				Service aService = new Service(line);

				//add service to the collection
				add(aService);
			}
		}
		catch (FileNotFoundException ex)		
		{
			//No services saved
		}
		finally
		{
			if (inFile != null)inFile.close();
		}		
	}//open

	/** Writes all the services in the collection to the FILE_NAME text file.
	 *  @throws FileNotFoundException if the file cannot be created
	 */	
	public void close() throws FileNotFoundException
	{
		PrintWriter outFile = new PrintWriter(FILE_NAME);
		for (Service aService : serviceList)
			outFile.println(aService.toString());
		outFile.close();
	}//close

	/** Searches for a service with the given service code in the collection.
	 *  @param serviceCode the code for which to search
	 *  @return the service if found, otherwise null
	 */ 
	public Service find(String serviceCode)
	{
		for (Service aService : serviceList)
			if (aService.getCode().equals(serviceCode))
				return aService;
		/*	for(int i=0; i < serviceList.size(); i++)
		if(serviceList.get(i).getCode().equals(serviceCode))
			return serviceList.get(i);*/

		return null;
	}//search

	/** Adds the given service to the collection.
	 *  Services are stored in alphabetical order of service name to facilitate
	 *  Provider Directory (Service Report) generation.
	 *  @param aService the service to be added
	 *  @throws IllegalArgumentException if the code of the service to be added
	 *				is not unique.
	 */
	public void add(Service aService)
	{
		Service tempService = find(aService.getCode());
		if (tempService != null) throw new IllegalArgumentException
		("A service with this code already exists");
		for (int i = 0; i < serviceList.size(); i++)
		{
			if (aService.getName().compareTo(serviceList.get(i).getName()) < 0)
			{
				serviceList.add(i, aService);
				return;
			}
		}
		//add to end of list
		serviceList.add(aService);
	}//add

	/** Updates the given service in the collection.
	 *  @param aService the service to be updated
	 */
	public void update(Service aService)
	{
		//if the name of the service has been changed, the list may no longer be
		//in alphabetical order.  Thus, delete the service from the list
		//and add it again.
		delete(aService.getCode());
		add(aService);
	}//update

	/** Deletes the service with the given service number from the collection
	 *  @param serviceCode the code of the service to delete
	 */
	public void delete(String serviceCode)
	{
		for (int i = 0; i < serviceList.size(); i++)
			if (serviceList.get(i).getCode().equals(serviceCode))
			{
				serviceList.remove(i);
				return;
			}
	}//delete

	/** Returns all the services in the collection
	 *  @return all the services
	 */
	public ArrayList getAllOrderedByName()
	{
		return serviceList;
	}//getAllOrderedByName


	//***************class variable 
	private static final String FILE_NAME = "Services.txt"; 

}//class Services