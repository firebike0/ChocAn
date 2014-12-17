package mdcs.csc440.chocan.UI;

import java.util.Date;
import java.text.ParseException;
import java.io.FileNotFoundException;

import mdcs.csc440.chocan.Beans.Visit;
import mdcs.csc440.chocan.Beans.Member;
import mdcs.csc440.chocan.Beans.Provider;
import mdcs.csc440.chocan.Beans.Service;
import mdcs.csc440.chocan.Beans.Controller.Visits;
import mdcs.csc440.chocan.Beans.Controller.Services;

//The VisitSubmitter control class provides information for Submit Visit , after the Verify Member use case has completed.

public class VisitSubmitter
{

	public VisitSubmitter(Provider theProvider, Member theMember)
	{
		try
		{
			ui = new UserInterface();

			services = new Services();
			visits = new Visits();
			services.open();
			visits.open();

			//get the service date
			Date serviceDate = ui.promptForDate
					("Service Date (" + UserInterface.DATE_FORMAT + "): ");

			//get the correct service
			Service theService = null;
			boolean correctCode = false;
			do
			{
				//get the service code
				String serviceCode = ui.promptForString("Service Code: ");
				theService = services.find(serviceCode);
				if (theService == null)
					ui.errorMessage("Invalid code.  Please re-enter.");
				else
				{
					//confirm the service
					String answer = ui.promptForString("Service: " 
							+ theService.getName()
							+ "  \nIs this correct? (Y)es or (N)o: ");
					if (answer != null && answer.length() >= 1 &&			
							Character.toUpperCase(answer.charAt(0)) == 'Y')
						correctCode = true;
				}
			} while (!correctCode);


			//The constructor initializes the date and time with the system time.
			Visit aVisit = new Visit(theService.getCode(),
					theProvider.getNumber(), theMember.getNumber(),
					serviceDate);
			visits.add(aVisit);
			//Display success confirmation and service fee
			ui.message("Your claim has been submitted successfully.");
			ui.message("Service fee due to you: " 
					+ ui.formatAsCurrency(theService.getFee()));

			services.close();
			visits.close();
		}
		catch (ParseException ex)
		{
			//File format is incorrect
			ui.errorMessage(ex.getMessage());
		}
		catch (IllegalArgumentException ex)
		{
			//This should only happen if the comments entered are too long.
			ui.errorMessage(ex.getMessage());
		}
		catch (FileNotFoundException ex)
		{
			//occurs if the file cannot be created
			ui.errorMessage(ex.getMessage());
		}		

	}
	
	private Services services;
	private Visits visits;

	private UserInterface ui;
}