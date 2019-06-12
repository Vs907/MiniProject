package com.globant.ecommerce.cart.service;

import java.util.List;

import com.globant.ecommerce.cart.model.CartModel;
import com.globant.ecommerce.cart.model.Product;
import com.globant.ecommerce.cart.model.UserModel;

public interface CartService {
	/**
	 * 
	 * @param cartmodel
	 * @return
	 */
	public int addToCart(CartModel cartmodel);

	/**
	 * 
	 * @param cartid
	 * @param productid
	 * @return
	 */
	public int deleteFromCart(int cartid, int productid);

	/**
	 * 
	 * @param cartid
	 * @return
	 */
	public List<Product> viewAllFromCart(int cartid);

	/**
	 * 
	 * @param cartid
	 * @return
	 */
	public String getUserid(int cartid);

	/**
	 * 
	 * @param cartid
	 * @return
	 */
	public int emptyCart(int cartid);

	/**
	 * 
	 * @param userid
	 * @return
	 */
	public int getCartid(String userid);

	/**
	 * 
	 * @param userid
	 * @return
	 */
	public UserModel getuser(String userid);

	/**
	 * 
	 * @param userid
	 * @return
	 */
	public int addUser(UserModel userid);

	/**
	 * 
	 * @param cartid
	 * @param productid
	 * @return
	 */
	public Product findProductFromCart(int cartid, int productid);
}
