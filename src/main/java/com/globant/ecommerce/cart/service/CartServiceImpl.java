package com.globant.ecommerce.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globant.ecommerce.cart.dao.CartDAOImpl;
import com.globant.ecommerce.cart.model.CartModel;
import com.globant.ecommerce.cart.model.Product;
import com.globant.ecommerce.cart.model.UserModel;
/**
 * 
 * @author vivek.sachapara
 *
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartDAOImpl dao;

	/**
	 * Method to add prodcut in cart
	 */
	@Override
	public int addToCart(CartModel cartmodel) {
		// TODO Auto-generated method stub
		Product  product = dao.findProductFromCart(cartmodel.getCartid(), cartmodel.getProduct().getProductId());
		
		if(product != null) {
			System.out.println(product.getProductName());
			return 0;
		}
		else {
			
			return dao.addToCart(cartmodel);
		}
	}

	/**
	 * Method to delete product from cart
	 */
	@Override
	public int deleteFromCart(int cartid, int productid) {
		// TODO Auto-generated method stub
		return dao.deleteFromCart(cartid, productid);
	}

	/**
	 * Method to lsit all product in cart
	 */
	@Override
	public List<Product> viewAllFromCart(int cartid) {
		// TODO Auto-generated method stub
		return dao.viewAllFromCart(cartid);
	}

	/**
	 * Method to get userid using cart id as parameter
	 */
	@Override
	public String getUserid(int cartid) {
		// TODO Auto-generated method stub
		return dao.getUserid(cartid);
	}

	/**
	 * Method to empty cart
	 */
	@Override
	public int emptyCart(int cartid) {
		// TODO Auto-generated method stub
		return dao.emptyCart(cartid);
	}

	/**
	 * Method to get cartid from userid
	 */
	@Override
	public int getCartid(String userid) {
		// TODO Auto-generated method stub
		return dao.getCartid(userid);
	}

	/**
	 * Method to get user using userid
	 */
	@Override
	public UserModel getuser(String userid) {
		// TODO Auto-generated method stub
		return dao.getuser(userid);
	}

	/**
	 * Method to add user in usercart database
	 */
	@Override
	public int addUser(UserModel user) {
		// TODO Auto-generated method stub
		return dao.addUser(user);
	}

	/**
	 * Method to find product from cart
	 */
	@Override
	public Product findProductFromCart(int cartid, int productid) {
		// TODO Auto-generated method stub
		return dao.findProductFromCart(cartid, productid);
	}

}
