package codes.aydin.mealer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {
    Address a = new Address("100 Louis Pasteur", "", "Ottawa", "Ontario", "K1N 6N5");
    CreditCard cc = new CreditCard("Tomer Szulsztein", "1234567821322212", "02", "23", "222");
    Client c = new Client("Tomer", "Szulsztein", "tszul056@uottawa.ca", "password", a, cc);
    Dish d = new Dish("Macaroni and Cheese", "Dinner", "American", "Macaroni, Cheese", "Dairy", "Super yummy", 7.99);
    Order o = new Order(c, d, 123);

    @Test
    public void orderGetMeal_isFuntional() {assertEquals(o.getMeal(), d);}

    @Test
    public void dishGetName_isFunctional() {assertEquals(d.getMealName(), "Macaroni and Cheese");}

    @Test
    public void clientGetPassword_isFunctional() {assertEquals(c.getPassword(), "password");}

    @Test
    public void creditCardGetName_isFunctional() {assertEquals(cc.getNameOnCard(), "Tomer Szulsztein");}
}