package codes.aydin.mealer;/*
 * Date: Oct 7, 2022
 * Student: Mustafa Ahmed 
 * Student No: 300242013
 * Class: SEG2105
 *
 * - This is a child class of the User class
 * - Along with their personal information, each user has their own credit card stored
 * - The credit card is stored as a "CreditCard" object.
 */

public class Client extends User {

	/*
	 * Instance variables
	 */

	//client's credit card
	private CreditCard creditCard;
	
	/*
	 * Constructor
	 */
	public Client(String firstName, String lastName, String email, String password, String address, 
				  String nameOnCard, short creditCardNum, short expiration, short cvc){
		
		super(firstName,lastName, email, password, address);

		creditCard = new CreditCard(nameOnCard, creditCardNum, expiration, cvc);
	}

	/*
	 *Instance methods
	 */	

	// At the moment, no specific instance methods needed 
	// The required getters and setters are already implemented in the parent class
	// All specific functionalities for the Client class will be done in later deliverables
}