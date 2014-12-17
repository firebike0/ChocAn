package mdcs.csc440.chocan.UI;

import java.util.NoSuchElementException;

import mdcs.csc440.chocan.UI.Maintainer.MemberMaintainer;
import mdcs.csc440.chocan.UI.Maintainer.ProviderMaintainer;
import mdcs.csc440.chocan.UI.Maintainer.ServiceMaintainer;

/** Simulates the operator interface (Maintenance Subsystem) 
 *  of the ChocAn system.
 *  @author Jean Naude 
 *  @version 1.0 March 2009
 */
public class MaintenanceInterface
{

	/**
	 * Creates a new Operator Interface
	 */
	public MaintenanceInterface()
	{
		UserInterface ui = new UserInterface();
		String menuText = "1.\tMaintain Provider\n"
				+ "2.\tMaintain Member\n" 
				+ "3.\tMaintain Service\n" 
				+ "4.\tQuit\n";

		int choice;
		do	
		{		 
			ui.message("\t\t\tMaintenance Subsystem");
			//displays menu and read choice
			choice = ui.menu(menuText);  
			switch(choice)
			{
			//Create the control object required for selected use case
			//Use case Maintain Provider
			case 1: new ProviderMaintainer(); break;
			//Use case Maintain Member
			case 2: new MemberMaintainer(); break;
			//Use case Maintain Service
			case 3: new ServiceMaintainer(); break;
			//Quit
			case 4: break;
			default: ui.errorMessage("Invalid choice.  Please re-enter.");
			}
		}while (choice != 4);
	}// default Constructor

	//***********************************************************************
	/**
	 * Starts the maintenance subsystem independently of the other subsystems
	 * @param args not used
	 */
	public static void main(String[] args)
	{
		try
		{
			new MaintenanceInterface();
		}
		catch (NoSuchElementException ex)
		{
			UserInterface ui = new UserInterface();
			ui.message("\nEnd of test run.\n");
		}
	}//main

}//class OperatorInterface
