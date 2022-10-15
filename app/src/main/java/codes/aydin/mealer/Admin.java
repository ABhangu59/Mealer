package codes.aydin.mealer;

import java.util.ArrayList;

public class Admin extends User {
	
	/*
	 * Not much at the moment for admin class
	 * Admin works more as a generic user with specific functions
	 */

	ArrayList<Complaint> inbox;

	//constructor
	public Admin(String firstName, String lastName, Address address){
		super(firstName, lastName, "admin@mealer.ca", "password123", address);
		inbox = new ArrayList<Complaint>();
	}

	public void suspendCook(Cook cook) {
		cook.setSuspended(true);
	}


}