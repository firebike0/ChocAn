package mdcs.csc440.chocan.Reports.Generator;

import java.util.Date;
import java.util.ArrayList;
import java.text.ParseException;
import java.io.FileNotFoundException;

import mdcs.csc440.chocan.Beans.Visit;
import mdcs.csc440.chocan.Beans.Member;
import mdcs.csc440.chocan.Beans.Provider;
import mdcs.csc440.chocan.Beans.Service;
import mdcs.csc440.chocan.Beans.Controller.MemberController;
import mdcs.csc440.chocan.Beans.Controller.VisitController;
import mdcs.csc440.chocan.Beans.Controller.ServiceController;
import mdcs.csc440.chocan.Reports.ProviderReport;

//Control class that handles the Produce Provider Report.


public class ProviderReportGenerator 
{

	public ProviderReportGenerator(Provider provider, Date endDate) 
			throws FileNotFoundException
	{
		VisitController visits = null;
		MemberController members = null;
		ServiceController services = null;

		//Create a new provider report
		report = new ProviderReport(provider, endDate);

		try
		{	
			visits = new VisitController();
			visits.open();
			members = new MemberController();
			members.open();
			services = new ServiceController();
			services.open();

			int noConsultations = 0;  //use to count number of consultations
			double totalFee = 0;      //use to accumulate fee

			//get all visits submitted by provider
			ArrayList<Visit> providerVisits = 
					visits.findByProvider(provider.getNumber());

			//for each visit
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

					//add visit details to report
					report.addDetail(nextVisit.getSubmissionDate(),
							nextVisit.getServiceDate(), nextVisit.getMemberNumber(),
							memberName, nextVisit.getServiceCode(), serviceFee);

					//increment number of consultations
					noConsultations++;

					//accumulate fee
					totalFee += serviceFee;
				}
			}

			
			report.addSummary(noConsultations, totalFee);
		}
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

	}

	public ProviderReport getReport()
	{
		return report;
	}

	private ProviderReport report;

}