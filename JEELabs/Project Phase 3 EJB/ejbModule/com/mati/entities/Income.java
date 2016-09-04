package com.mati.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "incomes")
public class Income {
	
	@Id
	@GeneratedValue
	@Column(name = "income_id", nullable = false)
	private long id; 
	
	@Column(name = "user", nullable = false)
	private long user;

	
	@Column(name = "date")
	private Timestamp date; 
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private double price;

	public Income(){}
	
	public Income(long userId, Timestamp date, String description, double price) {
		this.user = userId;
		this.date = date;
		this.description = description;
		this.price = price;
	}

	public Income(long userId, String description, double price) {
		this.user = userId;
		this.description = description;
		this.price = price;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Income [id=" + id + ", user=" + user + ", date=" + date + ", description=" + description + ", price="
				+ price + "]";
	}
	

	
	
	
}
