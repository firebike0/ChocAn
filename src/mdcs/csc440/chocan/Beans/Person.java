package mdcs.csc440.chocan.Beans;

import mdcs.csc440.chocan.Beans.Controller.Validator;

// Entity class that models persons that can be providers or members.
public abstract class Person
{	
	// Creates a default person using a unique number
	public Person()
	{
		//allocate the next number as this person's unique number
		number = nextNumber; 
		//increment next number ready for the next person created
		nextNumber++;  
		
		//set attributes to empty strings      
		street = "";
		city = "";
		state = "";
		zip = "";
		email = "";
	}

	// Creates a person object from a string representation
	public Person(String data)
	{
		fromString(data);
	}

	// Returns the person's unique number
	public long getNumber()
	{
		return number;
	}

	public String getName()
	{
		return name;
	}	

	public String getStreet()
	{
		return street;
	}

	public String getCity()
	{
		return city;
	}

	public String getState()
	{
		return state;
	}

	public String getZip()
	{
		return zip;
	}

	public String getEmail()
	{
		return email;
	}
	
	public void setName(String aName)
	{
		validator.validatePersonName(aName);
		name = aName;
	}	

	public void setStreet(String aStreet)
	{
		validator.validatePersonStreet(aStreet);
		street = aStreet;
	}

	public void setCity(String aCity)
	{
		validator.validatePersonCity(aCity);
		city = aCity;
	}

	public void setState(String aState)
	{
		validator.validatePersonState(aState);
		state = aState;
	}

	public void setZip(String aZip)
	{
		validator.validatePersonZip(aZip);
		zip = aZip;
	}

	// Changes the person's email address.  No validation is done.
	public void setEmail (String anEmail)
	{
		validator.validatePersonEmail(anEmail);
		email = anEmail;
	}
	
	@Override
	// Returns a string representation of the person
	public String toString()
	{
		return  "" + number + SEPARATOR + name + SEPARATOR  
				+ street + SEPARATOR + city + SEPARATOR + state + SEPARATOR 
				+ zip + SEPARATOR + email;
	}

	// Changes all the variables to the values given by the string given
	public void fromString(String data)
	{
		String [] fields = data.split("" + SEPARATOR);
		number = Integer.parseInt(fields[0]);
		setName(fields[1]);
		setStreet(fields[2]);
		setCity(fields[3]);
		setState(fields[4]);
		setZip(fields[5]);
		setEmail(fields[6]);
	}

	// String representation of the person that is suitable for text display.
	public String toFormattedString()
	{
		String personString   = "Number:         " + number
				+ "\nName:           " + name
				+ "\nStreet Address: " + street
				+ "\nCity:           " + city
				+ "\nState:          " + state
				+ "\nZip Code:       " + zip
				+ "\nEmail:          " + email;
		return personString;
	}

	// Returns the number that will be allocated to the next person created.
	public static long getNextNumber()
	{	
		return nextNumber;
	}

	public static void setNextNumber(long aNextNumber)
	{
		if (aNextNumber < nextNumber)
			throw new 
			IllegalArgumentException
			("The Person next number cannot be decreased");
		nextNumber = aNextNumber;
	}

	private Validator validator = new Validator();
	private long number;  // allow ability to hold 9 digits
	private String name;   
	private String street;   
	private String city ;  
	private String state;  
	private String zip;  
	private String email;  

	private static long  nextNumber;

	// The character used to separate fields
	protected static final char SEPARATOR = '#';

}