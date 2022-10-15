package codes.aydin.mealer;/*
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
	private String creditCardNum;
	private String expirationMonth;
	private String expirationYear;
	private String cvv;

	/*
	 * Constructor
	 */
	public CreditCard(String nameOnCard, String creditCardNum, String expirationMonth, String expirationYear, String cvv){

		this.nameOnCard = nameOnCard;
		this.creditCardNum = creditCardNum;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
		this.cvv = cvv;
	}

	/*
	 * Instance Methods
	 */

	//getters
	public String getNameOnCard() { return nameOnCard; }
	public String getCreditCardNum() { return creditCardNum; }
	public String getExpirationMonth() { return expirationMonth; }
	public String getExpirationYear() { return expirationYear; }
	public String getCVV() { return cvv; }

	//setters
	public void setNameOnCard(String nameOnCard)  { this.nameOnCard = nameOnCard; }
	public void setCreditCardNum(String creditCardNum) { this.creditCardNum = creditCardNum; }
	public void setExpirationMonth(String expirationMonth) { this.expirationMonth = expirationMonth; }
	public void setExpirationYear(String expirationYear) { this.expirationYear = expirationYear; }
	public void setCVV(String newCVC) { this.cvv = cvv; }
}