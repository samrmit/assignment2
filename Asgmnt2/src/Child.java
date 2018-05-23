import java.util.ArrayList;

/**
 * Class: Child
 * Author: s3685754
 * Date: 21st May 2018
 * Version 2
 *
 * This is the Dependent class that represents Dependent user in the system and derived from Person class.
 */
public class Child extends Person implements Classmate{
	private Adult parentName1;
    private Adult parentName2;
    private String className;
    public Child(String name, int age, String image, String status, String state, String sex, ArrayList<Person> friend_list,
    		Adult parentName1, Adult parentName2) {
        super(name, age, image, status, state, sex, friend_list);
        this.parentName1 = parentName1;
        this.parentName2 = parentName2;
    }
    
    public boolean isClassmateOf(Person P){ //Implementing interface method
    	if(className==((Child)P).className)
    			return true;
    	else
    		return false;
    }
    
    public boolean makeClassmate(Person P) { //Implementing interface method
    	if(isClassmateOf(P))
    		return false;
    	else {
    		className=CHILD_CLASS;
    		return true;
    	}
    }

    public String getParentName1() {
        return parentName1.getName();
    }

    public String getParentName2() {
        return parentName2.getName();
    }
    
    @Override 
    public boolean addedTo(Person p1) throws NotToBeFriendsException,TooYoungException { 

        
    	
    	if (p1 instanceof Child) {

            if (p1.getAge() <= 2 || this.getAge() <= 2) {
                 	 throw new TooYoungException(this.getName() + " is " + this.getAge() + " and " + p1.getName() + " is " + p1.getAge()+". Children with age 2 years or younger cannot have friends.");

            } else if (Math.abs(p1.getAge() - super.getAge()) > 3) {
                throw new NotToBeFriendsException( " Cannot be added as a friend. These two users' age gap is ", p1.getAge());

            } else {
 
            		return super.addedTo(p1);
 
            }


        } else {

        	return false;
        }

    } // Over

    @Override
    public void viewDetails() {
        super.viewDetails();
        //System.out.println("Parent 1: " +getParentName1()+"\nParent 2: "+getParentName2());
    } 


} //end class

