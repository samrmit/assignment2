/**
 * Class: Person
 * Author: s3685754
 * Date: 25th March 2018
 * Version 1
 *
 * This class in the highest level entity in of Network user. All other user classes are inherited from this class.
 */

import java.util.*;

public abstract class Person {
	
	public static int adultMinAge =17;

    private String name;
    private int age;
    private String sex;
    private String image;
    private String status;
    private String state;
    private ArrayList<Person> friend_list;

    public Person(String name, int age, String image, String status,String state,String sex,
    		ArrayList<Person> friend_list) {
        this.name = name;
        this.age = age;
        this.image = image;
        this.status = status;
        this.state = state;
        this.sex = sex;
        this.friend_list = friend_list;
 
    }

    /* this method add a person to friends list.
     */
    public boolean addedTo(Person p1) throws NotToBeFriendsException,TooYoungException {

        if (isFriendOf(p1)) {
            System.out.println("Cannot added because " +p1.name + " is already a friend.");
            return false;
        } else {
        	friend_list.add(p1); 
            return true;
        }
    }

    /* this method removes a friend from friend list.
     */
    public boolean deletedFrom(Person p1) {

        if (isFriendOf(p1)) {
      	
        	friend_list.remove(p1);
            System.out.println(p1.name + " is removed as a friend from " + this.name);
            return true;

        } else {
            System.out.println(p1.name + " is not a friend of " + this.name);
            return false;
        }

    }

    /* this method shows details of user.
     */
    public void viewDetails() {
        System.out.println("Name: " + name + "\nAge: " + age + "\nImage: " + image + "\nStatus: " + status +
                "\nFriends list: " + showFriends());
    }

    /* 5 accessor methods and 2 mutator methods are included*/

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }
    
    public String getState() {
        return state;
    }

    public String getFriendList() {
    	
    	String[] frAr = new String[friend_list.size()];

		for (int i = 0; i < friend_list.size(); i++) {
			frAr[i] = friend_list.get(i).getName();
		}

		return Arrays.toString(frAr);
    }

    public void setImage(String newImage) {
        image = newImage;
    }

    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public boolean isFriendOf(Person P) {  //Checking the given person is a friend of current user by name
        //List<String> list = Arrays.asList(friend_list);
        return friend_list.contains(P);
    }

    public String showFriends() { // Showing all friends of current user by name
    	
    	String[] frAr = new String[friend_list.size()];
    	
    	for (int i = 0; i < friend_list.size(); i++) {    		
    		frAr[i] = friend_list.get(i).getName();
		}
        return Arrays.toString(frAr);
    }

    public String[] push(String[] array, String newItem) { // adding an item to an existing array
        String[] longer = new String[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = newItem;
        return longer;
    }
    
/*    public String[] push(Person array, Person newItem) { // adding an item to an existing array
        String[] longer = new String[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = newItem;
        return longer;
    }*/

    public String[] remove(String[] array, String delItem) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == delItem) {
                String[] copy = new String[array.length - 1];
                System.arraycopy(array, 0, copy, 0, i);
                System.arraycopy(array, i + 1, copy, i, array.length - i - 1);
                return copy;
            }
        }
        return array;
    } //Removing an item from an existing array


} // end class
