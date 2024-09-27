package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.CustomerAddressEntity;
import com.entity.CustomerEntity;
import com.repository.CustomerRepository;
import com.repository.CustomerAddressRepository;

@RestController
public class SessionController {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerAddressRepository customerAddressRepository;
	
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
	
	@PostMapping("/customeraddress")
	public CustomerAddressEntity customerAddress(@RequestBody CustomerAddressEntity customerAddressEntity)
	{
		customerAddressRepository.save(customerAddressEntity);
		return customerAddressEntity;
	}
}
