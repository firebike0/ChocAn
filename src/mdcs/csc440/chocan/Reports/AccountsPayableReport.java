package mdcs.csc440.chocan.Reports;

import java.util.Date;

// Boundary class that models an accounts payable report
public class AccountsPayableReport extends DateRangeReport 
{
	
	public AccountsPayableReport(Date anEndDate) 
	{
		super(anEndDate);   //call the superclass constructor

		addHeading("Accounts Payable");		

		//Add date
		String dateString = dateFormatter.format(getEndDate());
		formatter.format("Week Ending: %s%n%n", dateString);

		//Add detail headings	
		formatter.format("Provider Number   Consultations           Fee  "
				+"Provider Name%n"); 
		formatter.format("---------------   -------------    ----------  "
				+"-------------%n"); 
	}

	// Adds a line of detail about a provider to the report
	public void addDetail(long providerNumber,  int noConsultations, double totalFee, String providerName)
	{		
		String totalFeeString = currencyFormatter.format(totalFee);
		formatter.format("%15d   %13d  %12s  %s%n", 
				providerNumber, noConsultations, totalFeeString, providerName);
	}

	public void addSummary(int totalNoConsultations, double grandTotalFee, 
			int providerCount)
	{
		String grandTotalFeeString = currencyFormatter.format(grandTotalFee);
		formatter.format("---------------   -------------    ----------  "
				+"-------------%n"); 
		formatter.format("Totals:           %13d  %12s     %d%n", 
				totalNoConsultations, grandTotalFeeString, providerCount);
	}

}
