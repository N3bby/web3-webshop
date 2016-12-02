package db;

import domain.Product;
import java.util.List;

public interface ProductRepository {

    public Product get(String productId);

    public List<Product> getAll();

    public void add(Product product);

    public void update(Product product);

    public void delete(String productId);

    void close();

}
