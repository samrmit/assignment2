import java.util.ArrayList;

/**
 * Class: Dependent
 * Author: s3685754
 * Date: 25th March 2018
 * Version 1
 *
 * This is the Dependent class that represents Dependent user in the system and derived from Person class.
 */
public class Dependent extends Person implements Classmate{

    private Adult parentName1;
    private Adult parentName2;
    private boolean isYoungChild = false;

    public Dependent(String name, int age, String image, String status, String state, String sex, ArrayList<Person> friend_list,
    		Adult parentName1, Adult parentName2) {
        super(name, age, image, status, state, sex, friend_list);
        this.parentName1 = parentName1;
        this.parentName2 = parentName2;
        if (age <3 ) {
        	this.isYoungChild = true;
        }
    }
    
    public void callOtherClassmates() {
    	//some action
    }

    public String getParentName1() {
        return parentName1.getName();
    }

    public String getParentName2() {
        return parentName2.getName();
    }
    
    public boolean getIsYoungChild() {
        return isYoungChild;
    }

    public boolean addedTo(Person p1) throws NotToBeFriendsException,TooYoungException { // Overriding super class method

        
    	
    	if (p1 instanceof Dependent) {

            if (p1.getAge() <= 2 || this.getAge() <= 2) {
                //System.out.println("Cannot added as friends. Both the dependants should not be less than or equal to 2 years of age to become friends.");
                //System.out.println(this.getName() + " is " + this.getAge() + " and ." + p1.getName() + " is " + p1.getAge());
            	 throw new TooYoungException(this.getName() + " is " + this.getAge() + " and ." + p1.getName() + " is " + p1.getAge());

            } else if (Math.abs(p1.getAge() - super.getAge()) > 3) {
                //System.out.println(p1.getName() + " cannot be added as a friend. These two users' age gap is " + Math.abs(p1.getAge()) + ".");
                //System.out.println("Dependants' age gap should not exceed 3 years to become friends. ");
                throw new NotToBeFriendsException( " cannot be added as a friend. These two users' age gap is ", p1.getAge());
                //return false;
            } else {
 
            		return super.addedTo(p1);
 
            }


        } else {

            System.out.println(p1.getName() + " is not a Dependant. Dependant can have only dependant friends.");
            return false;
        }

    } // Over

    public void viewDetails() {
        super.viewDetails();
        System.out.println("Parent 1: " +parentName1.getName()+"\nParent 2: "+parentName2.getName());
    } //Overriding from super class


} //end class

