package mdcs.csc440.chocan.Reports.Generator;

import java.util.Date;
import java.util.ArrayList;
import java.text.ParseException;
import java.io.FileNotFoundException;

import mdcs.csc440.chocan.Beans.Visit;
import mdcs.csc440.chocan.Beans.Member;
import mdcs.csc440.chocan.Beans.Provider;
import mdcs.csc440.chocan.Beans.Service;
import mdcs.csc440.chocan.Beans.Controller.Visits;
import mdcs.csc440.chocan.Beans.Controller.Members;
import mdcs.csc440.chocan.Beans.Controller.Services;
import mdcs.csc440.chocan.Reports.ProviderReport;

/** Control class to co-ordinate the use case Produce Provider Report.
 *  @author Jean Naude
 *  @version 1.0 March 2009
 */

public class ProviderReportGenerator 
{

	/** Creates a new provider report generator which creates a new provider report.
	 *  @param provider the provider about whom the report is generated
	 *  @param endDate a date within the week for which the report is to be
	 *         generated
	 *  @throws FileNotFoundException if the file cannot be created.
	 */
	public ProviderReportGenerator(Provider provider, Date endDate) 
			throws FileNotFoundException
	{
		Visits visits = null;
		Members members = null;
		Services services = null;

		//Create a new provider report
		report = new ProviderReport(provider, endDate);

		try
		{						
			//create and open the collections of claims, members and services
			visits = new Visits();
			visits.open();
			members = new Members();
			members.open();
			services = new Services();
			services.open();

			int noConsultations = 0;  //use to count number of consultations
			double totalFee = 0;      //use to accumulate fee

			//get all claims submitted by provider
			ArrayList<Visit> providerVisits = 
					visits.findByProvider(provider.getNumber());

			//for each claim
			for (Visit nextVisit : providerVisits)
			{
				//test whether within date range
				if (nextVisit.getSubmissionDate().after(report.getStartDateRange()) 
						&& nextVisit.getSubmissionDate().before(report.getEndDateRange()))
				{
					//get the member to whom the service was provided
					String memberName;
					Member member = members.find(nextVisit.getMemberNumber());
					if (member == null) 
						memberName = "Invalid Number";
					else memberName = member.getName();

					//get the fee for the service
					double serviceFee;
					Service service = services.find(nextVisit.getServiceCode());
					if ( service == null)
						serviceFee = 0;   //indicates invalid code
					else serviceFee = service.getFee();

					//add claim details to report
					report.addDetail(nextVisit.getSubmissionDate(),
							nextVisit.getServiceDate(), nextVisit.getMemberNumber(),
							memberName, nextVisit.getServiceCode(), serviceFee);

					//increment number of consultations
					noConsultations++;

					//accumulate fee
					totalFee += serviceFee;
				}//if date in specified week
			}//for

			//add summary details to report
			report.addSummary(noConsultations, totalFee);
		}//try
		catch (ParseException ex)
		{
			report.addErrorMessage(ex.getMessage());
		}
		finally
		{
			if (visits != null) visits.close();
			if (members != null) members.close();
			if (services != null) services.close();
		}		

	}//constructor

	/** Returns the report
	 *  @return the report
	 */
	public ProviderReport getReport()
	{
		return report;
	}//getReport

	//********************instance variable
	private ProviderReport report;

}//class ProviderReportGenerator