
public class NotToBeFriendsException extends Exception{
	
	public NotToBeFriendsException(String errMsg, int age) {
		super(errMsg);
		System.out.println("Age of the new friend you are going to add is "+ age);
		System.out.println("Error message : "+ errMsg);
	}

}
