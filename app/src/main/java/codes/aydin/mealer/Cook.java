package codes.aydin.mealer;

import java.util.ArrayList;

import codes.aydin.mealer.User;

/*
 * Date: Oct 7, 2022
 * Student: Mustafa Ahmed 
 * Student No: 300242013
 * Class: SEG2105
 *
 * - This is a child class of the User class
 * - The Cook class stores all the cook's personal information
 * - It also stores the cook's menu as an ArrayList using the built in Java class
 */

public class Cook extends User {

	//Instance variables

	private String personalDescription;

	/*
	 * voidCheque is temporarily being stored as a String
	 * until we figure out a way to store image objects
	 */
	private String voidCheque; 

	/*
	 * The Cook's menu is being stored as an array list of Dish objects. 
	 * This will allow for easy manipulation of the menu
	 */
	private ArrayList<Dish> menu;

	


	/*
	 * Constructor
	 */
	public Cook(String firstName, String lastName, String email, String password, String address,
				String personalDescription){

		super(firstName,lastName, email, password, address);

		this.personalDescription = personalDescription;
		voidCheque = "This is a placeholder until we have a real image";
		menu = new ArrayList<Dish>();
	}

	//getters
	public String getPersonalDescription() { return personalDescription; }
	public String getVoidCheque()		   { return voidCheque; }

	//setters
	public void setPersonalDescription( String newDesc) { personalDescription = newDesc; }
	public void setVoidCheque(String newCheque)		    { voidCheque = newCheque; }

	//have to fugure out getters and setters regarding the menu//
	
}