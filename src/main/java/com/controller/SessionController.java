package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.CustomerAddressEntity;
import com.entity.CustomerEntity;
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
		List<RestaurantEntity> restaurant = restaurantRepository.findAll();
		
		return restaurant;
	}
	
	@GetMapping("/getrestaurant/{pincode}")
	public RestaurantEntity getRestaurantByPincode(@PathVariable("pincode") Integer pincode)
	{
		Optional<RestaurantEntity> op = restaurantRepository.findByPincode(pincode);
		if(op.isEmpty())
		{
			return null;
		}
		else
		{
			RestaurantEntity restaurantEntity = op.get();
			return restaurantEntity;
		}
	}
	
	
}
