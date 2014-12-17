package mdcs.csc440.chocan.UI;

import java.util.NoSuchElementException;

//This class allows for testing the ChocAn software product

public class ChocAnSystem
{

	public ChocAnSystem()
	{
		UserInterface ui = new UserInterface();

		String menuText = "1.\tProvider Subsystem\n" +
				"2.\tMaintenance Subsystem\n" +
				"3.\tManager Subsystem\n" +
				"4.\tAccounting Procedure\n" +
				"5.\tQuit\n";

		int choice;
		try
		{

			do
			{
				ui.message("\t\t\tChocoholics Anonymous");

				//display the menu and read the choice
				choice = ui.menu(menuText); 
				switch(choice)
				{
				//Start subsystem chosen
				case 1: new ProviderInterface(); break;
				case 2: new MaintenanceInterface(); break;
				case 3: new ManagerInterface(); break;
				case 4: new SchedulerInterface(); break;
				case 5: break;
				default: ui.errorMessage("Invalid choice.  Please re-enter.");
				}
			}while (choice != 5);
		} 
		catch (NoSuchElementException ex)
		{
			ui.message("\nEnd of test run.\n");
		}
	}


	// Starts the testing interface
	public static void main(String [] args)
	{
		new ChocAnSystem();
	}

}
