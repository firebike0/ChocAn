package mdcs.csc440.chocan.Reports;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Formatter;
import java.io.FileNotFoundException;
import java.util.Locale;

import mdcs.csc440.chocan.UI.UserInterface;

/*  This class models a report that can have headings and details added.
 *  The report can be displayed in a user interface, printed or sent by email. 
 *  This implementation uses a StringBuilder for the text of the report.
 */
public abstract class Report
{

	public Report ()
	{
		// Send all output to the StringBuilder
		sb = new StringBuilder();     
		formatter = new Formatter(sb);

		addHeading("Chocoholics Anonymous");
	}


	// Adds a heading to the report
	public void addHeading(String heading)
	{
		//center  headings, assume 80 characters per line
		String firstHalf = heading.substring(0, heading.length()/2);
		String secondHalf = heading.substring(heading.length()/2);
		formatter.format("%40s", firstHalf);
		formatter.format("%-40s%n%n", secondHalf);   
	}

	// Adds an error message to the report if necessary
	public void addErrorMessage(String message)
	{
		formatter.format("%n*****Error: %s*****%n", message);
	}

	/*  Simulates sending a report by email.  The report is saved to a
	 *  text file named by the "email address" given plus the extension .txt.
	 */
	public void sendByEmail(String emailAddress) throws FileNotFoundException
	{
		PrintWriter out = null;
		fileName = emailAddress + ".txt";
		out = new PrintWriter(fileName);
		out.println(sb);
		out.close();
	}

	public void display(UserInterface ui)
	{
		ui.message(sb.toString());     
	}

	// Simulates printing the report.
	public void print(String printerName) throws FileNotFoundException
	{
		sendByEmail(printerName);
	}

	/* Returns the name of the file to which this report was last saved.
	 *  If called before print or sendByEmail is called, null is returned.
	 */
	public String getFileName()
	{
		return fileName;
	}

	protected static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US); 

	protected Formatter formatter;
	private StringBuilder sb;
	private String fileName;

}
