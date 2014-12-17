package mdcs.csc440.chocan.Reports;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Formatter;
import java.io.FileNotFoundException;
import java.util.Locale;

import mdcs.csc440.chocan.UI.UserInterface;

/** This class models a report that can have headings and details added.
 *  The report can be displayed in a user interface, printed or sent by email. 
 *  This implementation uses a StringBuilder for the text of the report.
 *  @author Jean Naude
 *  @version 1.0 March 2009
 */

public abstract class Report
{
	/** Creates a new report
	 */
	public Report ()
	{
		// Send all output to the Appendable object sb
		sb = new StringBuilder();     
		formatter = new Formatter(sb);

		// Add a heading
		addHeading("Chocoholics Anonymous");
	}//default constructor


	/** Adds a heading, centre justified, to the report
	 *  @param heading the heading to be added
	 */
	public void addHeading(String heading)
	{
		//centre justify headings, assume 80 characters per line
		String firstHalf = heading.substring(0, heading.length()/2);
		String secondHalf = heading.substring(heading.length()/2);
		formatter.format("%40s", firstHalf);
		formatter.format("%-40s%n%n", secondHalf);   
	}//addHeading

	/** Adds an error message to the report
	 *  @param message the message to be added
	 */
	public void addErrorMessage(String message)
	{
		formatter.format("%n*****Error: %s*****%n", message);
	}//addErrorMessage

	/** Simulates sending a report by email.  The report is saved to a
	 *  text file named by the "email address" given plus the extension .txt.
	 *  @param emailAddress the email address to which the report must be sent
	 *  @throws FileNotFoundException if the file cannot be created
	 */
	public void sendByEmail(String emailAddress) throws FileNotFoundException
	{
		PrintWriter out = null;
		fileName = emailAddress + ".txt";
		out = new PrintWriter(fileName);
		out.println(sb);
		out.close();
	}//sendByEmail

	/** Displays the report on the given user interface
	 *  @param ui the user interface
	 */
	public void display(UserInterface ui)
	{
		ui.message(sb.toString());     
	}//display

	/** Simulates printing the report.  The report is saved to a text file
	 *  named by the printer name given.
	 *  @param printerName the printer to which the report must be printed
	 *  @throws FileNotFoundException if the file cannot be created
	 */
	public void print(String printerName) throws FileNotFoundException
	{
		sendByEmail(printerName);
	}//print

	/** Returns the name of the file to which this report was last saved.
	 *  If called before print or sendByEmail is called, null is returned.
	 *  @return the file name last used to save the report
	 */
	public String getFileName()
	{
		return fileName;
	}//getFileName

	//*********************class variables

	/** Currency is displayed in dollars, according to specification
	 */
	protected static NumberFormat currencyFormatter 
	= NumberFormat.getCurrencyInstance(Locale.US); 

	//*********************instance variables
	/** The formatter that builds the report.
	 */
	protected Formatter formatter;
	private StringBuilder sb;
	private String fileName;

}//class Report
