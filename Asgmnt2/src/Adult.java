/**
 * Class Name: Adult
 * Author: s3685754
 * Date: 25th March 2018
 * Version 1
 *
 * This is the Adult class that represents Adult user in the system and derived from Person class.
 */
import java.util.*;
import java.lang.*;


public class Adult extends Person implements Classmate,Colleague {
    private ArrayList<Person> children;

    public Adult(String name, int age, String image, String status, String state, String sex, ArrayList<Person> friend_list, ArrayList<Person> children){
        super(name, age, image, status, state, sex, friend_list);
        this.children=children;
    }
    
    public void callOtherClassmates() {
    	//some action
    }
    
    public void callMeeting() {
    	//some action
    }

/*    public String[] getChildren(){ // Accessor
        return children;
    }*/

	public boolean addedTo(Person p1) throws NotToBeFriendsException,TooYoungException { // Overriding super class method
		
		if (p1.getAge() < adultMinAge) {
			throw new NotToBeFriendsException(p1.getName() + " is a Dependant. Dependant cannot be a friend of Adult.",
					p1.getAge());
	
		} else {

			if (p1 instanceof Dependent) {
				System.out.println(p1.getName() + " is a Dependant. Dependant cannot be a friend of Adult.");
				return false;

			} else {
				return super.addedTo(p1);
			}

		}

	}
    
    public boolean addChild(Person p1) { // Add a children
 
    	if(p1 instanceof Dependent){
    		this.children.add(p1); 
            return true;
        }else{
        	System.out.println(p1.getName() +" is not a Child.");
            return false;
        }
 
    }

    public void viewDetails(){ // Overriding super class method
        super.viewDetails();
        System.out.println("Children list: " +showChildren());
    }

	public String showChildren() { // Displaying name of children of current user

		String[] chrAr = new String[children.size()];

		for (int i = 0; i < children.size(); i++) {
			chrAr[i] = children.get(i).getName();
		}
		return Arrays.toString(chrAr);
	}


}// end class


