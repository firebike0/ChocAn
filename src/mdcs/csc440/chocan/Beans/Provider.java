package mdcs.csc440.chocan.Beans;

// Entity class that models a provider
public class Provider extends Person
{
	// Creates a default provider, allocating a unique number
	public Provider()
	{
		super();
	}

	// Creates a provider from a string
	public Provider(String data)
	{
		super(data);
	}

	@Override
	public String toString()
	{
		return super.toString();
	}

	// Changes the variables to the values given by the string
	public void fromString(String data)
	{
		super.fromString(data);
	}

	// Returns a string of the provider that is suitable for text display.
	public String toFormattedString()
	{
		return super.toFormattedString();
	}

}