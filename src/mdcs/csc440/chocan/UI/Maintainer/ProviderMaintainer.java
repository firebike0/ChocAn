package mdcs.csc440.chocan.UI.Maintainer;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import mdcs.csc440.chocan.Beans.Provider;
import mdcs.csc440.chocan.Beans.Controller.Providers;
import mdcs.csc440.chocan.UI.UserInterface;

/** Control class to co-ordinate the use case Maintain Provider.  A Provider can
 *  be added, updated or deleted.
 *  @author Jean Naude 
 *  @version 1.0 March 2009
 */
public class ProviderMaintainer extends PersonMaintainer
{

	/**
	 * Creates a new ProviderMaintainer control object
	 */
	public ProviderMaintainer()
	{
		try
		{
			//create and open the provider collection
			providers = new Providers();
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
				ui.message("\t\t\tMaintain Providers\n\n");
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

			//close provider collection
			providers.close();
		}
		catch (FileNotFoundException ex)
		{
			//occurs if the file cannot be created
			ui.errorMessage(ex.getMessage());
		}

	}//default constructor

	// Allows the user to add a new provider to the collection
	private void addProvider()
	{
		ui.message("\tAdd a provider\n");
		//Create a new default provider
		Provider newProvider = new Provider();
		//get values for attributes
		//false for the second parameter means required attributes 
		//must be provided	
		updatePerson(ui, newProvider, false); 	
		//add to provider collection
		providers.add(newProvider);	
		//display provider
		ui.message("\nNew Provider details: \n" + newProvider.toFormattedString()
				+ "\n\n");
	}//addProvider

	// Allows the user to update an existing provider's details
	private void editProvider()
	{
		ui.message("\tEdit a provider\n\n");
		//get provider number
		long number = ui.promptForLong("Provider number: ");

		//search for provider in collection
		Provider aProvider = providers.find(number);
		if (aProvider != null)
		{
			//display provider
			ui.message("\nCurrent Provider details: \n" 
					+ aProvider.toFormattedString());

			//get updated values for attributes
			//true means attributes not provided
			//will retain their original values	
			updatePerson(ui, aProvider, true);  

			//update provider in collection
			providers.update(aProvider);

			//display updated provider
			ui.message("\nUpdated Provider details: \n" 
					+ aProvider.toFormattedString()+ "\n\n");
		}
		else ui.errorMessage("Provider number " + number 
				+ " cannot be found.\n");
	}//editProvider

	//Allows the user to delete a provider from the collection
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

	}//deleteProvider

	//**************instance variables
	private UserInterface ui;	   //an instance of a user interface
	private Providers providers;  //a collection of provider objects

	//********************************************************************
	/**
	 * Runs the ProviderMaintainer independently of the rest of the system.
	 * @param args not used
	 */
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
	}//main	

}//class ProviderMaintainer