package mdcs.csc440.chocan.UI;

import java.util.NoSuchElementException;

import mdcs.csc440.chocan.UI.Maintainer.MemberMaintainer;
import mdcs.csc440.chocan.UI.Maintainer.ProviderMaintainer;
import mdcs.csc440.chocan.UI.Maintainer.ServiceMaintainer;

//Simulates the Maintenance Subsystem.
public class MaintenanceInterface
{

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
			choice = ui.menu(menuText);  
			switch(choice)
			{
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
	}
}