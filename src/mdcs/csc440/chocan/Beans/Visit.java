package mdcs.csc440.chocan.Beans;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

// Entity class that models a visit
public class Visit 
{

	// Creates a new visit, and sets the submission date and time to the current system date and time
	public Visit() 
	{
		submissionDate = new Date();
	}

	// Creates a new visit with the given values
	public Visit(String aServiceCode, long aProviderNumber, long aMemberNumber, Date aServiceDate)
	{
		this(); //call default constructor
		setServiceCode(aServiceCode);
		setProviderNumber(aProviderNumber);
		setMemberNumber(aMemberNumber);
		setServiceDate(aServiceDate);
	}

	// Creates a visit from the string
	public Visit (String data) throws ParseException
	{
		fromString(data);
	}

	public Date getSubmissionDate()
	{
		return submissionDate;
	}
	
	public String getServiceCode()
	{
		return serviceCode;
	}
	
	public long getProviderNumber()
	{
		return providerNumber;
	}
	
	public long getMemberNumber()
	{
		return memberNumber;
	}
	
	public Date getServiceDate()
	{
		return serviceDate;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setServiceCode(String aCode)
	{
		if (aCode == null || aCode.length() == 0)
			throw new IllegalArgumentException("A service code is required");
		else if (aCode.length() > CODE_LENGTH)
			throw new IllegalArgumentException("Service code may not be more than " 
					+ CODE_LENGTH + " digits");
		else
			for (int i = 0; i < aCode.length(); i++)
				if (!Character.isDigit(aCode.charAt(i)))
					throw new IllegalArgumentException("Service code must consist " 
							+ "of digits only");
		serviceCode = aCode;
	}
	
	public void setProviderNumber(long aNumber)
	{
		if (aNumber < 0)
			throw new IllegalArgumentException("Invalid provider number");
		providerNumber = aNumber;
	}
	
	public void setMemberNumber(long aNumber)
	{
		if (aNumber < 0)
			throw new IllegalArgumentException("Invalid member number");
		memberNumber = aNumber;
	}
	
	public void setServiceDate(Date aDate)
	{
		if (aDate == null)
			throw new IllegalArgumentException("The service date is required");
		else serviceDate = aDate;
	}
	
	public void setComments(String aComment) {
		//no check is done if comments exist or not as it is optional
		comments = aComment;
	}
	
	public String toString()
	{
		dateFormatter.applyPattern(DATE_TIME_FORMAT);
		String submissionDateString = dateFormatter.format(submissionDate);
		dateFormatter.applyPattern(DATE_FORMAT);
		String serviceDateString = dateFormatter.format(serviceDate);
		return submissionDateString + SEPARATOR
				+ serviceCode + SEPARATOR
				+ providerNumber + SEPARATOR
				+ memberNumber + SEPARATOR
				+ serviceDateString + SEPARATOR
				+ comments;
	}

	// Changes all the variables to the values given by the string 
	private void fromString(String data) throws ParseException 
	{
		String [] fields = data.split("" + SEPARATOR);
		System.out.println(fields.length);
		dateFormatter.applyPattern(DATE_TIME_FORMAT);
		submissionDate = dateFormatter.parse(fields[0]);
		setServiceCode(fields[1]);
		setProviderNumber(Integer.parseInt(fields[2]));
		setMemberNumber(Integer.parseInt(fields[3]));
		dateFormatter.applyPattern(DATE_FORMAT);
		setServiceDate(dateFormatter.parse(fields[4]));
		setComments(fields[5]);
	}
	
	private Date submissionDate;
	private String serviceCode;
	private long providerNumber;
	private long memberNumber;
	private Date serviceDate;	
	private String comments;

	public static final int CODE_LENGTH = 6;

	private static final String DATE_FORMAT = "MM-dd-yyyy";
	private static final String DATE_TIME_FORMAT = "MM-dd-yyyy HH-mm-ss";	
	private static final char SEPARATOR = '#';
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat();

}