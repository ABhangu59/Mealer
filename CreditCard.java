/*
 * Date: Oct 7, 2022
 * Student: Mustafa Ahmed 
 * Student No: 300242013
 * Class: SEG2105
 *
 * - This credit card class is used by the Client class
 * - Each client will store a unique credit card that can be edited as needed
 * - It stores all the client's credit card info and can make changes to the saved information
 */


public class CreditCard{
	
	/*
	 * Instance variables
	 */
	private String nameOnCard;
	private short creditCardNum;
	private short expiration;
	private short cvc;

	/*
	 * Constructor
	 */
	public CreditCard(String nameOnCard, short creditCardNum, short expiration, short cvc){

		this.nameOnCard = nameOnCard;
		this.creditCardNum = creditCardNum;
		this.expiration = expiration;
		this.cvc = cvc;
	}

	/*
	 * Instance Methods
	 */

	//getters
	public String getNameOnCard()	{ return nameOnCard; }
	public short getCreditCardNum()	{ return creditCardNum; }
	public short getExpiration()	{ return expiration; }
	public short getCVC()			{ return cvc; }

	//setters
	public void setNameOnCard(String newNameCard)  { nameOnCard = newNameCard; }
	public void setCreditCardNum(short newCardNum) { creditCardNum = newCardNum; }
	public void setExpiration(short newExpiration) { expiration = newExpiration; }
	public void setCVC(short newCVC)			   { cvc = newCVC; }
}