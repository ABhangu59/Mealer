package codes.aydin.mealer;
/*
 * Date: Oct 7, 2022
 * Student: Mustafa Ahmed 
 * Student No: 300242013
 * Class: SEG2105
 *
 * - This is the parent class for all users in the mealer app.
 * - It stores all personal information about the given user
 * - Along with storing data it can return and can change the user's information
 */


public class User {

	/*
	 * Instance variables
	 */
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Address address;
	private boolean logInStatus;

	/*
	 * Constructor
	 */
	public User(String firstName, String lastName, String email, String password, Address address){
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address = address;
		this.logInStatus = false; 
	}

	/*
	 *Instance functions 
	 */

	//getters
	public String getFirstName()	{ return firstName; }
	public String getLastName()		{ return lastName; }
	public String getEmail()		{ return email; }
	public String getPassword()		{ return password; }
	public Address getAddress()		{ return address; }

	//setters
	public void setFirstName(String newFirst)	{ firstName = newFirst; }
	public void setLastName(String newLast)		{ lastName = newLast; }
	public void setEmail(String newEmail)		{ email = newEmail; }
	public void setPassword(String newPass)		{ password = newPass; }
	public void setAddress(Address newAddress)	{ address = newAddress; }

	

	/*
	 * log in status functions
	 */

	//checks if the user is logged in
	public boolean isLoggedIn()	{ return logInStatus == true; }

	//log in and out
	public void logIn()		{ logInStatus = true; }
	public void logOut()	{ logInStatus = false; }


}