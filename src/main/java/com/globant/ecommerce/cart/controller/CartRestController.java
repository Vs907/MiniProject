package com.globant.ecommerce.cart.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
	public Response getAll(@PathVariable("userid") String userid,
			@RequestHeader(value = "authToken", defaultValue = "") String authToken) {
		Response response = new Response();
		if (authenticate(authToken, userid)) {
			DataModel datamodel = new DataModel();
			int cartid = facade.getCartid(userid);
			if (cartid == 0) {
				response.setMessage("Cart Detailed Not Found");
				response.setStatusCode("404");

			} else {

				List<Product> productlist = facade.viewAllFromCart(cartid);
				datamodel.setCartid(cartid);
				datamodel.setUserid(userid);
				datamodel.setProduct(productlist);
				response.setMessage("Cart Detailed Fetched Successful");
				response.setStatusCode("200");
				response.setData(datamodel);
			}

		} else {
			response.setMessage("User Not Logged In");
			response.setStatusCode("401");
		}
		return response;
	}

	/**
	 * 
	 * @param userid
	 * @param authToken
	 * @param product
	 * @return response
	 */
	@PostMapping("/cart/{userid}")
	public Response addToCart(@PathVariable("userid") String userid,
			@RequestHeader(value = "authToken", defaultValue = "") String authToken, @RequestBody Product product) {

		Response response = new Response();
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
			cartModel.setUserid(userid);
			cartModel.setProduct(product);
			int res = facade.addToCart(cartModel);
			if(res == 1) {
			response.setMessage("Your Product added to cart Successfully");
			response.setStatusCode("200");
			DataModel data = new DataModel();
			data.setCartid(cartid);
			data.setUserid(userid);
			List<Product> productlist = new ArrayList<Product>();
			productlist.add(product);
			data.setProduct(productlist);
			response.setData(data);
			}
			else {
				response.setMessage("Product already added to cart");
				response.setStatusCode("401");
			}
		} else {
			response.setMessage("User Not Logged In");
			response.setStatusCode("401");

		}

		return response;
	}

	/**
	 * 
	 * @param authToken
	 * @param cartid
	 * @param productid
	 * @return response
	 */

	@DeleteMapping("/cart/{cartid}/{productid}")
	public Response deleteFromCart(@RequestHeader(value = "authToken", defaultValue = "") String authToken,
			@PathVariable("cartid") int cartid, @PathVariable("productid") int productid) {
		Response response = new Response();
		if (authenticate(authToken, facade.getUserid(cartid))) {
			response.setMessage("Your Product deleted from cart successfully");
			response.setStatusCode("200");
			DataModel data = new DataModel();
			data.setCartid(cartid);
			data.setUserid(facade.getUserid(cartid));
			List<Product> productlist = new ArrayList<Product>();
			Product product = new Product();
			product = facade.findProductFromCart(cartid, productid);
			productlist.add(product);
			data.setProduct(productlist);
			response.setData(data);
			int result = facade.deleteFromCart(cartid, productid);
			if (result == 1)
				return response;
			else {
				response.setMessage("Product cannot be deleted..");
				response.setStatusCode("404");
				return response;
			}
		} else {
			response.setMessage("User not logged in ");
			response.setStatusCode("404");
			return response;
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
	public Response emptyCart(@PathVariable("cartid") int cartid,
			@RequestHeader(value = "authToken", defaultValue = "") String authToken) {

		Response response = new Response();
		if (authenticate(authToken, facade.getUserid(cartid))) {
			response.setMessage("Your Product cart is empty");
			response.setStatusCode("200");
			DataModel data = new DataModel();
			response.setData(data);
			if (facade.emptyCart(cartid) == 1) {
				return response;
			} else {
				response.setMessage("Your cart cannot be emptied");
				response.setStatusCode("404");
				return response;
			}
		}

		else {
			response.setMessage("User not logged in ");
			response.setStatusCode("404");
			return response;
		}

	}
	
	
	
	/**
	 * 
	 * @param authToken
	 * @param userid
	 * @return
	 * Method to authenticate user and see if match by authToken	
	 */
	

	public boolean authenticate(String authToken, String userid) {
//
//		String url = "http://192.168.43.163/checklogin";
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("authToken", authToken);
//		HttpEntity entity = new HttpEntity(headers);
//		RestTemplate rst = new RestTemplate();
//		ResponseEntity<String> resp = rst.exchange(url, HttpMethod.GET, entity, String.class);
//		JSONObject jo = new JSONObject(resp.getBody());
//		String statusCode = jo.getString("statusCode");
//
//		if (statusCode.equals("200")) {
//			JSONObject json = jo.getJSONObject("data");
//			int user_id = json.getInt("id");
//			if (userid.equals(user_id + "")) {
//				return true;
//			} else
//				return false;
//		} else
//			return false;
		return true;
	}

}
