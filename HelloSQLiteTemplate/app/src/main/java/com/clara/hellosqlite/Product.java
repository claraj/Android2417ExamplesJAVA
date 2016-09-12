package com.clara.hellosqlite;

public class Product {

	String name;
	int quantity;

	public Product(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Name: " + name + " quantity: " + quantity;
	}
}
