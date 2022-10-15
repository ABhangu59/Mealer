package codes.aydin.mealer;

public class Review {
    private String review;
    private int rating;
    private Client client;

    public Review(int rating, String review, Client client) {
        this.rating = rating;
        this.review = review;
        this.client = client;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    public Client getClient() {
        return client;
    }

    // Generate getters if we implement editing reviews?
}
