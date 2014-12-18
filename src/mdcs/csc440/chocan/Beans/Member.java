package mdcs.csc440.chocan.Beans;

import mdcs.csc440.chocan.Beans.Controller.Validator;

//Entity class that models a member
public class Member extends Person
{

	public Member()
	{
		super();
		status = 'A';  //default status is Active
	}

	// Creates a member from a string representation of the member.
	public Member(String data)
	{
		super(data);		
	}

	// Returns the status of the member
	// A for active S for suspended
	public char getStatus()
	{
		return status;
	}

	public void setStatus(char newStatus)
	{
		if (MEMBER_STATUS_VALUES.indexOf(newStatus) < 0)
			throw new IllegalArgumentException(MEMBER_STATUS_HELP);
		status = newStatus;
	}

	@Override
	public String toString()
	{
		return super.toString() + SEPARATOR + status;
	}
	
	// Changes the instance variables to the values declared by the string
	public void fromString(String data)
	{
		super.fromString(data);
		setStatus(data.charAt(data.length()-1));
	}

	// String representation of the member that is suitable for text display.
	public String toFormattedString()
	{
		return super.toFormattedString() + "\nStatus:         " + status;
	}

	private char status;

	private static final String MEMBER_STATUS_VALUES = "AS";

	// Message giving the characters that are valid for the member status input
	public static final String MEMBER_STATUS_HELP = "Member status must be "
			+ "one of the following characters: A(ctive) or S(uspended)";

}