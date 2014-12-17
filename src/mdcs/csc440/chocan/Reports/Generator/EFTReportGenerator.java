package mdcs.csc440.chocan.Reports.Generator;

import java.util.Date;
import java.util.ArrayList;
import java.text.ParseException;
import java.io.FileNotFoundException;

import mdcs.csc440.chocan.Beans.Visit;
import mdcs.csc440.chocan.Beans.Person;
import mdcs.csc440.chocan.Beans.Provider;
import mdcs.csc440.chocan.Beans.Service;
import mdcs.csc440.chocan.Beans.Controller.Visits;
import mdcs.csc440.chocan.Beans.Controller.Providers;
import mdcs.csc440.chocan.Beans.Controller.Services;
import mdcs.csc440.chocan.Reports.EFTReport;

/** Control class to co-ordinate the use case Produce EFT Data.
 *  @author Jean Naude
 *  @version 1.0 March 2009
 */
public class EFTReportGenerator 
{
	/** Creates a new EFT report generator which creates a new 
	 *  EFT report.
	 *  @param endDate a date within the week for which the report is to be
	 *         generated
	 *  @throws FileNotFoundException if the file cannot be created.
	 */
	public EFTReportGenerator(Date endDate) throws FileNotFoundException
	{
		Visits visits = null;
		Services services = null;
		Providers providers = null;

		//create a new EFT report
		report = new EFTReport(endDate);

		try
		{	
			//create and open claims, services and providers collections					
			visits = new Visits();
			visits.open();
			services = new Services();
			services.open();
			providers = new Providers();
			providers.open();

			//get all providers
			ArrayList<Person> allProviders = providers.getAll(); 

			//for each provider
			for (Person person : allProviders)
			{
				double totalFee = 0; //accumulates fees payable to provider

				Provider provider = (Provider)person;

				//get all claims submitted by provider
				ArrayList<Visit> providerVisits = 
						visits.findByProvider(provider.getNumber());

				//for each claim
				for (Visit nextVisit : providerVisits)
				{
					//test whether claim is witin date range
					if (nextVisit.getSubmissionDate()
							.after(report.getStartDateRange()) 
							&& nextVisit.getSubmissionDate()
							.before(report.getEndDateRange()))
					{						
						//get service fee
						double serviceFee;
						Service service = services.find(nextVisit.getServiceCode());
						if ( service == null)
							throw new IllegalStateException("Invalid Code"); 
						else serviceFee = service.getFee();

						//accumulate fees payable
						totalFee += serviceFee;

					}//if date in specified week
				}//for each claim				

				if (totalFee > 0)
				{
					//add fees payable detail to report
					report.addDetail(provider.getNumber(), totalFee,
							provider.getName());
				}
			}//for each provider		
		}//try
		catch (ParseException ex)
		{
			report.addErrorMessage(ex.getMessage());
		}
		finally
		{
			if (visits != null) visits.close();
			if (providers != null) providers.close();
			if (services != null) services.close();
		}		

	}//constructor

	/** Returns the report
	 *  @return the report
	 */
	public EFTReport getReport()
	{
		return report;
	}//getReport

	//********************instance variable
	private EFTReport report;

}//class EFTReportGenerator