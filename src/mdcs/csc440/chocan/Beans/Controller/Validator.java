package mdcs.csc440.chocan.Beans.Controller;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

// class takes care of all validation in the system.
public class Validator {
	
	
////////Person Validation
	public static final int PERSON_NAME_LENGTH = 25;
	public static final int STREET_LENGTH = 25;
	public static final int CITY_LENGTH = 14;
	public static final int STATE_LENGTH = 2;
	public static final int ZIP_LENGTH = 5;
	
	public void validatePersonName(String aName){
		if(aName == null || aName.length() == 0)
			throw new IllegalArgumentException("A name is required");
		else if (aName.length() > PERSON_NAME_LENGTH)
			throw new IllegalArgumentException("Name may not be more than "
					+ PERSON_NAME_LENGTH + " characters");
	}
	public void validatePersonStreet(String aStreet){
		if (aStreet == null) aStreet = "";	//Replace null with an empty string
		else if (aStreet.length() > STREET_LENGTH)
			throw new IllegalArgumentException("Street may not be more than " 
					+ STREET_LENGTH + " characters");
	}
	public void validatePersonCity(String aCity){
		if (aCity == null) aCity = "";		//Replace null with an empty string
		else if (aCity.length() > CITY_LENGTH)
			throw new IllegalArgumentException("City may not be more than " 
					+ CITY_LENGTH + " characters");
	}
	public void validatePersonState(String aState){
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
	}
	public void validatePersonZip(String aZip){
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
	}
	public void validatePersonEmail(String anEmail){
		//redo this validation*******************************
		if (anEmail == null) anEmail = "";
	}
	
	
////////Member Validation
	private static final String MEMBER_STATUS_VALUES = "AS";
	//Message giving the characters that are valid for the member status
	public static final String MEMBER_STATUS_HELP = "Member status must be "
			+ "one of the following characters: A(ctive) or S(uspended)";
	
	public void validateMemberStatus(char newStatus){
		if(MEMBER_STATUS_VALUES.indexOf(newStatus) < 0)
			throw new IllegalArgumentException(MEMBER_STATUS_HELP);
	}
	
	
////////Service Validation
	public static final int SERVICE_CODE_LENGTH = 6;
	public static final int SERVICE_NAME_LENGTH = 20;
	public static final double MAX_FEE = 1000;
	private static NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
	
	public void validateServiceCode(String aCode){
		if (aCode == null || aCode.length() == 0)
			throw new IllegalArgumentException("A service code is required");
		else if (aCode.length() > SERVICE_CODE_LENGTH)
			throw new IllegalArgumentException
			("The service code may not be more than "
					+ SERVICE_CODE_LENGTH + " digits");
		else
		{
			//check that each character is a digit
			for (int i = 0; i < aCode.length(); i++)
				if (! Character.isDigit(aCode.charAt(i)))
					throw new IllegalArgumentException
					("The service code may contain digits only");
		}
	}
	public void validateServiceName(String aName){
		if (aName == null || aName.length() == 0)
			throw new IllegalArgumentException("A service name is required");
		else if (aName.length() > SERVICE_NAME_LENGTH)
			throw new IllegalArgumentException
			("The service name may not be more than " 
					+ SERVICE_NAME_LENGTH + " characters");
	}
	public void validateServiceFee(double aFee){
		if (aFee < 0 || aFee >= MAX_FEE)
			throw new IllegalArgumentException
			("The fee must be between $0 and " + formatter.format(MAX_FEE));
	}
}
