package mdcs.csc440.chocan.UI;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.NumberFormat;
import java.util.Locale;

/** The UserInterface class encapsulates all the user interaction 
 *  with the system.
 *  This implementation uses the console for input and output.
 *  @author Jean Naude
 *  @version 1.0 March 2009
 */
public class UserInterface
{

	/**
	 * Creates a new UserInterface
	 */
	public UserInterface()
	{
		in = new Scanner(System.in);
	}//default constructor

	/** Displays the menu text given, prompts the user for his choice
	 *  and returns the choice.
	 *  @param menuText the menu text to display
	 *  @return the user's choice
	 */
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
			//End of input, either of a test case using an input file
			//or the user presses Ctrl + Z (Windows)
			//or Ctrl + D (Unix) to exit the system.
			throw ex;
		}
		catch (Exception ex)       //all other exceptions
		{
			errorMessage(ex.getMessage());
			return 0;
		}
	}//menu

	/** Displays the message as an error message
	 *  @param msg the message to display
	 */
	public void errorMessage(String msg)
	{
		System.out.println("\n\n***Error: " + msg + "\n");
		//Make sure the user takes notice.  Similar to modal
		//error dialog in a GUI
		System.out.print("Press enter to continue ...");
		in.nextLine();
	}//errorMessage

	/** Displays the message as a standard message on a new line.
	 *  @param msg the message to display
	 */
	public void message(String msg)
	{
		System.out.print("\n" + msg);
	}//message

	/** Displays a prompt to the user for a string 
	 *  and returns the user's input
	 *  @param prompt the prompt
	 *  @return the user input 
	 */
	public String promptForString(String prompt)
	{
		message(prompt);
		String input = in.nextLine();
		return input;
	}//promptForString

	/** Diplays a prompt to the user for a long integer 
	 *  and returns the user's input
	 *  @param prompt the prompt
	 *  @return the user input 
	 */
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
	}//getLong

	/** Diplays a prompt to the user for a date and returns the user's input
	 *  @param prompt the prompt
	 *  @return the user input 
	 */
	public Date promptForDate(String prompt)
	{
		try
		{
			message(prompt);  //display prompt
			String dateString = in.nextLine();  //read input
			dateFormatter.applyPattern(DATE_FORMAT); 
			return dateFormatter.parse(dateString);  //convert to a date
		}
		catch (ParseException ex)
		{
			errorMessage("Invalid date. Please follow format given.");
			return promptForDate(prompt); //give the user another chance
		}

	}//promptForDate

	/** Displays a prompt to the user for a double value and 
	 *  returns the user's input
	 *  @param prompt the prompt
	 *  @return the user input
	 */
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
	}//promptForDouble

	/** Displays a prompt to the user for a double value, and returns the 
	 *  user's input. If the user gives no value, the default value is returned.
	 *  @param prompt the prompt
	 *  @param defaultValue the defaultValue
	 *  @return the user input or the defaultValue
	 */
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
	}//promptForDouble

	/** Returns the value given formatted as currency
	 *  @param value the value to be formatted
	 *  @return the value as a currency string
	 */
	public String formatAsCurrency(double value)
	{
		return currencyFormatter.format(value);
	}//formatAsCurrency


	//******************instance variable	
	private Scanner in;

	//******************class variables
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat();
	private static NumberFormat currencyFormatter 
	= NumberFormat.getCurrencyInstance(Locale.US);

	/** Required format for input of dates
	 */
	public static final String DATE_FORMAT = "MM-dd-yyyy";


}//class UserInterface
