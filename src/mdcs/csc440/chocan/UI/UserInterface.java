package mdcs.csc440.chocan.UI;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.NumberFormat;
import java.util.Locale;

import mdcs.csc440.chocan.Beans.Controller.Validator;

//UserInterface class holds all of the user data within the system.

public class UserInterface
{

	public UserInterface()
	{
		in = new Scanner(System.in);
	}//default constructor

	// Displays the menu text given, prompts the user for his choice and returns the choice.

	public int menu(String menuText)	
	{
		try
		{

			message("");
			message(menuText);  //display menu text
			message("  Choice: ");
			String input = in.nextLine();
			int choice = Integer.parseInt(input); //read choice as an integer
			return choice;
		}
		catch (NumberFormatException ex)        //not a valid integer
		{
			errorMessage("Please enter digits only.");
			return menu(menuText);              //give the user another chance
		}
		catch (NoSuchElementException ex)
		{
			throw ex;
		}
		catch (Exception ex)       //all other exceptions
		{
			errorMessage(ex.getMessage());
			return 0;
		}
	}

	public void errorMessage(String msg)
	{
		System.out.println("\n\n***Error: " + msg + "\n");
		System.out.print("Press enter to continue ...");
		in.nextLine();
	}

	// Displays the message as a standard message on a new line.

	public void message(String msg)
	{
		System.out.print("\n" + msg);
	}

	public String promptForString(String prompt)
	{
		message(prompt);
		String input = in.nextLine();
		return input;
	}

	public long promptForLong(String prompt)
	{
		try
		{
			message(prompt);    //display prompt
			String input = in.nextLine();
			long number = Integer.valueOf(input).longValue();//convert to long
			return number;
		}
		catch (NumberFormatException ex) //not a valid long
		{
			errorMessage("Please enter digits only.");
			return promptForLong(prompt);  //give the user another chance
		}
	}

	// Displays a prompt to the user for a date and returns the user's input
	public Date promptForDate(String prompt)
	{
		try
		{
			message(prompt);  //display prompt
			String dateString = in.nextLine();  //read input
			//validating date input
			while(!dateString.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")){
				message(prompt);  			 //display prompt
				dateString = in.nextLine();  //read input
			}
			dateFormatter.applyPattern(DATE_FORMAT); 
			return dateFormatter.parse(dateString);  //convert to a date
		}
		catch (ParseException ex)
		{
			errorMessage("Invalid date. Please follow format given.");
			return promptForDate(prompt); //give the user another chance
		}

	}

	public double promptForDouble(String prompt)
	{
		try
		{
			message(prompt);
			String input = in.nextLine();
			double value = Double.parseDouble(input); //convert to a double
			return value;
		}
		catch (NumberFormatException ex)  //not a valid double
		{
			errorMessage("Please enter digits and at most one decimal point");
			return promptForDouble(prompt);  //give the user another chance
		}
	}

	public double promptForDouble(String prompt, double defaultValue)
	{
		try
		{
			message(prompt);
			String input = in.nextLine();
			//test for no value
			if (input != null && input.length() > 0)
			{
				double value = Double.parseDouble(input); //convert to double
				return value;
			}
			else return defaultValue;  //no value given -- return default value
		}
		catch (NumberFormatException ex)  
		{
			errorMessage("Please enter digits and at most one decimal point");
			return promptForDouble(prompt);  //give the user another chance
		}
	}

	// Returns the value given formatted as currency
	public String formatAsCurrency(double value)
	{
		return currencyFormatter.format(value);
	}

	
	private Scanner in;

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat();
	private static NumberFormat currencyFormatter 
	= NumberFormat.getCurrencyInstance(Locale.US);

	public static final String DATE_FORMAT = "MM-dd-yyyy";


}
