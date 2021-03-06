package mdcs.csc440.chocan.UI;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import mdcs.csc440.chocan.Beans.Member;
import mdcs.csc440.chocan.Beans.Provider;
import mdcs.csc440.chocan.Beans.ProviderDirectory;
import mdcs.csc440.chocan.Beans.Controller.MemberController;
import mdcs.csc440.chocan.Beans.Controller.ProviderController;
import mdcs.csc440.chocan.Reports.Generator.ServiceReportGenerator;

//This class simulates the provider terminal interface
public class ProviderInterface
{

	public ProviderInterface()
	{
		ui = new UserInterface();

		//Start use case Manage Session
		logon();

		String menuText = "\t1. Add Service Provided to Member\n" 
				+ "\t2. Request Provider Directory\n"
				+ "\t3. Quit\n"; 

		int choice;
		do 
		{      
			//display menu and read choice
			ui.message("\n\t\t\tProvider Subsystem\n");
			choice = ui.menu(menuText); 
			switch(choice)
			{
			//Use case Verify Member followed optionally by Submit visit
			case 1: verifyMember(); break;
			//Use case Receive Provider Directory
			case 2: receiveDirectory(); break;
			case 3: break;
			default: ui.errorMessage("Invalid choice.  Please re-enter.");
			}
		}while (choice != 3);

	}

	private void logon()
	{
		try
		{
			ProviderController providers = new ProviderController();
			providers.open();

			boolean validNumber = false;
			do
			{
				//prompt user for provider number
				long number = ui.promptForLong("Provider Number: ");
				//search for provider in collection
				theProvider = providers.find(number);
				if (theProvider == null)
					ui.errorMessage("Invalid provider number.  Please re-enter.");
				else validNumber = true;
			}while (!validNumber);

			providers.close();
		}
		catch (FileNotFoundException ex)
		{
			//occurs if the file cannot be created
			ui.errorMessage(ex.getMessage());
		}

	}

	//Verify Member and Submit Visit use cases
	private void verifyMember()
	{
		try
		{
			MemberController members = new MemberController();
			members.open();
			boolean canContinue = false;

			//Prompt the provider for a member number.
			long number = ui.promptForLong("Member Number: ");
			theMember = members.find(number);

			//Display the status of the member.
			if (theMember == null)
				ui.message("Invalid member number.");
			else if (theMember.getStatus() == 'A')
			{
				ui.message("Active Member\n");
				canContinue = true;
			}
			else if (theMember.getStatus() == 'S')
				ui.message("Suspended Member\n");
			else ui.errorMessage("Invalid status: " + theMember.getStatus());

			if (canContinue)  //Can continue only if member is active
			{
				//Submit Visit
				String answer = ui.promptForString
						("Continue to submit visit? (Y)es or (N)o: ");
				if (answer != null && answer.length() >= 1)        
					if (Character.toUpperCase(answer.charAt(0)) == 'Y')
					{
						//Create a new VisitSubmitter control object
						new VisitSubmitter(theProvider, theMember);           
					}
			}

			members.close();
		}
		catch (FileNotFoundException ex)
		{
			//occurs if the file cannot be created
			ui.errorMessage(ex.getMessage());
		}
	}

	private void receiveDirectory()
	{
		try
		{
			//generate the report
			ServiceReportGenerator generator = new ServiceReportGenerator();
			ProviderDirectory theReport = generator.getReport();

			//Send directory by email to the provider       
			theReport.sendByEmail(theProvider.getName() + " Provider Directory");

			//Display success confirmation on provider terminal
			ui.message("Your directory has been sent to you by email");
		}
		catch (FileNotFoundException ex)
		{
			//occurs if the file cannot be created
			ui.errorMessage(ex.getMessage());
		}

	}

	private Provider theProvider;
	private Member theMember;
	private UserInterface ui;

	//Starts the provider subsystem independently of the other subsystems

	public static void main(String[] args)
	{
		try
		{
			new ProviderInterface();
		}
		catch (NoSuchElementException ex)
		{
			UserInterface ui = new UserInterface();
			ui.message("\nEnd of test run.\n");
		}
	}
}