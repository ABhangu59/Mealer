package codes.aydin.mealer;

public class Complaint {
    private Cook cook;
    private Client client;
    private String description;

    public Complaint(String description, Cook cook, Client client) {
        this.description = description;
        this.cook = cook;
        this.client = client;
    }

}
