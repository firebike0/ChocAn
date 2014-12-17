package mdcs.csc440.chocan.Reports;

import java.util.Date;

/** Boundary class that models an accounts payable report
 *  @author Jean Naude
 *  @version 1.0 March 2009
 */

public class AccountsPayableReport extends DateRangeReport 
{

	/**
	 * Creates a new AccountsPayableReport.
	 * @param anEndDate the date to be included in the date range
	 */
	public AccountsPayableReport(Date anEndDate) 
	{
		super(anEndDate);   //call the superclass constructor

		//Add a title
		addHeading("Accounts Payable");		

		//Add date
		String dateString = dateFormatter.format(getEndDate());
		formatter.format("Week Ending: %s%n%n", dateString);

		//Add detail headings	
		formatter.format("Provider Number   Consultations           Fee  "
				+"Provider Name%n"); 
		formatter.format("---------------   -------------    ----------  "
				+"-------------%n"); 
	}// constructor

	/** Adds a line of detail about a provider to the report
	 *  @param providerNumber the provider's number
	 *  @param noConsultations the number of consultations the provider had
	 *                         during the week
	 *  @param totalFee the total fee payable to the provider
	 *  @param providerName the provider's name
	 */
	public void addDetail(long providerNumber,  int noConsultations,
			double totalFee, String providerName)
	{		
		String totalFeeString = currencyFormatter.format(totalFee);
		formatter.format("%15d   %13d  %12s  %s%n", 
				providerNumber, noConsultations, totalFeeString, providerName);
	}//addDetail

	/** Adds a summary to the report
	 *  @param totalNoConsultations the total number of consultations for all 
	 *                              providers
	 *  @param grandTotalFee the total fee payable to all providers
	 *  @param providerCount the number of providers to be paid
	 */
	public void addSummary(int totalNoConsultations, double grandTotalFee, 
			int providerCount)
	{
		String grandTotalFeeString = currencyFormatter.format(grandTotalFee);
		formatter.format("---------------   -------------    ----------  "
				+"-------------%n"); 
		formatter.format("Totals:           %13d  %12s     %d%n", 
				totalNoConsultations, grandTotalFeeString, providerCount);
	}//addSummary

}//class AccountsPayableReport
