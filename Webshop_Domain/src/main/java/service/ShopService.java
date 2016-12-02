package service;

import java.util.List;
import java.util.Properties;

import db.*;
import domain.Person;
import domain.Product;

public class ShopService {

//	private PersonRepository personRepository = new PersonRepositoryInMemory();
//  private ProductRepository productRepository = new ProductRepositoryInMemory();

	private PersonRepository personRepository;
    private ProductRepository productRepository;

	public ShopService(Properties properties){
        personRepository = new PersonRepositoryDB(properties);
        productRepository = new ProductRepositoryDB(properties);
	}

	//Person ----------------------

	public Person getPerson(String personId) {
		return getPersonRepository().get(personId);
	}

	public List<Person> getPersons() {
		return getPersonRepository().getAll();
	}

	public void addPerson(Person person) {
		getPersonRepository().add(person);
	}

	public void updatePersons(Person person) {
		getPersonRepository().update(person);
	}

	public void deletePerson(String id) {
		getPersonRepository().delete(id);
	}

	private PersonRepository getPersonRepository() {
		return personRepository;
	}

    public Person getUserIfAuthenticated(String personId, String password) {

        Person person = getPerson(personId);
        return person != null && person.isCorrectPassword(password) ? person : null;

    }

	//Product ----------------------

	public Product getProduct(String productId) {
        return getProductRepository().get(productId);
	}

    public List<Product> getProducts() {
        return getProductRepository().getAll();
    }

    public void addProduct(Product product) {
        getProductRepository().add(product);
    }

    public void updateProduct(Product product) {
        getProductRepository().update(product);
    }

    public void deleteProduct(String id) {
        getProductRepository().delete(id);
    }

    private ProductRepository getProductRepository() {
        return productRepository;
    }

    //Etc -------------------------

    public void close() {
        productRepository.close();
        personRepository.close();
    }

}
