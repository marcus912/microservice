package com.evn.msf4j.model;

public class Meal {
	
	Meal() {
		
	}
	
	public Meal(String n, Double p) {
		this.name = n;
		this.price = p;
	}
	
	private String name;
	private Double price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
}
