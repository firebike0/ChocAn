package mdcs.csc440.chocan.Beans;

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
		if (aName == null || aName.length() == 0)
			throw new IllegalArgumentException("A name is required");
		else if (aName.length() > NAME_LENGTH)
			throw new IllegalArgumentException("Name may not be more than " 
					+ NAME_LENGTH + " characters");
		name = aName;
	}	

	public void setStreet(String aStreet)
	{
		if (aStreet == null) aStreet = "";	//Replace null with an empty string
		else if (aStreet.length() > STREET_LENGTH)
			throw new IllegalArgumentException("Street may not be more than " 
					+ STREET_LENGTH + " characters");
		street = aStreet;
	}

	public void setCity(String aCity)
	{
		if (aCity == null) aCity = "";		//Replace null with an empty string
		else if (aCity.length() > CITY_LENGTH)
			throw new IllegalArgumentException("City may not be more than " 
					+ CITY_LENGTH + " characters");
		city = aCity;
	}

	public void setState(String aState)
	{
		if (aState == null) aState = "";	//Replace null with an empty string
		else if (aState.length() > 0)
		{
			if (aState.length() != STATE_LENGTH) 
				throw new IllegalArgumentException("State must be exactly "
						+ STATE_LENGTH + " letters");
			//test whether each character is a letter
			for (int i = 0; i < STATE_LENGTH; i++)
				if(! Character.isLetter(aState.charAt(i)))
					throw new IllegalArgumentException("State must be " 
							+ STATE_LENGTH + " letters only");
		}		

		state = aState;
	}

	public void setZip(String aZip)
	{
		if (aZip == null) aZip = "";         //Replace null with an empty string
		else if (aZip.length() > 0)          //if given, zip must be 5 digits
		{
			//test for correct length
			if (aZip.length() != ZIP_LENGTH) 
				throw new IllegalArgumentException("Zip code must be exactly " 
						+ ZIP_LENGTH + " digits");

			//test whether each character is a digit
			for (int i = 0; i < ZIP_LENGTH; i++)
				if (!Character.isDigit(aZip.charAt(i)))
					throw new IllegalArgumentException("Zip code must be " 
							+ ZIP_LENGTH + " digits only");
		}		

		zip = aZip;
	}

	// Changes the person's email address.  No validation is done.
	public void setEmail (String anEmail)
	{
		if (anEmail == null) anEmail = "";
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

	public static final int NAME_LENGTH = 25;

	public static final int STREET_LENGTH = 25;

	public static final int CITY_LENGTH = 14;

	public static final int STATE_LENGTH = 2;

	public static final int ZIP_LENGTH = 5;

}