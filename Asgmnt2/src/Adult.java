/**
 * Class Name: Adult
 * Author: s3685754
 * Date: 21st May 2018
 * Version 2
 *
 * This is the Adult class that represents Adult user in the system and derived from Person class.
 */
import java.util.*;
import java.lang.*;


public class Adult extends Person implements Classmate,Colleague {
    private ArrayList<Person> children;
    private Adult partner;
    private String className;
    private String officeName;
    public static final int ADULT_MIN_AGE=17;
        public Adult(String name, int age, String image, String status, String state,
    		String sex, ArrayList<Person> friend_list, ArrayList<Person> children){
        super(name, age, image, status, state, sex, friend_list);
        this.children=children;
    }
    
    public boolean isClassmateOf(Person P){ //Implementing interface method
    	if(className==((Adult)P).className)
    			return true;
    	else
    		return false;
    }
    
    public boolean makeClassmate(Person P) { //Implementing interface method
    	if(isClassmateOf(P))
    		return false;
    	else {
    		className=ADULT_CLASS;
    		return true;
    	}
    }
    
    public boolean isColleagueOf(Adult A) {
    	if(officeName==A.officeName)
    		return true;
    	else
    		return false;
    	
    }
	public boolean makeColleage(Adult A) {
		if(isColleagueOf(A))
			return false;
		else {
			officeName=OFFICE;
			return true;
		}
		
	}
    /*public boolean isColleage(Adult A1) { //Implementing interface method
    	
    } */

    public void setPartner(Adult P) { // Mutator for amending partner
    	partner = P;
    }
    
    public Adult getPartner() { // Accessor for partner
    	return partner;
    }
    
    @Override 
	public boolean addedTo(Person p1) throws NotToBeFriendsException,TooYoungException { 
		
		if (p1.getAge() < ADULT_MIN_AGE) {
			throw new NotToBeFriendsException(p1.getName() + " is not a Adult.",
					p1.getAge());
	
		} else {

			if (p1 instanceof Child) {
				//System.out.println(p1.getName() + " is a Dependant. Dependant cannot be a friend of Adult.");
				return false;

			} else {
				return super.addedTo(p1);
			}

		}

	}
    
    public boolean addChild(Person p1) { // Add a children
 
    	if(p1 instanceof Child){
    		this.children.add(p1); 
            return true;
        }else{
        	//System.out.println(p1.getName() +" is not a Child.");
            return false;
        }
 
    }

    @Override 
    public void viewDetails(){ 
        super.viewDetails();
    }

	public String showChildren() { // Displaying name of children of current user

		String[] chrAr = new String[children.size()];

		for (int i = 0; i < children.size(); i++) {
			chrAr[i] = children.get(i).getName();
		}
		return Arrays.toString(chrAr);
	}

	

}// end class


