package mdcs.csc440.chocan.Reports.Generator;

import java.util.ArrayList;
import java.text.ParseException;
import java.io.FileNotFoundException;

import mdcs.csc440.chocan.Beans.ProviderDirectory;
import mdcs.csc440.chocan.Beans.Service;
import mdcs.csc440.chocan.Beans.Controller.Services;

/** Control class that co-ordinates use case Request Provider Directory
 *  @author Jean Naude
 *  @version 1.0 March 2009
 */
public class ServiceReportGenerator
{
	/** Creates a new service report generator object which creates a new
	 *  provider directory (service report).
	 */
	public ServiceReportGenerator()
	{
		try
		{
			Services services = new Services();
			services.open();

			//Create a new provider directory
			report = new ProviderDirectory();

			//Get all services
			ArrayList<Service> allServices = services.getAllOrderedByName();

			//Add all services to provider directory
			for (Service aService : allServices)
				report.addDetail(aService.getName(), aService.getCode(),
						aService.getFee());

			services.close();

		}
		catch (FileNotFoundException ex)
		{
			//occurs if the file cannot be created
			report.addErrorMessage(ex.getMessage());
		}
		catch (ParseException ex)
		{
			//occurs if the file format is incorrect
			report.addErrorMessage(ex.getMessage());
		}
	}//default constructor

	/** Returns the report
	 *  @return the report
	 */
	public ProviderDirectory getReport()
	{
		return report;
	}//getReport

	//********************instance variable
	private ProviderDirectory report;

}//class ServiceReportGenerator
