package mdcs.csc440.chocan.UI.Maintainer;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import mdcs.csc440.chocan.Beans.Provider;
import mdcs.csc440.chocan.Beans.Controller.ProviderController;
import mdcs.csc440.chocan.UI.UserInterface;

//Control class to co-ordinate the use case Maintain Provider.  A Provider can be added, updated or deleted.

public class ProviderMaintainer extends PersonMaintainer
{


	 //Creates a new ProviderMaintainer control object

	public ProviderMaintainer()
	{
		try
		{
			providers = new ProviderController();
			providers.open();

			//set up menu for user interface		
			ui = new UserInterface();					
			String menuText = "1.\tAdd a New Provider\n" +
					"2.\tEdit a Provider\n" +
					"3.\tDelete a Provider\n" +
					"4.\tQuit\n";

			int choice;
			do
			{
				ui.message("\t\t\tMaintain Providers");
				//display menu and read choice
				choice = ui.menu(menuText);  
				switch(choice)
				{
				case 1: addProvider(); break;
				case 2: editProvider(); break;
				case 3: deleteProvider(); break;
				case 4: break;
				default: ui.errorMessage("Invalid choice.  Please re-enter.");
				}
			}while (choice != 4);

			providers.close();
		}
		catch (FileNotFoundException ex)
		{
			//occurs if the file cannot be created
			ui.errorMessage(ex.getMessage());
		}

	}

	private void addProvider()
	{
		ui.message("\tAdd a provider\n");
		Provider newProvider = new Provider();
		//false for the second parameter means required attributes must be provided	
		updatePerson(ui, newProvider, false); 	
		//add to provider collection
		providers.add(newProvider);	
		//display provider
		ui.message("\nNew Provider details: \n" + newProvider.toFormattedString()
				+ "\n\n");
	}

	private void editProvider()
	{
		ui.message("\tEdit a provider\n\n");
		//get provider number
		long number = ui.promptForLong("Provider number: ");

		//search for provider in collection
		Provider aProvider = providers.find(number);
		if (aProvider != null)
		{
			ui.message("\nCurrent Provider details: \n" 
					+ aProvider.toFormattedString());

			//get updated values for attributes
			//true means attributes not provided
			//will retain their original values	
			updatePerson(ui, aProvider, true);  

			providers.update(aProvider);

			//display updated provider
			ui.message("\nUpdated Provider details: \n" 
					+ aProvider.toFormattedString()+ "\n\n");
		}
		else ui.errorMessage("Provider number " + number 
				+ " cannot be found.\n");
	}

	private void deleteProvider()
	{
		ui.message("\tDelete a provider\n\n");

		//get provider number
		long number = ui.promptForLong("Provider number: ");

		//search for provider in collection
		Provider aProvider = providers.find(number);
		if (aProvider != null)
		{
			//display provider and request confirmation of deletion
			ui.message("\nCurrent Provider details: \n" 
					+ aProvider.toFormattedString() + "\n");
			String answer = ui.promptForString
					("Are you sure you want to delete this provider? (Y)es or (N)o: ");
			if (answer != null && answer.length() >= 1)			
				if (Character.toUpperCase(answer.charAt(0)) == 'Y')
				{
					//delete provider from collection
					providers.delete(number);

					//display acknowledgment
					ui.message("\nThe provider has been deleted.\n\n");
				}
				else ui.message("The provider has not been deleted.\n");
			else ui.message("\nThe provider has not been deleted.\n\n");
		}
		else
			ui.errorMessage("Provider number " + number + " cannot be found.\n");

	}

	private UserInterface ui;	   
	private ProviderController providers;   

	 // Runs the ProviderMaintainer independently of the rest of the system.

	public static void main(String[] args)
	{
		try
		{
			new ProviderMaintainer();
		}
		catch (NoSuchElementException ex)
		{
			UserInterface ui = new UserInterface();
			ui.message("\nEnd of test run.\n");
		}
	}	

}