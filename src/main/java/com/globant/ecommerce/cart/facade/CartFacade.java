package com.globant.ecommerce.cart.facade;

import java.util.List;

import com.globant.ecommerce.cart.model.CartModel;
import com.globant.ecommerce.cart.model.Product;
import com.globant.ecommerce.cart.model.UserModel;

public interface CartFacade {
	/**
	 * 
	 * @param cartModel
	 * @return
	 */
	public int addToCart(CartModel cartModel);

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
	 * @param user
	 * @return
	 */
	public int addUser(UserModel user);

	/**
	 * @param cartid
	 * @param productid
	 * @return
	 */
	public Product findProductFromCart(int cartid, int productid);

}
