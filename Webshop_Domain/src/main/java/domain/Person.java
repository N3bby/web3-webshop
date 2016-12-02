package domain;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {

	private String userid;
	private String email;
	private String hashedPassword;
	private String firstName;
	private String lastName;
	private String salt;

	//Constructor for Controller
	public Person(String userid, String email, String password, String firstName, String lastName) {
		this();
		setUserid(userid);
		setEmail(email);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
	}

	//Constructor for DB
	public Person(String userid, String email, String hashedPassword, String firstName, String lastName, String salt) {
		setSalt(salt);
		setUserid(userid);
		setEmail(email);
		setPasswordPreHashed(hashedPassword);
		setFirstName(firstName);
		setLastName(lastName);
	}

	public Person() {
		setSalt(new SecureRandom().generateSeed(20).toString()); //Generate random salt
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		if(userid.isEmpty()){
			throw new IllegalArgumentException("No userid given");
		}
		this.userid = userid;
	}

	public void setEmail(String email) {
		if(email.isEmpty()){
			throw new IllegalArgumentException("No email given");
		}
		String USERID_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern p = Pattern.compile(USERID_PATTERN);
		Matcher m = p.matcher(email);
		if (!m.matches()) {
			throw new IllegalArgumentException("Email not valid");
		}
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return hashedPassword;
	}

	public void setPassword(String password) {
		if(password.isEmpty()){
			throw new IllegalArgumentException("No password given");
		}
		this.hashedPassword = hashPassword(password);
	}

	private void setPasswordPreHashed(String hashedPassword) {
		if(hashedPassword.isEmpty()) {
			throw new IllegalArgumentException("No hashedPassword given");
		}
		this.hashedPassword = hashedPassword;
	}

	public boolean isCorrectPassword(String password) {
		return getPassword().equals(hashPassword(password));
	}

	private String hashPassword(String password) {
		MessageDigest crypt;
		try {
			crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(getSalt().getBytes("UTF-8"));
			crypt.update(password.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException("Problem occurred while hashing password");
		}
		return new BigInteger(1, crypt.digest()).toString(16);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(firstName.isEmpty()){
			throw new IllegalArgumentException("No firstname given");
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(lastName.isEmpty()){
			throw new IllegalArgumentException("No last name given");
		}
		this.lastName = lastName;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		if(salt.trim().isEmpty()) throw new IllegalArgumentException("Salt cannot be empty");
		this.salt = salt;
	}

}
