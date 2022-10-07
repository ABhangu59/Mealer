import java.util.ArrayList;



public class Cook extends User{

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

	


	//Constructor
	public Cook(String firstName, String lastName, String email, String password, String address,
				String personalDescription){

		super(firstName,lastName, email, password, address);

		this.personalDescription = personalDescription;
		voidCheque = "This is a placeholder until we have a real image";
		menu = new ArrayList<Dish>();
	}

	//getters
	public String getPersonalDescription(){ return personalDescription; }
	public String getVoidCheque()		  { return voidCheque; }

	//have to fugure out getters and setters regarding the menu
	
}