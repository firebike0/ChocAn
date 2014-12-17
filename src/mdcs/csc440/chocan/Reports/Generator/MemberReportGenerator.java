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
import mdcs.csc440.chocan.Beans.Controller.Providers;
import mdcs.csc440.chocan.Beans.Controller.Services;
import mdcs.csc440.chocan.Reports.MemberReport;

/** Control class to co-ordinate the use case Produce Member Report.
*  @author Jean Naude
*  @version 1.0 March 2009
*/
public class MemberReportGenerator 
{
/** Creates a new member report generator which creates a new member report.
*  @param member the member about whom the report is generated
*  @param endDate a date within the week for which the report is to be
*         generated
*  @throws FileNotFoundException if the file cannot be created.
*/
public MemberReportGenerator(Member member, Date endDate) 
						throws FileNotFoundException
{
	Visits visits = null;
Providers providers = null;
Services services = null;

//Create a new member report
report = new MemberReport(member, endDate);

try
{						
//create and open the claims, providers and services collections
	visits = new Visits();
	visits.open();
providers = new Providers();
providers.open();
services = new Services();
services.open();
   					
//Get all claims submitted for services to this member, ordered by
//service date
ArrayList<Visit> memberVisits = 
visits.findByMember(member.getNumber());
	
//for each claim
for (Visit nextVisit : memberVisits)
{
	//test whether within date range
	if (nextVisit.getSubmissionDate()
				.after(report.getStartDateRange()) 
		&& nextVisit.getSubmissionDate()
				.before(report.getEndDateRange()))
	{
		//get provider
		String providerName;
		Provider provider = providers
			.find(nextVisit.getProviderNumber());
		if (provider == null) 
			providerName = "Invalid Number";
		else providerName = provider.getName();
		
		//get service
		String serviceName;
		Service service = services.find(nextVisit.getServiceCode());
		if ( service == null)
			serviceName = "Invalid Code";
		else serviceName = service.getName();
		
		//add service details to report
		report.addDetail(nextVisit.getServiceDate(), serviceName,
				providerName);
		
	}//if date in specified week
}//for

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
public MemberReport getReport()
{
return report;
}//getReport

//********************instance variable
private MemberReport report;

}//class MemberReportGenerator