package mdcs.csc440.chocan.Reports;

import java.util.Date;

import mdcs.csc440.chocan.Beans.Member;

/** Boundary class that models a member report
 *  @author Jean Naude
 *  @version 1.0 March 2009
 */

public class MemberReport extends DateRangeReport 
{

	/**
	 * Creates a new MemberReport.
	 * @param aMember the member whose details and services received 
	 *                are to be included
	 * @param anEndDate the date to be included in the date range
	 */
	public MemberReport(Member aMember, Date anEndDate) 
	{
		super(anEndDate);   //call the superclass constructor

		//Add a title
		addHeading("Services Received");		

		//Add date and member details
		String dateString = dateFormatter.format(getEndDate());
		formatter.format("Week Ending: %s %20s  Member Number: %d%n%n",
				dateString,"", aMember.getNumber());
		formatter.format("Member Name: %-32s Address: %s%n", 
				aMember.getName(), aMember.getStreet());
		formatter.format("%54s %s%n", "",aMember.getCity());
		formatter.format("%54s %s%n", "",aMember.getState());
		formatter.format("%54s %s%n%n", "",aMember.getZip());

		//Add detail headings	
		formatter.format("Service Date    Service               Provider%n"); 
		formatter.format("------------    -------               --------%n"); 
		detailCount = 0;
	}// constructor

	/** Adds a line of detail about a service received by the member.
	 *  @param serviceDate the date of the service
	 *  @param serviceName the name of the service
	 *  @param providerName the name of the provider who rendered the service
	 */
	public void addDetail(Date serviceDate, String serviceName, 
			String providerName)
	{		
		String serviceDateString = dateFormatter.format(serviceDate);
		formatter.format("%12s    %-20s  %s%n", 
				serviceDateString, serviceName, providerName);
		detailCount++;
	}//addDetail

	/** Returns the number of lines of detail added to the report
	 *  @return the number of lines of detail
	 */
	public int getDetailCount()
	{
		return detailCount;
	}//getDetailCount

	//************************instance variable
	private int detailCount;

}//class MemberReport
