package mdcs.csc440.chocan.Beans.Controller;

import java.text.NumberFormat;
import java.util.Locale;

public class Validator {
	
	public Validator(){
	}
	
///////////////////PERSON VALIDATION
	public void validatePersonName(String aName) throws IllegalArgumentException{
		if (aName == null || aName.length() == 0)
			throw new IllegalArgumentException("A name is required");
		else if (aName.length() > PERSON_NAME_LENGTH)
			throw new IllegalArgumentException("Name can not be more than " 
					+ PERSON_NAME_LENGTH + " characters");
	}
	public void validatePersonStreet(String aStreet) throws IllegalArgumentException{
		if (aStreet == null) aStreet = "";	//Replace null with an empty string
		else if (aStreet.length() > STREET_LENGTH)
			throw new IllegalArgumentException("Street can not be more than " 
					+ STREET_LENGTH + " characters");
	}
	public void validatePersonCity(String aCity) throws IllegalArgumentException{
		if (aCity == null) aCity = "";		//Replace null with an empty string
		else if (aCity.length() > CITY_LENGTH)
			throw new IllegalArgumentException("City can not be more than " 
					+ CITY_LENGTH + " characters");
	}
	public void validatePersonState(String aState) throws IllegalArgumentException{
		if (aState == null) aState = "";	//Replace null with an empty string
		else if (aState.length() > 0)
		{
			if (aState.length() != STATE_LENGTH) 
				throw new IllegalArgumentException("State must be exactly "
						+ STATE_LENGTH + " letters");
			//test whether each character is a letter
			for (int i = 0; i < STATE_LENGTH; i++)
				if(! Character.isLetter(aState.charAt(i)))
					throw new IllegalArgumentException("State must be exactly " 
							+ STATE_LENGTH + " letters only");
		}
	}
	public void validatePersonZip(String aZip) throws IllegalArgumentException{
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
					throw new IllegalArgumentException("Zip code must be exactly " 
							+ ZIP_LENGTH + " digits only");
		}
	}
	public void validatePersonEmail(String anEmail) throws IllegalArgumentException{
		if(!anEmail.matches(EMAIL_FORMAT) || anEmail == null || anEmail.length() == 0){
			throw new IllegalArgumentException("Email needs to be in 'abc@dce.com' format ");
		}
	}
	private static final int PERSON_NAME_LENGTH = 25;
	private static final int STREET_LENGTH = 25;
	public static final int CITY_LENGTH = 14;
	public static final int STATE_LENGTH = 2;
	public static final int ZIP_LENGTH = 5;
	public static final String EMAIL_FORMAT = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
	
	
/////////////////////SERVICE VALIDATION
	public void validateServiceCode(String aCode) throws IllegalArgumentException{
		if (aCode == null || aCode.length() == 0)
			throw new IllegalArgumentException("A service code must be present");
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
	}
	public void validateServiceName(String aName) throws IllegalArgumentException{
		if (aName == null || aName.length() == 0)
			throw new IllegalArgumentException("A service name must be present");
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
	public static final int CODE_LENGTH = 6;
	public static final int SERVICE_NAME_LENGTH = 20;
	public static final double MAX_FEE = 1000;
	private static NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
}
