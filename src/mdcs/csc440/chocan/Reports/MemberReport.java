package mdcs.csc440.chocan.Reports;

import java.util.Date;

import mdcs.csc440.chocan.Beans.Member;

// Class that models a member report
public class MemberReport extends DateRangeReport 
{
	
	public MemberReport(Member aMember, Date anEndDate) 
	{
		super(anEndDate);

		addHeading("Services Received");		

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
	}

	// Adds a line of detail about a service received by the member.
	public void addDetail(Date serviceDate, String serviceName, 
			String providerName)
	{		
		String serviceDateString = dateFormatter.format(serviceDate);
		formatter.format("%12s    %-20s  %s%n", 
				serviceDateString, serviceName, providerName);
		detailCount++;
	}

	// Returns the number of lines of detail added to the report
	public int getDetailCount()
	{
		return detailCount;
	}

	private int detailCount;

}
