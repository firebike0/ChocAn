package mdcs.csc440.chocan.Beans;

import mdcs.csc440.chocan.Reports.Report;

// Boundary class modeling a provider directory -- a list of services in alphabetical order according to the service name.
public class ProviderDirectory extends Report
{
	
	public ProviderDirectory()
	{
		String title = "Provider Directory";
		
		formatter.format("%31s %n%n", title);
		formatter.format("%-25s %-6s   %8s %n","Service Name", "Code", "Fee");
		formatter.format("%-25s %-6s   %8s %n","------------", "----", "---");
		
	}
	
	// Adds a line of detail about a service
	public void addDetail(String name, String code, double fee)
	{
		formatter.format("%-25s %-6s   %8s %n", name, code, 
							currencyFormatter.format(fee));
	}
			
}
