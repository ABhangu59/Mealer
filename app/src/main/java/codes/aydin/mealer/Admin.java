package codes.aydin.mealer;

public class Admin extends User{
	
	/*
	 * Not much at the moment for admin class
	 * Admin works more as a generic user with specific functions
	 */
	//constructor
	public Admin(String firstName, String lastName, String address){
		super(firstName, lastName, "admin@mealer.ca", "password123", address);
	}


}