package com.globant.ecommerce.cart.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.globant.ecommerce.cart.facade.CartFacadeImpl;
import com.globant.ecommerce.cart.model.CartModel;
import com.globant.ecommerce.cart.model.DataModel;
import com.globant.ecommerce.cart.model.Product;
import com.globant.ecommerce.cart.model.Response;
import com.globant.ecommerce.cart.model.UserModel;

/**
 * 
 * @author vivek.sachapara
 *
 */
@RestController
public class CartRestController {

	@Autowired
	private CartFacadeImpl facade;

	/**
	 * 
	 * @param cartid
	 * @return response
	 */

	@GetMapping("cart/{userid}")
	public ResponseEntity<DataModel> getAllU(@PathVariable("userid") String userid,
			@RequestHeader(value = "authToken", defaultValue = "") String authToken) {
		HttpHeaders headers = new HttpHeaders();
		if (authenticate(authToken, userid)) {
			DataModel datamodel = new DataModel();
			int cartid = facade.getCartid(userid);
			if (cartid == 0) {
				headers.add("message", "Card Detailed Not Found");
				return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
			} else {
				List<Product> productlist = facade.viewAllFromCart(cartid);
				datamodel.setCartid(cartid);
				datamodel.setUserid(userid);
				datamodel.setProduct(productlist);
				headers.add("message", "Cart Details");
				return new ResponseEntity<>(datamodel, headers, HttpStatus.OK);
			}

		}
		headers.add("Msg", "Not Authorised");
		return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);

	}

	/**
	 * 
	 * @param userid
	 * @param authToken
	 * @param product
	 * @return response
	 */


	@PostMapping("/cart/{userid}")
	public ResponseEntity<DataModel> addToCart(@PathVariable("userid") String userid,
			@RequestHeader(value = "authToken", defaultValue = "") String authToken, @RequestBody Product product) {

		HttpHeaders headers = new HttpHeaders();
		if (authenticate(authToken, userid)) {
			CartModel cartModel = new CartModel();
			UserModel user = facade.getuser(userid);
			int cartid = 0;
			if (user != null) {

				cartid = user.getCartid();
			} else {
				user = new UserModel();
				user.setUserid(userid);
				double x = Math.random() * 10000;
				cartid = (int) x;
				user.setCartid(cartid);
				facade.addUser(user);

			}

			cartModel.setCartid(cartid);
			cartModel.setProduct(product);
			int res = facade.addToCart(cartModel);
			if (res == 1) {
				DataModel data = new DataModel();
				data.setCartid(cartid);
				data.setUserid(userid);
				List<Product> productlist = new ArrayList<Product>();
				productlist.add(product);
				data.setProduct(productlist);

				headers.add("message", "Your Product added to cart Successfully");
				return new ResponseEntity<DataModel>(data, headers, HttpStatus.OK);
			} else {
				headers.add("message", "Product already added to cart");
				return new ResponseEntity<DataModel>(null, headers, HttpStatus.METHOD_NOT_ALLOWED);

			}
		} else {
			headers.add("message", "User not logged in");
			return new ResponseEntity<DataModel>(null, headers, HttpStatus.UNAUTHORIZED);
		}

	}

	/**
	 * 
	 * @param authToken
	 * @param cartid
	 * @param productid
	 * @return response
	 */
	@DeleteMapping("/cart/{cartid}/{productid}")
	public ResponseEntity<DataModel> deleteFromCart(
			@RequestHeader(value = "authToken", defaultValue = "") String authToken, @PathVariable("cartid") int cartid,
			@PathVariable("productid") int productid) {
		HttpHeaders headers = new HttpHeaders();
		if (authenticate(authToken, facade.getUserid(cartid))) {

			DataModel data = new DataModel();
			data.setCartid(cartid);
			data.setUserid(facade.getUserid(cartid));
			List<Product> productlist = new ArrayList<Product>();
			Product product = new Product();
			product = facade.findProductFromCart(cartid, productid);
			productlist.add(product);
			data.setProduct(productlist);

			int result = facade.deleteFromCart(cartid, productid);
			if (result == 1) {
				headers.add("message", "Your Product deleted from cart successfully");
				return new ResponseEntity<DataModel>(data, headers, HttpStatus.OK);
			} else {

				headers.add("message", "Product cannot be deleted..");
				return new ResponseEntity<DataModel>(null, headers, HttpStatus.METHOD_NOT_ALLOWED);

			}
		} else {

			headers.add("message", "User not logged in ");
			return new ResponseEntity<DataModel>(null, headers, HttpStatus.UNAUTHORIZED);
		}

	}

	/**
	 * 
	 * 
	 * @param cartid
	 * @param authToken
	 * @return
	 */

	@DeleteMapping("/cart/{cartid}")
	public ResponseEntity<DataModel> emptyCart(@PathVariable("cartid") int cartid,
			@RequestHeader(value = "authToken", defaultValue = "") String authToken) {
		HttpHeaders header = new HttpHeaders();
		DataModel data = new DataModel();
		if (authenticate(authToken, facade.getUserid(cartid))) {
			int result = facade.emptyCart(cartid);
			System.out.println(result);
			if (result > 0) {
				header.add("message", "Your Product cart is empty");
				return new ResponseEntity<DataModel>(data, header, HttpStatus.OK);
			} else {
				header.add("message", "Your cart cannot be empties");
				return new ResponseEntity<DataModel>(header, HttpStatus.NOT_ACCEPTABLE);

			}
		}

		else {
			header.add("message", "User not logged in");
			return new ResponseEntity<DataModel>(header, HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 
	 * @param authToken
	 * @param userid
	 * @return Method to authenticate user and see if match by authToken
	 */

	public boolean authenticate(String authToken, String userid) {

		String url = "http://192.168.43.163:8080/checklogin";
		HttpHeaders headers = new HttpHeaders();
		headers.set("authToken", authToken);

		HttpEntity entity = new HttpEntity(headers);
		RestTemplate rst = new RestTemplate();
		try {
			ResponseEntity<String> resp = rst.exchange(url, HttpMethod.GET, entity, String.class);

			if (resp.getStatusCodeValue() != 400) {
				JSONObject jo = new JSONObject(resp.getBody());
				int user_id = jo.getInt("id");
				if (userid.equals(user_id + ""))
					return true;
			}
		} catch (Exception e) {
			return false;
			// TODO: handle exception
		}
		return false;

	}

}
