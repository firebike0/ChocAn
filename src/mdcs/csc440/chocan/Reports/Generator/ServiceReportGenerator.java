package mdcs.csc440.chocan.Reports.Generator;

import java.util.ArrayList;
import java.text.ParseException;
import java.io.FileNotFoundException;

import mdcs.csc440.chocan.Beans.ProviderDirectory;
import mdcs.csc440.chocan.Beans.Service;
import mdcs.csc440.chocan.Beans.Controller.ServiceController;

//Control class that handles the Request Provider Directory

public class ServiceReportGenerator
{

	public ServiceReportGenerator()
	{
		try
		{
			ServiceController services = new ServiceController();
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
	}
	
	public ProviderDirectory getReport()
	{
		return report;
	}
	
	private ProviderDirectory report;

}

