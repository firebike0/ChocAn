package mdcs.csc440.chocan.Reports;

import java.util.Date;

import mdcs.csc440.chocan.Beans.Provider;

// Class that models a provider report
public class ProviderReport extends DateRangeReport 
{

	public ProviderReport(Provider aProvider, Date anEndDate) 
	{
		super(anEndDate);   
		
		addHeading("Claim Submissions");		

		//Add date and provider details
		String dateString = dateFormatter.format(getEndDate());
		formatter.format("Week Ending: %s %20s  Provider Number: %d%n%n",
				dateString,"", aProvider.getNumber());
		formatter.format("Provider Name: %-30s Address: %s%n", 
				aProvider.getName(), aProvider.getStreet());
		formatter.format("%54s %s%n", "",aProvider.getCity());
		formatter.format("%54s %s%n", "",aProvider.getState());
		formatter.format("%54s %s%n%n", "",aProvider.getZip());
	
		formatter.format("Submission Date-Time  Service Date    Code       Fee" 
				+ "  Member No.  Member Name%n");
		formatter.format("--------------------  ------------    ----       ---" 
				+ "  ----------  -----------%n");
		detailCount = 0;
	}

	// Adds a line of detail about a visit
	public void addDetail(Date submitDate, Date serviceDate, long memberNumber,
			String memberName, String serviceCode, double serviceFee)
	{		
		String submitDateString = dateTimeFormatter.format(submitDate);
		String serviceDateString = dateFormatter.format(serviceDate);
		String serviceFeeString = currencyFormatter.format(serviceFee);
		formatter.format("%20s  %12s  %6s  %8s  %10d  %s%n", 
				submitDateString, serviceDateString, serviceCode, serviceFeeString,
				memberNumber, memberName);
		detailCount++;
	}

	// Adds a summary to the report
	public void addSummary(int noConsultations, double totalFee)
	{
		formatter.format("%nNumber of consultations: %d%n", noConsultations);
		String totalFeeString = currencyFormatter.format(totalFee);
		formatter.format("Total Fee: %s%n", totalFeeString);
	}

	// Returns the number of lines of detail added to the report
	public int getDetailCount()
	{
		return detailCount;
	}

	private int detailCount;

}
