package mdcs.csc440.chocan.UI;

import java.util.NoSuchElementException;

/** This class provides a testing interface for the ChocAn software product.
 *  The main method starts the product and allows the tester to simulate the 
 *  provider subsystem, the data maintenance subsystem, the reporting subsystem
 *  and the accounting procedure.
 *  @author Jean Naude
 *  @version 1.0 March 2009
 */
public class ChocAnSystem
{

	/**
	 * Creates a new ChocAnSystem.
	 */
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
	}//default constructor


	/** Starts the testing interface
	 */
	public static void main(String [] args)
	{
		new ChocAnSystem();
	}//main	

}//class ChocAnSystem
