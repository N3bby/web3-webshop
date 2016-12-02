package domain;

public class Product {

    private String id;
    private String description;
    private double price;

    public Product() { }

    public Product(String id, String description, double price) {
        setId(id);
        setDescription(description);
        setPrice(price);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty())
            throw new IllegalArgumentException("Product id cannot be empty.");
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty())
            throw new IllegalArgumentException("Product description cannot be empty.");
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price < 0) throw new IllegalArgumentException("Product price cannot be negative.");
        this.price = price;
    }

}
