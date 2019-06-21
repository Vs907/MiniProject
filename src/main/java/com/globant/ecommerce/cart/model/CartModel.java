package com.globant.ecommerce.cart.model;

public class CartModel {

	
	private int cartid;
	private Product product;


	public int getCartid() {
		return cartid;
	}

	public void setCartid(int cartid) {
		this.cartid = cartid;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
