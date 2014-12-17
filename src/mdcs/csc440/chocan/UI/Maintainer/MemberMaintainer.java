package mdcs.csc440.chocan.UI.Maintainer;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import mdcs.csc440.chocan.Beans.Member;
import mdcs.csc440.chocan.Beans.Controller.MemberController;
import mdcs.csc440.chocan.UI.UserInterface;

//Control class to Maintain Member.  A Member can  be added, updated or deleted.


public class MemberMaintainer extends PersonMaintainer
{

	public MemberMaintainer()
	{
		try
		{
			//Create and open the member collection
			members = new MemberController();
			members.open();

			//Create a user interface and set up menu
			ui = new UserInterface();
			String menuText = "1.\tAdd a New Member\n" +
					"2.\tEdit a Member\n" +
					"3.\tDelete a Member\n" +
					"4.\tQuit\n";

			int choice;
			do
			{
				ui.message("\t\t\tMaintain Members");
				//display menu and get choice
				choice = ui.menu(menuText);  
				switch(choice)
				{
				case 1: addMember(); break;
				case 2: editMember(); break;
				case 3: deleteMember(); break;
				case 4: break;
				default: ui.errorMessage("Invalid choice.  Please re-enter.");
				}
			}while (choice != 4);

			//close the member collection 
			members.close();
		}
		catch (FileNotFoundException ex)
		{
			//occurs if the file cannot be created
			ui.errorMessage(ex.getMessage());
		}

	}

	private void addMember()
	{
		ui.message("\tAdd a Member");
		Member newMember = new Member();
		//false for last parameter means required attributes must be provided
		updatePerson(ui, newMember, false); 
		members.add(newMember);	
		//Display the member
		ui.message("New member details: \n");
		ui.message(newMember.toFormattedString() + "\n");
	}

	private void editMember()
	{
		ui.message("\tEdit a Member");
		//Get member number
		long number = ui.promptForLong("Member number: ");
		//Search for member
		Member aMember = members.find(number);
		if (aMember != null)
		{
			//Display member
			ui.message("Current Member Details: \n" + aMember.toFormattedString());
			//get updated values for attributes
			//true means attributes not provided will retain their original values		
			updatePerson(ui, aMember, true);  
			boolean validStatus = false;
			do
			{	
				String input = ui.promptForString("Member status: ");		
				if (input != null && input.length() > 0)
				{
					try
					{
						aMember.setStatus(Character.toUpperCase(input.charAt(0)));
						validStatus = true;
					}
					catch (IllegalArgumentException ex)
					{
						ui.errorMessage(ex.getMessage());
					}
				}
				else validStatus = true;
			} while (!validStatus);

			members.update(aMember);

			//display updated details as confirmation of success
			ui.message("Updated Member details:\n");
			ui.message(aMember.toFormattedString() + "\n");
		}
		else ui.errorMessage("Member number " + number + " cannot be found.\n");
	}

	private void deleteMember()
	{
		ui.message("\tDelete a Member");

		//get member number
		long number = ui.promptForLong("Member number: ");

		//search for member in collection
		Member aMember = members.find(number);

		if (aMember != null)
		{
			//display member
			ui.message("Current Member Details: \n" 
					+ aMember.toFormattedString() + "\n" );

			//confirm deletion
			String answer = ui.promptForString
					("Are you sure you want to delete this member? (Y)es or (N)o: ");
			if (answer != null && answer.length() >= 1)			
				if (Character.toUpperCase(answer.charAt(0)) == 'Y')
				{
					//delete member from collection
					members.delete(number);

					//display acknowledgment
					ui.message("The member has been deleted.\n");
				}
				else ui.message("The member has not been deleted.\n");
			else ui.message("The member has not been deleted.\n");
		}
		else
			ui.errorMessage("Member number " + number + " cannot be found.\n");			

	}

	private UserInterface ui;
	private MemberController members;

	// Runs the MemberMaintainer independently of the rest of the system.

	public static void main(String[] args)
	{
		try
		{
			new MemberMaintainer();
		}
		catch (NoSuchElementException ex)
		{
			UserInterface ui = new UserInterface();
			ui.message("\nEnd of test run.\n");
		}
	}

}
