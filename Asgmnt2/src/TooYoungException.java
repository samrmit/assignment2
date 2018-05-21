/*Handles too young exceptions*/
public class TooYoungException extends Exception {
	
	public TooYoungException(String errMsg) {
		super(errMsg);
		System.out.println("Children younger than or equal 2 years cannot have friends.");
		System.out.println("Error message : "+ errMsg);
	}

}
