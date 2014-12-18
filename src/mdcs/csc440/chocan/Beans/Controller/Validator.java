package mdcs.csc440.chocan.Beans.Controller;

public class Validator {
	
	public Validator(){
	}
	
	private static final int NAME_LENGTH = 25;
	public void validatePersonName(String aName) throws IllegalArgumentException{
		if (aName == null || aName.length() == 0)
			throw new IllegalArgumentException("A name is required");
		else if (aName.length() > NAME_LENGTH)
			throw new IllegalArgumentException("Name can not be more than " 
					+ NAME_LENGTH + " characters");
	}

}
