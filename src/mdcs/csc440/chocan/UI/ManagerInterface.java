package mdcs.csc440.chocan.UI;

import java.util.Date;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import mdcs.csc440.chocan.Beans.Member;
import mdcs.csc440.chocan.Beans.Provider;
import mdcs.csc440.chocan.Beans.Controller.MemberController;
import mdcs.csc440.chocan.Beans.Controller.ProviderController;
import mdcs.csc440.chocan.Reports.AccountsPayableReport;
import mdcs.csc440.chocan.Reports.MemberReport;
import mdcs.csc440.chocan.Reports.ProviderReport;
import mdcs.csc440.chocan.Reports.Generator.AccountsPayableReportGenerator;
import mdcs.csc440.chocan.Reports.Generator.MemberReportGenerator;
import mdcs.csc440.chocan.Reports.Generator.ProviderReportGenerator;

//The class simulates the Manager subsystem
public class ManagerInterface
{

	// Creates a new Reporting Subsystem
	public ManagerInterface()
	{
		ui = new UserInterface();
		String menuText = "1.\tProvider Report\n" +
				"2.\tMember Report\n" +
				"3.\tAccounts Payable Report\n" +
				"4.\tQuit\n";

		int choice;
		do
		{
			ui.message("\t\t\tManager Subsystem");

			//display menu and get choice
			choice = ui.menu(menuText);
			switch(choice)
			{
			//use case Produce Provider Report
			case 1: providerReport(); break;

			//use case Produce Member Report
			case 2: memberReport(); break;

			//use case Produce Accounts Payable Report
			case 3: accountsPayableReport(); break;

			//Quit
			case 4: break;
			default: ui.errorMessage("Invalid choice.  Please re-enter.");
			}
		}while (choice != 4);

	}

	private void providerReport()
	{
		try
		{
			//Get the provider number
			long number = ui.promptForLong("Provider Number: ");

			//Search for the provider number in the collection
			ProviderController providers = new ProviderController();
			providers.open();
			Provider theProvider = providers.find(number);
			providers.close();

			if (theProvider == null)
			{
				ui.errorMessage("Invalid Provider Number");
				return;
			}

			//Get the end date
			Date endDate = ui.promptForDate("End date of week ("  + UserInterface.DATE_FORMAT + "): ");

			//Generate report for specified week
			ProviderReportGenerator generator = new ProviderReportGenerator(theProvider, endDate);
			ProviderReport report = generator.getReport();

			//Display the report
			report.display(ui);

			//Save the report to a file, "print", if the user wishes
			String answer = ui.promptForString("Save the report? (Y)es or (N)o: ");
			if (answer != null && answer.length() >= 1 &&			
					Character.toUpperCase(answer.charAt(0)) == 'Y')
			{
				report.print(theProvider.getName());
				ui.message("The report has been saved as " 
						+ report.getFileName() +"\n");
			}
			else ui.message("Report not saved");
		}
		catch (FileNotFoundException ex)			
		{
			ui.errorMessage(ex.getMessage());
		}

	}

	private void memberReport()
	{
		try
		{
			//Get the member number
			long number = ui.promptForLong("Member Number: ");

			//Search for the member in the collection
			MemberController members = new MemberController();
			members.open();
			Member theMember = members.find(number);
			members.close();

			if (theMember == null)
			{
				ui.errorMessage("Invalid Member Number");
				return;
			}

			//Get the end date
			Date endDate = ui.promptForDate("End date of week (" + UserInterface.DATE_FORMAT + "): ");

			//Generate report for specified week
			MemberReportGenerator generator = new MemberReportGenerator(theMember, endDate);
			MemberReport report = generator.getReport();

			//Display the report
			report.display(ui);

			//Save the report to a file, "print", if the user wishes
			String answer = ui.promptForString
					("Save the report? (Y)es or (N)o: ");
			if (answer != null && answer.length() >= 1 &&			
					Character.toUpperCase(answer.charAt(0)) == 'Y')
			{
				report.print(theMember.getName());
				ui.message("The report has been saved as " 
						+ report.getFileName() +"\n");
			}	
			else ui.message("Report not saved");
		}		
		catch (FileNotFoundException ex)			
		{
			//occurs if the file cannot be created
			ui.errorMessage(ex.getMessage());
		}
	}

	private void accountsPayableReport()
	{
		try
		{
			//Get the end date
			Date endDate = ui.promptForDate("End date of week (" + UserInterface.DATE_FORMAT + "): ");

			//Generate report for specified week
			AccountsPayableReportGenerator generator = new AccountsPayableReportGenerator(endDate);
			AccountsPayableReport report = generator.getReport();

			//Display the report
			report.display(ui);

			//"Print" (save the report to a file) if the user wishes
			String answer = ui.promptForString("Save the report? (Y)es or (N)o: ");
			if (answer != null && answer.length() >= 1 &&			
					Character.toUpperCase(answer.charAt(0)) == 'Y')
			{
				report.print("Accounts Payable");
				ui.message("The report has been saved as " + report.getFileName() + "\n");
			}
			else ui.message("Report not saved");

		}	
		catch (FileNotFoundException ex)			
		{
			//occurs if the file cannot be created
			ui.errorMessage(ex.getMessage());
		}
	}

	private UserInterface ui;
}