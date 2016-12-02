package db;

import domain.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepositoryInMemory implements ProductRepository {

    private Map<String, Product> products = new HashMap<String, Product>();

    public ProductRepositoryInMemory () {
        Product product1 = new Product("note7", "Galaxy Note 7", 739);
        add(product1);
    }

    @Override
    public Product get(String productId){
        if(productId == null){
            throw new IllegalArgumentException("No id given");
        }
        return products.get(productId);
    }

    @Override
    public List<Product> getAll(){
        return new ArrayList<Product>(products.values());
    }

    @Override
    public void add(Product product){
        if(product == null){
            throw new IllegalArgumentException("No product given");
        }
        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException("Product already exists");
        }
        products.put(product.getId(), product);
    }

    @Override
    public void update(Product product){
        if(product == null){
            throw new IllegalArgumentException("No product given");
        }
        if(!products.containsKey(product.getId())){
            throw new IllegalArgumentException("No product found");
        }
        products.put(product.getId(), product);
    }

    @Override
    public void delete(String productId){
        if(productId == null){
            throw new IllegalArgumentException("No id given");
        }
        products.remove(productId);
    }

    @Override
    public void close() {

    }

}
