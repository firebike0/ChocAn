package mdcs.csc440.chocan.Reports;

import java.util.Date;

import mdcs.csc440.chocan.Beans.Provider;

/** Boundary class that models a provider report
 *  @author Jean Naude
 *  @version 1.0 March 2009
 */

public class ProviderReport extends DateRangeReport 
{

	/**
	 * Creates a new ProviderReport.
	 * @param aProvider the provider whose details and claims are to be included
	 * @param anEndDate the date to be included in the date range
	 */
	public ProviderReport(Provider aProvider, Date anEndDate) 
	{
		super(anEndDate);   //call the superclass constructor

		//Add a title
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

		//Add detail headings	
		formatter.format("Submission Date-Time  Service Date    Code       Fee" 
				+ "  Member No.  Member Name%n");
		formatter.format("--------------------  ------------    ----       ---" 
				+ "  ----------  -----------%n");
		detailCount = 0;
	}// constructor

	/** Adds a line of detail about a claim submitted
	 *  @param submitDate the date of submission
	 *  @param serviceDate the date of the service provided
	 *  @param memberNumber the number of the member to whom the service was
	 *                      rendered
	 *  @param memberName the name of the member to whom the service was rendered
	 *  @param serviceCode the code of the service that was provided
	 *  @param serviceFee the fee for the service provided
	 */
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
	}//addDetail

	/** Adds a summary to the report
	 *  @param noConsultations the number of consultations the provider had
	 *  @param totalFee the total fee payable to the provider
	 */
	public void addSummary(int noConsultations, double totalFee)
	{
		formatter.format("%nNumber of consultations: %d%n", noConsultations);
		String totalFeeString = currencyFormatter.format(totalFee);
		formatter.format("Total Fee: %s%n", totalFeeString);
	}//addSummary	

	/** Returns the number of lines of detail added to the report
	 *  @return the number of lines of detail
	 */
	public int getDetailCount()
	{
		return detailCount;
	}//getDetailCount

	//************************instance variable
	private int detailCount;

}//class ProviderReport
