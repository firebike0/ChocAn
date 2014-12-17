package mdcs.csc440.chocan.Beans.Controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;

import mdcs.csc440.chocan.Beans.Service;

// This class models a collection of Service objects.
public class Services
{
	
	private ArrayList<Service> serviceList;

	public Services() 
	{
		serviceList = new ArrayList<Service>();
	}

	// Reads all the services in the FILE_NAME text file into the collection.
	public void open() throws ParseException
	{
		Scanner inFile = null;
		try
		{
			FileReader reader = new FileReader(FILE_NAME);
			inFile = new Scanner(reader);

			while(inFile.hasNextLine())
			{
				String line = inFile.nextLine();

				//create service with this data
				Service aService = new Service(line);

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
	}

	// Writes all the services in the collection to the FILE_NAME text file.
	public void close() throws FileNotFoundException
	{
		PrintWriter outFile = new PrintWriter(FILE_NAME);
		for (Service aService : serviceList)
			outFile.println(aService.toString());
		outFile.close();
	}

	// Searches for a service in the collection.
	public Service find(String serviceCode)
	{
		for (Service aService : serviceList)
			if (aService.getCode().equals(serviceCode))
				return aService;
		//for some reason wasnt playing friendly... switched to enhanced and it works?
		/*for(int i=0; i < serviceList.size(); i++)
		if(serviceList.get(i).getCode().equals(serviceCode))
			return serviceList.get(i);*/

		return null;
	}

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
	}

	public void update(Service aService)
	{
		//if the name of the service has been changed, the list may no longer be
		//in alphabetical order.  Thus, delete the service from the list
		//and add it again.
		delete(aService.getCode());
		add(aService);
	}

	// Deletes the service from the collection
	public void delete(String serviceCode)
	{
		for (int i = 0; i < serviceList.size(); i++)
			if (serviceList.get(i).getCode().equals(serviceCode))
			{
				serviceList.remove(i);
				return;
			}
	}

	public ArrayList getAllOrderedByName()
	{
		return serviceList;
	}

	private static final String FILE_NAME = "Services.txt"; 

}