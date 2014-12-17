package mdcs.csc440.chocan.Reports.Generator;

import java.util.Date;
import java.util.ArrayList;
import java.text.ParseException;
import java.io.FileNotFoundException;

import mdcs.csc440.chocan.Beans.Visit;
import mdcs.csc440.chocan.Beans.Person;
import mdcs.csc440.chocan.Beans.Provider;
import mdcs.csc440.chocan.Beans.Service;
import mdcs.csc440.chocan.Beans.Controller.VisitController;
import mdcs.csc440.chocan.Beans.Controller.ProviderController;
import mdcs.csc440.chocan.Beans.Controller.ServiceController;
import mdcs.csc440.chocan.Reports.EFTReport;

// Control class produce EFT Data.
public class EFTReportGenerator 
{

	public EFTReportGenerator(Date endDate) throws FileNotFoundException
	{
		VisitController visits = null;
		ServiceController services = null;
		ProviderController providers = null;

		//create a new EFT report
		report = new EFTReport(endDate);

		try
		{						
			visits = new VisitController();
			visits.open();
			services = new ServiceController();
			services.open();
			providers = new ProviderController();
			providers.open();

			ArrayList<Person> allProviders = providers.getAll(); 

			for (Person person : allProviders)
			{
				double totalFee = 0;

				Provider provider = (Provider)person;

				ArrayList<Visit> providerVisits = 
						visits.findByProvider(provider.getNumber());

				for (Visit nextVisit : providerVisits)
				{
					//test whether visit is within date range
					if (nextVisit.getSubmissionDate()
							.after(report.getStartDateRange()) 
							&& nextVisit.getSubmissionDate()
							.before(report.getEndDateRange()))
					{						
						double serviceFee;
						Service service = services.find(nextVisit.getServiceCode());
						if ( service == null)
							throw new IllegalStateException("Invalid Code"); 
						else serviceFee = service.getFee();

						totalFee += serviceFee;

					}
				}				

				if (totalFee > 0)
				{
					//add fees payable detail to report
					report.addDetail(provider.getNumber(), totalFee, provider.getName());
				}
			}		
		}
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

	}
	
	public EFTReport getReport()
	{
		return report;
	}

	private EFTReport report;

}