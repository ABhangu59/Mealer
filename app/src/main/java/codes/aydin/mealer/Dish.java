package codes.aydin.mealer;/*
 * Date: Oct 7, 2022
 * Student: Mustafa Ahmed 
 * Student No: 300242013
 * Class: SEG2105
 *
 * - This Dish class is used by the Cook class
 * - Cooks will add Dishes to their menus
 * - It stores all the information about a given dish
 * - Once stored the details about each dish can be edited
 */

//TODO Do we need cuisine type and meal type?

public class Dish{

	/*
	 * Instance variables
	 */
	String mealName;
	String mealType;
	String cuisineType;
	String ingredients;
	String allergens;
	String description;
	double price;

	/*
	 * Constructor
	 */
	public Dish(String mealName, String mealType, String cuisineType, String ingredients, String allergens, String description, double price){
		
		this.mealName = mealName;
		this.mealType = mealType;
		this.cuisineType = cuisineType;
		this.ingredients = ingredients;
		this.allergens = allergens;
		this.description = description;
		this.price = price;
	}

	/*
	 * Instance methods
	 */

	//getters
	public String getMealName()		{ return mealName; }
	public String getMealType()		{ return mealType; }
	public String getCuisineType()	{ return cuisineType; }
	public String getIngredients()	{ return ingredients; }
	public String getAllergens()	{ return allergens; }
	public String getDescription()	{ return description; }
	public double getPrice()		{ return price; }

	//setters
	public void setMealName(String newMealName)			{ mealName = newMealName; }
	public void setMealType(String newMealType)			{ mealType = newMealType; }
	public void setCuisineType(String newCuisineType)	{ cuisineType = newCuisineType; }
	public void setIngredients(String newIngr)			{ ingredients = newIngr; }
	public void setAllergens(String newAllergens)		{ allergens = newAllergens; }
	public void setDescriptions(String newDesc)			{ description = newDesc; }
	public void setPrice(double newPrice)				{ price = newPrice; }


}