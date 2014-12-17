package mdcs.csc440.chocan.Beans.Controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;

import mdcs.csc440.chocan.Beans.Visit;

/** Claims represents a collection of Claim objects.  Claim objects can be added
 *  to the collection, but never deleted or updated. The collection can be asked 
 *  for all the claims submitted by a given provider and for all the claims for
 *  services rendered to a given member.
 *  @author Jean Naude
 *  @version 1.0 March 2009
 */ 

public class Visits
{
	/**
	 * Creates a new empty Claims object
	 */
	public Visits() 
	{
		visitList = new ArrayList<Visit>();
	}//default constructor

	/** Reads all the claims in the FILE_NAME text file into the collection.
	 *  @throws NumberFormatException if the provider or member number is 
	 *             not a valid integer
	 *  @throws IllegalArgumentException if any of the values are
	 *             invalid.
	 *  @throws IndexOutOfBoundsException if there are 
	 *             not enough values in the string
	 *  @throws ParseException if the submission date and time or the 
	 *             service date is not in the correct format
	 */
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
	}//open

	/** Writes all the claims in the collection to the FILE_NAME text file.
	 *  @throws FileNotFoundException if the file cannot be created.
	 */   
	public void close() throws FileNotFoundException 
	{
		PrintWriter outFile = new PrintWriter(FILE_NAME);
		for (Visit aVisit : visitList)
			outFile.println(aVisit.toString());
		outFile.close();
	}//close

	/** Adds the given claim to the collection.
	 *  @param aClaim the claim to be added
	 */
	public void add(Visit aVisit)
	{
		visitList.add(aVisit);
	}//add

	/** Finds all the claims submitted by a given provider.
	 *  @param providerNumber the provider's number
	 *  @return all the claims submitted by the given provider
	 */
	public ArrayList<Visit> findByProvider(long providerNumber)
	{
		ArrayList<Visit> providerVisits = new ArrayList<Visit>();
		for(Visit aVisit : visitList )
			if (aVisit.getProviderNumber() == providerNumber)
				providerVisits.add(aVisit);

		return providerVisits;
	}//findByProvider

	/** Finds all the claims for services rendered to a given member.
	 *  @param memberNumber the member's number
	 *  @return all the claims for services rendered to the given member
	 */
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
	}//findByMember

	//class variable 
	private static final String FILE_NAME = "Visits.txt";
	//instance variable  
	private ArrayList<Visit> visitList;

}//class Claims
