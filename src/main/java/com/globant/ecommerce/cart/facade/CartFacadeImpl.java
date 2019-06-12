package com.globant.ecommerce.cart.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.globant.ecommerce.cart.model.CartModel;
import com.globant.ecommerce.cart.model.Product;
import com.globant.ecommerce.cart.model.UserModel;
import com.globant.ecommerce.cart.service.CartServiceImpl;
/**
 * 
 * @author vivek.sachapara
 *
 */
@Component
public class CartFacadeImpl implements CartFacade {

	@Autowired
	private CartServiceImpl service;


	@Override
	public int addToCart(CartModel cartmodel) {
		// TODO Auto-generated method stub
		return service.addToCart(cartmodel);
	}

	@Override
	public int deleteFromCart(int cartid, int productid) {
		// TODO Auto-generated method stub
		return service.deleteFromCart(cartid, productid);
	}

	@Override
	public List<Product> viewAllFromCart(int cartid) {
		// TODO Auto-generated method stub
		return service.viewAllFromCart(cartid);
	}

	@Override
	public String getUserid(int cartid) {
		// TODO Auto-generated method stub
		return service.getUserid(cartid);
	}

	@Override
	public int emptyCart(int cartid) {
		// TODO Auto-generated method stub
		return service.emptyCart(cartid);
	}

	@Override
	public int getCartid(String userid) {
		// TODO Auto-generated method stub
		return service.getCartid(userid);
	}

	@Override
	public UserModel getuser(String userid) {
		// TODO Auto-generated method stub
		return service.getuser(userid);
	}

	@Override
	public int addUser(UserModel user) {
		// TODO Auto-generated method stub
		return service.addUser(user);
	}

	@Override
	public Product findProductFromCart(int cartid, int productid) {
		// TODO Auto-generated method stub
		return null;
	}

}
