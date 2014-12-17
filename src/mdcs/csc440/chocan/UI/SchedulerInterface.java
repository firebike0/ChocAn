package mdcs.csc440.chocan.UI;

import java.util.Date;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import mdcs.csc440.chocan.Beans.Member;
import mdcs.csc440.chocan.Beans.Person;
import mdcs.csc440.chocan.Beans.Provider;
import mdcs.csc440.chocan.Beans.Controller.Members;
import mdcs.csc440.chocan.Beans.Controller.Providers;
import mdcs.csc440.chocan.Reports.MemberReport;
import mdcs.csc440.chocan.Reports.ProviderReport;
import mdcs.csc440.chocan.Reports.Generator.AccountsPayableReportGenerator;
import mdcs.csc440.chocan.Reports.Generator.EFTReportGenerator;
import mdcs.csc440.chocan.Reports.Generator.MemberReportGenerator;
import mdcs.csc440.chocan.Reports.Generator.ProviderReportGenerator;

/** Simulates the Scheduler Interface of the ChocAn System.  Through this
 *  interface (boundary class) the scheduler (tester in this implementation)
 *  starts the use case Run Accounting Procedure.
 *  @author Jean Naude
 *  @version 1.0 March 2009
 */
public class SchedulerInterface 
{

	/**
	 * Creates a new SchedulerInterface which then runs the accounting procedure.
	 */
	public SchedulerInterface() 
	{
		//for communciation with the tester
		UserInterface ui = new UserInterface();
		ui.message("Running the accounting procedure ...\n");

		//Use today's date for all reports
		Date now = new Date();

		try
		{
			//Generate provider reports
			ui.message("Generating the providers' reports ...");
			Providers providers	= new Providers();
			providers.open();
			ArrayList<Person> allProviders = providers.getAll(); 
			for (Person person : allProviders)
			{
				Provider provider = (Provider) person;
				ProviderReportGenerator generator 
				= new ProviderReportGenerator(provider, now);
				ProviderReport theReport =	generator.getReport() ;
				if (theReport.getDetailCount() > 0)
					theReport.sendByEmail(provider.getName());
			}
			providers.close();

			//Generate member reports
			ui.message("Generating the members' reports ...");
			Members members = new Members();
			members.open();
			ArrayList<Person> allMembers = members.getAll(); 
			for (Person person : allMembers)
			{
				Member member = (Member) person;
				MemberReportGenerator generator 
				= new MemberReportGenerator(member, now);
				MemberReport theReport = generator.getReport();
				if(theReport.getDetailCount() > 0)
					theReport.sendByEmail(member.getName());
			}
			members.close();

			//Generate accounts payable report
			ui.message("Generating the accounts payable report ...");
			AccountsPayableReportGenerator generator 
			= new AccountsPayableReportGenerator(now);
			generator.getReport().sendByEmail("Accounts Payable");

			//Generate EFT data
			ui.message("Generating the EFT data ...");
			EFTReportGenerator eftGenerator = new EFTReportGenerator(now);
			eftGenerator.getReport().print("EFT Data");

			ui.message("\nAccounting procedure completed successfully.\n");	
		}
		catch (FileNotFoundException ex)
		{
			//occurs if a file cannot be created
			ui.errorMessage(ex.getMessage());
		}	

	}//default constructor

	//************************************************************
	/**
	 * Runs the accounting procedure independently of subsystems
	 * @param args not used
	 */
	public static void main(String[] args) 
	{
		try
		{
			new SchedulerInterface();
		}
		catch (NoSuchElementException ex)
		{
			UserInterface ui = new UserInterface();
			ui.message("\nEnd of test run.\n");
		}
	}//main	
}//SchedulerInterface