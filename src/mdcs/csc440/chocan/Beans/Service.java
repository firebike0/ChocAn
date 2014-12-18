package mdcs.csc440.chocan.Beans;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import mdcs.csc440.chocan.Beans.Controller.Validator;

//  Entity class that models a service
public class Service 
{

	public Service() 
	{		
	}

	// Creates a new service with the given values
	public Service(String aCode, String aName, double aFee)	
	{
		setCode(aCode);
		setName(aName);
		setFee(aFee);
	}

	// Creates a service from a string
	public Service (String data) throws ParseException
	{
		fromString(data);
	}

	public String getCode()
	{
		return code;
	}

	public String getName()
	{
		return name;
	}

	public double getFee()
	{
		return fee;
	}

	public void setCode(String aCode)
	{
		validator.validateServiceCode(aCode);
		code = aCode;
	}

	public void setName (String aName)
	{
		validator.validateServiceName(aName);
		name = aName;
	}

	public void setFee(double aFee)
	{
		validator.validateServiceFee(aFee);
		fee = aFee;
	}

	@Override
	// Returns a string representation of the service
	public String toString()
	{
		return code + SEPARATOR + name + SEPARATOR + formatter.format(fee);
	}

	// Changes all the variables to the values by the string
	public void fromString(String data) throws ParseException
	{
		String [] fields = data.split("" + SEPARATOR);
		setCode(fields[0]);
		setName(fields[1]);
		setFee(formatter.parse(fields[2]).doubleValue());
	}

	// String representation of the service that is suitable for text display.
	public String toFormattedString()
	{
		String serviceString = "Code:  " + code
				+ "\nName:  " + name
				+ "\nFee:   " + formatter.format(fee);
		return serviceString;
	}

	private Validator validator;
	private String code;
	private String name;
	private double fee;

	private static final char SEPARATOR = '#';
	private static NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

}//class Service
