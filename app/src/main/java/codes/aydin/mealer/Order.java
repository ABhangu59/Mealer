package codes.aydin.mealer;

public class Order {
    private int status; //0 = pending; 1 = approved; 2 = rejected;
    private Client client;
    private Dish meal;
    private long pickupTime;

    public Order(Client client, Dish meal, long pickupTime) {
        this.client = client;
        this.meal = meal;
        this.pickupTime = pickupTime;
        status = 0;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public Client getClient() {
        return client;
    }

    public Dish getMeal() {
        return meal;
    }
}
