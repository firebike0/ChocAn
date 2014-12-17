package mdcs.csc440.chocan.Beans;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

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
		if (aCode == null || aCode.length() == 0)
			throw new IllegalArgumentException("A service code is required");
		else if (aCode.length() > CODE_LENGTH)
			throw new IllegalArgumentException
			("The service code may not be more than "
					+ CODE_LENGTH + " digits");
		else
		{
			//check that each character is a digit
			for (int i = 0; i < aCode.length(); i++)
				if (! Character.isDigit(aCode.charAt(i)))
					throw new IllegalArgumentException
					("The service code may contain digits only");
		}
		code = aCode;
	}

	public void setName (String aName)
	{
		if (aName == null || aName.length() == 0)
			throw new IllegalArgumentException("A service name is required");
		else if (aName.length() > NAME_LENGTH)
			throw new IllegalArgumentException
			("The service name may not be more than " 
					+ NAME_LENGTH + " characters");
		name = aName;
	}

	public void setFee(double aFee)
	{
		if (aFee < 0 || aFee >= MAX_FEE)
			throw new IllegalArgumentException
			("The fee must be between $0 and " + formatter.format(MAX_FEE));
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

	private String code;
	private String name;
	private double fee;

	public static final int CODE_LENGTH = 6;

	public static final int NAME_LENGTH = 20;

	public static final double MAX_FEE = 1000;

	private static final char SEPARATOR = '#';
	private static NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

}//class Service
