package mdcs.csc440.chocan.Reports;

import java.util.Date;

/** Boundary class that models the EFT data report.
 *  @author Jean Naude
 *  @version 1.0 March 2009
 */
public class EFTReport extends DateRangeReport 
{

	/**
	 * Creates a new EFT Report.
	 * @param anEndDate the date to be included in the date range
	 */
	public EFTReport(Date anEndDate) 
	{
		super(anEndDate);   //call the superclass constructor
	}// constructor

	/** Adds a line of detail about a provider to be paid to the report
	 *  @param providerNumber the provider's number
	 *  @param totalFee the total fee payable to the provider
	 *  @param providerName the provider's name
	 */
	public void addDetail(long providerNumber, double totalFee, 
			String providerName)
	{		
		String totalFeeString = currencyFormatter.format(totalFee);
		formatter.format("%9d  %12s  %s%n", providerNumber,  totalFeeString,
				providerName);
	}//addDetail

}//class EFTReport
