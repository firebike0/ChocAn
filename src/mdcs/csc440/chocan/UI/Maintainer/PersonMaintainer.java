package mdcs.csc440.chocan.UI.Maintainer;

import mdcs.csc440.chocan.Beans.Person;
import mdcs.csc440.chocan.UI.UserInterface;

//Superclass for ProviderMaintainer and MemberMaintainer
public abstract class PersonMaintainer
{
	public PersonMaintainer()
	{
	}

	// Prompts the user for the new values with which to update a person.
	
	public void updatePerson(UserInterface ui, Person aPerson
			, boolean retainOldValue)
	{
		try
		{
			if (retainOldValue) 
				ui.message("\nEnter new values.  "
						+ "Press Enter for values that are correct.");
			String input = ui.promptForString("\nName: ");
			if (input.length() == 0 && retainOldValue)
				aPerson.setName(aPerson.getName());     //ensure valid name
			else aPerson.setName(input);
			input = ui.promptForString("Street Address: ");
			if (input.length() == 0 && retainOldValue); //retain old value
			else aPerson.setStreet(input);
			input = ui.promptForString("City: ");
			if (input.length() == 0 && retainOldValue); //retain old value
			else aPerson.setCity(input);
			input = ui.promptForString("State: ");
			if (input.length() == 0 && retainOldValue); //retain old value
			else aPerson.setState(input);
			input = ui.promptForString("Zip Code: ");
			if (input.length() == 0 && retainOldValue); //retain old value
			else aPerson.setZip(input);
			input = ui.promptForString("Email: ");
			if (input.length() == 0 && retainOldValue); //retain old value
			else aPerson.setEmail(input);
		}
		catch (IllegalArgumentException ex)
		{
			ui.errorMessage(ex.getMessage());
			ui.message("Current details:\n");
			ui.message(aPerson.toFormattedString());
			ui.message("\nPlease repeat input.\n");
			updatePerson(ui, aPerson, true);    //Give the user another chance to enter in details
		}
	}

}
