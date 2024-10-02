package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.CustomerAddressEntity;
import com.entity.CustomerEntity;
import com.entity.LoginEntity;
import com.entity.RestaurantEntity;
import com.repository.CustomerRepository;
import com.repository.RestaurantRepository;
import com.repository.CustomerAddressRepository;

@RestController
public class SessionController {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerAddressRepository customerAddressRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	//Customer
	
	@PostMapping("/customersignup")
	public CustomerEntity customerSignup(@RequestBody CustomerEntity customerEntity)
	{
		customerRepository.save(customerEntity);
		return customerEntity;
	}
		
	@GetMapping("/getcustomer")
	public List<CustomerEntity> getAllCustomers()
	{
		List<CustomerEntity> customer = customerRepository.findAll();
		
		return customer;
	}
	
	//Customer Address
	
	@PostMapping("/customeraddress")
	public CustomerAddressEntity customerAddress(@RequestBody CustomerAddressEntity customerAddressEntity)
	{
		customerAddressRepository.save(customerAddressEntity);
		return customerAddressEntity;
	}
	
	// get address by customerId
	
	@GetMapping("/myaddress/{customerId}")
	public String getAddressById(@PathVariable("customerId") Integer customerId)
	{
		Optional<CustomerAddressEntity> op = customerAddressRepository.findByCustomer_CustomerId(customerId);
		if(op.isEmpty())
		{
			return "customerId Not Found";
		}
		else
		{
			CustomerAddressEntity customerAddressEntity = op.get();
			return customerAddressEntity.getAddressLine();
		}
	}
	
	// Restaurant
	
	@PostMapping("/addrestaurant")
	public RestaurantEntity addRestaurant(@RequestBody RestaurantEntity restaurantEntity)
	{
		restaurantRepository.save(restaurantEntity);
		return restaurantEntity;
	}
	
	@GetMapping("/getrestaurant")
	public List<RestaurantEntity> getAllRestaurants()
	{
		//List<RestaurantEntity> restaurant = restaurantRepository.findAll(); --for restaurant details
		
		return restaurantRepository.findByActive(1);
	}
	
	
	@GetMapping("/getrestaurant/{pincode}")
	public RestaurantEntity getRestaurantByPincode(@PathVariable("pincode") Integer pincode)
	{
		Optional<RestaurantEntity> op = restaurantRepository.findByPincodeAndActive(pincode,1);
		if(op.isEmpty())
		{
			return null;
		}
		else
		{
			RestaurantEntity restaurantEntity = op.get();
			return restaurantEntity;
			//return op.get();
		}
	}
	
	// login with userType - customer or restaurant
	
	@PostMapping("/login")
	public String userLogin(@RequestBody LoginEntity loginEntity)
	{	
		String email = loginEntity.getEmail();
		String password = loginEntity.getPassword();
		String userType = loginEntity.getUserType();
		
		if(userType.equalsIgnoreCase("customer"))
		{
			Optional<CustomerEntity> customer = customerRepository.findByEmailAndPassword(email, password);
			if(customer.isEmpty())
			{
				return "Invalid Login Credentials !";
			}
			else
			{
				return "Customer Login Successful..";
			}
		}
		else if(userType.equalsIgnoreCase("restaurant"))
		{
			Optional<RestaurantEntity> restaurant = restaurantRepository.findByEmailAndPassword(email, password);
			if(restaurant.isEmpty())
			{
				return "Invalid Login Credentials !";
			}
			else
			{
				return "Restaurant Login Successful..";
			}
		}
		else {
			return "Invalid userType ! ";
		}
	}
	
}
