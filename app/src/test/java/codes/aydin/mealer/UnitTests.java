package codes.aydin.mealer;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {
    Address a = new Address("100 Louis Pasteur", "", "Ottawa", "Ontario", "K1N 6N5");
    CreditCard cc = new CreditCard("Tomer Szulsztein", "1234567821322212", "02", "23", "222");
    Client c = new Client("Tomer", "Szulsztein", "tszul056@uottawa.ca", "password", a, cc);
    Dish d1 = new Dish("Macaroni and Cheese", "Dinner", "American", "Macaroni, Cheese", "Dairy", "Super yummy", 7.99);
    Dish d2 = new Dish("Cheeseburger", "Lunch", "American", "Bread, Cheese, Beef", "Dairy, Gluten", "Better than Burger King", 2.99);
    Dish d3 = new Dish("Lettuce", "Snack", "Natural", "Lettuce", "", "Green", 99.99);
    Dish d4 = new Dish("Lasagna", "Dinner", "Italian", "Tomato Sauce, Cheese, Beef, Pasta", "Dairy, Gluten", "Has layers", 1111.04);
    Order o = new Order(c, d1, 123);

    Admin admin = new Admin("Tomer", "Szulsztein", a);
    Cook cook1 = new Cook("Cook", "Cookingson", "cook@mealer.app", "passowrd", a, "description", "voidCheque");
    Cook cook2 = new Cook("Cook", "Cookingson", "cook@mealer.app", "passowrd", a, "description", "voidCheque");

    Review review = new Review(3, "very yummy", c);

    @Test
    public void orderGetMeal_isFuntional() {assertEquals(d1,o.getMeal());}

    @Test
    public void dishGetName_isFunctional() {assertEquals("Macaroni and Cheese",d1.getMealName());}

    @Test
    public void dishSetPrice_isFunctional() {d4.setPrice(16); assertEquals(16,d4.getPrice(), 0); }

    @Test
    public void dishSetMealType_isFunctional() {d4.setMealType("Edible"); assertEquals("Edible",d4.getMealType()); }

    @Test
    public void clientGetPassword_isFunctional() {assertEquals("password",c.getPassword());}

    @Test
    public void creditCardGetName_isFunctional() {assertEquals("Tomer Szulsztein",cc.getNameOnCard());}

    @Test
    public void addSingleDishToCook_isFunctional() {
        cook1.addDish(d1);
        ArrayList<Dish> menu = new ArrayList<Dish>();
        menu.add(d1);
        assertEquals(menu,cook1.getMenu());
    }

    @Test
    public void addMultipleDishesToCook_isFunctional() {
        ArrayList<Dish> dishes = new ArrayList<Dish>();
        dishes.add(d2);
        dishes.add(d3);
        cook2.addDish(dishes);

        ArrayList<Dish> menu = new ArrayList<Dish>();
        menu.add(d2);
        menu.add(d3);
        assertEquals(menu,cook2.getMenu());
    }

    @Test
    public void suspendCook_isFunctional() {admin.suspendCook(cook1); assertTrue(cook1.getSuspended());}

    @Test
    public void reviewGetRating_isFunctional() {
        assertEquals(3,review.getRating());
    }

    @Test
    public void reviewGetClient_isFunctional() { assertEquals(c,review.getClient());}

    @Test
    public void reviewGetReview_isFunctional() { assertEquals("very yummy", review.getReview());}

}