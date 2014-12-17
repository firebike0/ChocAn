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
import mdcs.csc440.chocan.Reports.AccountsPayableReport;

// Control class to produce Accounts Payable Report.
public class AccountsPayableReportGenerator 
{
	
	public AccountsPayableReportGenerator(Date endDate) 
			throws FileNotFoundException
	{
		Visits visits = null;
		Services services = null;
		Providers providers = null;

		//create a new accounts payable report
		report = new AccountsPayableReport(endDate);

		try
		{						
			visits = new Visits();
			visits.open();
			services = new Services();
			services.open();
			providers = new Providers();
			providers.open();

			int totalNoConsultations = 0; //accumulates number of consultations
			int providerCount = 0;     //counts number of providers to be paid
			double grandTotalFee = 0;  //accumulates all fees payable

			ArrayList<Person> allProviders = providers.getAll(); 

			for (Person person : allProviders)
			{
				int noConsultations = 0;  //counts this provider's visit
				double totalFee = 0; //accumulates fees payable to this provider 

				Provider provider = (Provider)person;

				ArrayList<Visit> providerVisits = visits.findByProvider(provider.getNumber());

				//for each visit
				for (Visit nextVisit : providerVisits)
				{
					//test whether within date range
					if (nextVisit.getSubmissionDate()
							.after(report.getStartDateRange()) 
							&& nextVisit.getSubmissionDate()
							.before(report.getEndDateRange()))
					{						
						double serviceFee;
						Service service 
						= services.find(nextVisit.getServiceCode());
						if ( service == null)
							serviceFee = 0;   //indicates invalid code
						else serviceFee = service.getFee();

						noConsultations++;
						
						//accumulate fees payable
						totalFee += serviceFee;
					}
				}

				if (noConsultations > 0)
				{
					//add provider details to report
					report.addDetail(provider.getNumber(), noConsultations, totalFee, provider.getName());

					totalNoConsultations += noConsultations;

					//accumulate fees payable for all providers
					grandTotalFee += totalFee;

					providerCount++;
				}
			}

			//add summary to report		
			report.addSummary(totalNoConsultations, grandTotalFee, providerCount);
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
	
	public AccountsPayableReport getReport()
	{
		return report;
	}

	private AccountsPayableReport report;

}