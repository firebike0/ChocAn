package mdcs.csc440.chocan.Beans.Controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;

import mdcs.csc440.chocan.Beans.Visit;

// Visits represents a collection of Visit objects.
public class Visits
{

	public Visits() 
	{
		visitList = new ArrayList<Visit>();
	}

	// Reads all the claims in the FILE_NAME text file into the collection.
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
				Visit aClaim = new Visit(line);
				add(aClaim);
			}
		}
		catch (FileNotFoundException ex)    
		{
			//No claims saved.  Not an error.  Do nothing.
		}
		finally
		{
			if (inFile != null) inFile.close();
		}     
	}

	// Writes all the claims in the collection to the FILE_NAME text file. 
	public void close() throws FileNotFoundException 
	{
		PrintWriter outFile = new PrintWriter(FILE_NAME);
		for (Visit aVisit : visitList)
			outFile.println(aVisit.toString());
		outFile.close();
	}

	public void add(Visit aVisit)
	{
		visitList.add(aVisit);
	}

	// Finds all the visits submitted by a given provider.
	public ArrayList<Visit> findByProvider(long providerNumber)
	{
		ArrayList<Visit> providerVisits = new ArrayList<Visit>();
		for(Visit aVisit : visitList )
			if (aVisit.getProviderNumber() == providerNumber)
				providerVisits.add(aVisit);

		return providerVisits;
	}

	// Finds all the claims for services provided to a given member.
	public ArrayList<Visit> findByMember(long memberNumber)
	{
		ArrayList<Visit> memberClaims = new ArrayList<Visit>();
		for(Visit aVisit : visitList )
			if (aVisit.getMemberNumber() == memberNumber)
				memberClaims.add(aVisit);

		//Sort by service date (Use a bubble sort for clarity)
		for (int i = memberClaims.size() - 1; i > 0; i--)
			for (int j = 0; j < i; j++)
				if (memberClaims.get(j).getServiceDate()
						.after(memberClaims.get(i).getServiceDate()))
				{
					Visit temp = memberClaims.get(i);
					memberClaims.set(i, memberClaims.get(j));
					memberClaims.set(j, temp);
				}     

		return memberClaims;
	}

	private static final String FILE_NAME = "Visits.txt";

	private ArrayList<Visit> visitList;

}
