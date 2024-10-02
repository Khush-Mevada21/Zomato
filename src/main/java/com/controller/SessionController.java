package com.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.CustomerAddressEntity;
import com.entity.CustomerEntity;
import com.entity.LoginEntity;
import com.entity.RestaurantEntity;
import com.entity.UpdatePasswordEntity;
import com.repository.CustomerRepository;
import com.repository.RestaurantRepository;
import com.service.EmailService;
import com.repository.CustomerAddressRepository;

@RestController
public class SessionController {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerAddressRepository customerAddressRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	EmailService emailService;
	
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
			if(customer.isEmpty()){
				return "Invalid Login Credentials !";
			}
			else{
				return "Customer Login Successful..";
			}
		}
		else if(userType.equalsIgnoreCase("restaurant"))
		{
			Optional<RestaurantEntity> restaurant = restaurantRepository.findByEmailAndPassword(email, password);
			if(restaurant.isEmpty()){
				return "Invalid Login Credentials !";
			}
			else{
				return "Restaurant Login Successful..";
			}
		}
		else {
			return "Invalid userType ! ";
		}
	}
	

	// forget password 
	@PostMapping("/forgetpassword")
	public String forgetPassword(@RequestBody LoginEntity loginEntity)
	{
		String email = loginEntity.getEmail();
		String userType = loginEntity.getUserType();
		
		if(userType.equalsIgnoreCase("customer")) 
		{
			Optional<CustomerEntity> customer = customerRepository.findByEmail(email);
			if(customer.isEmpty()) {
				return "Customer Email Not Found";
			}
			else {
				String otp = generateOtp();
				
				sendOtpEmail(email, otp);
				
				return "OTP Send to customer email..";
			}
		}
		else if(userType.equalsIgnoreCase("restaurant")) 
		{
			Optional<RestaurantEntity> customer = restaurantRepository.findByEmail(email);
			if(customer.isEmpty()) {
				return "Restaurant Email Not Found";
			}
			else {
				String otp = generateOtp();
				
				sendOtpEmail(email, otp);
				
				return "OTP Send to restaurant email..";
			}
		}
		else {
			return "Inavlid userType !";
		}
	}
	
	
	// OTP Generation
	private String generateOtp()
	{
		Random random = new Random();
		int otp = 1000 + random.nextInt(9000);
		System.out.println("Generated OTP -- Your OTP is :"+otp);
		return String.valueOf(otp);
	}

    private void sendOtpEmail(String email, String otp) 
    {
    	emailService.sendOTPMail(email, "Your OTP is : "+otp);
    	System.out.println("Sent OTP -- Your OTP 2 is :"+otp);
    }
	
    
    // Update Password
    @PostMapping("/updatepassword")
    public String updatePassword(@RequestBody UpdatePasswordEntity updatePasswordEntity)
    {
    	String email = updatePasswordEntity.getEmail();
    	String newpassword = updatePasswordEntity.getNewpassword();
    	String cpassword = updatePasswordEntity.getCpassword();
    	Integer otp = updatePasswordEntity.getOtp();
    	
    	Optional<CustomerEntity> customer = customerRepository.findByEmail(email);
    	if(customer.isEmpty()) 
    	{
    		return "Customer Email Not Found !";
    	}
    	else if(newpassword.equals(cpassword)) 
    	{
    		CustomerEntity customerEntity = customer.get();
    		
    		customerEntity.setPassword(newpassword);
    		customerRepository.save(customerEntity);
    		return "Password Updated Succesfully..";
    	}
    	else
    	{
    		return "Password doesn't match !";
    	}
    }
		
}
