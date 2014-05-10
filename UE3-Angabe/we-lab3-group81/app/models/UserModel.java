package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import at.ac.tuwien.big.we14.lab2.api.User;

@Entity
public class UserModel implements User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String name;
	
	@Column(length = 255)
	private String hashedPass; 
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name= name;
	}
	
	public Long getId() {
		return id;
	}

	public String getPassword() {
		return hashedPass;
	}

	public void setPassword(String password) {
		this.hashedPass = password;
	}
	
	public static UserModel authenticate(String username, String password) {
		UserModel out = new UserModel();
		out.setName("maxi");
		out.setPassword("password");
		
		return out;
	}

	public static UserModel findUserByName(String username) {
		/*
		EntityManager em = JPA.em();
		String queryString = "SELECT u FROM UserModel u WHERE username = :username";
		TypedQuery<UserModel> query = em.createQuery(queryString, UserModel.class);
		query.setParameter("username", username);
		
		return query.getSingleResult();
		//*/
		return UserModel.authenticate("hans", "huber");
	}
}
