package mdcs.csc440.chocan.Reports;

import java.util.Date;

// Class that models the EFT data report.
public class EFTReport extends DateRangeReport 
{
	
	public EFTReport(Date anEndDate) 
	{
		super(anEndDate);
	}

	// Adds a line of detail about a provider to be paid to the report
	public void addDetail(long providerNumber, double totalFee, 
			String providerName)
	{		
		String totalFeeString = currencyFormatter.format(totalFee);
		formatter.format("%9d  %12s  %s%n", providerNumber,  totalFeeString,
				providerName);
	}

}
