package com.globant.ecommerce.cart.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.globant.ecommerce.cart.model.CartModel;
import com.globant.ecommerce.cart.model.Product;
import com.globant.ecommerce.cart.model.UserModel;
/**
 * 
 * @author vivek.sachapara
 *
 */
@Component
public class CartDAOImpl implements CartDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Method to add product to cart
	 */
	@Override
	public int addToCart(CartModel cartModel) {
		// TODO Auto-generated method stub
		final String query = " insert into cart(cartid,userid,productName,productId,quantity,price) values (?,?,?,?,?,?) ";
		Object param[] = { cartModel.getCartid(), cartModel.getUserid(), cartModel.getProduct().getProductName(),
				cartModel.getProduct().getProductId(), cartModel.getProduct().getQuantity(),
				cartModel.getProduct().getPrice() };
		int result = 0;
		try {
			result = jdbcTemplate.update(query, param);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	/**
	 * Method to delete product from cart using productid and cartid
	 * 
	 * @param productid,cartid
	 */
	@Override
	public int deleteFromCart(int cartid, int productid) {
		// TODO Auto-generated method stub
		final String query = "delete from cart where productId = ? and cartid = ?";
		Object param[] = { productid, cartid };
		int result = 0;
		try {
			result = jdbcTemplate.update(query, param);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	/**
	 * Method to view all product from cart
	 * 
	 * @param cartid
	 */
	@Override
	public List<Product> viewAllFromCart(int cartid) {
		// TODO Auto-generated method stub
		final String query = "select productId,productName,quantity,price from cart where cartid = ?";
		Object param[] = { cartid };
		List<Product> cartProductList = new ArrayList<Product>();
		try {

			cartProductList = jdbcTemplate.query(query, param, new BeanPropertyRowMapper<Product>(Product.class));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cartProductList;
	}

	/**
	 * Method to get userid using cartid
	 * 
	 * @param cartid
	 */
	@Override
	public String getUserid(int cartid) {
		// TODO Auto-generated method stub
		final String query = "select userid from db.usercart where cartid = ?";
		Object param[] = { cartid };
		String userid = "";
		try {
			userid = jdbcTemplate.queryForObject(query, param, String.class);
		} catch (Exception e) {
			// TODO: handle exception
			return userid;
		}
		return userid;
	}

	/**
	 * Method to delete all product from cart
	 * 
	 * @param cartid
	 */
	@Override
	public int emptyCart(int cartid) {
		final String query = "delete from cart where cartid = ?";
		Object param[] = { cartid };
		int result = 0;
		try {
			result = jdbcTemplate.update(query, param);
			System.out.println(result);
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	/**
	 * Method to getcart id from userid
	 * 
	 * @param userid
	 */
	@Override
	public int getCartid(String userid) {
		// TODO Auto-generated method stub
		final String query = "select cartid from usercart where userid = ?";
		Object param[] = { userid };
		int cartid = 0;
		try {
			cartid = jdbcTemplate.queryForObject(query, param, Integer.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cartid;
	}

	/**
	 * Method to get user
	 * 
	 * @param userid
	 */
	@Override
	public UserModel getuser(String userid) {
		final String query = "select * from usercart where userid = ?";
		Object param[] = { userid };
		UserModel user = null;
		try {
			user = jdbcTemplate.queryForObject(query, param, new BeanPropertyRowMapper<UserModel>(UserModel.class));
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
		}

		return user;
	}

	/**
	 * Method to add user to usercart database
	 * 
	 * @param user(UserModel)
	 */
	@Override
	public int addUser(UserModel user) {
		// TODO Auto-generated method stub
		final String query = "insert into usercart values (?,?)";
		Object param[] = { user.getCartid(), user.getUserid() };
		int result = 0;
		try {
			result = jdbcTemplate.update(query, param);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	/**
	 * Method to find product from cart
	 * 
	 * @param cartid,productid
	 */
	public Product findProductFromCart(int cartid, int productid) {
		// TODO Auto-generated method stub
		final String query = "select * from cart where cartid = ? and productid = ?";
		Object param[] = { cartid, productid };
		Product product = null;
		try {
			product = jdbcTemplate.queryForObject(query, param, new BeanPropertyRowMapper<Product>(Product.class));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return product;

	}

}
