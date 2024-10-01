package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.CustomerAddressEntity;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddressEntity, Integer>{

	Optional<CustomerAddressEntity> findByCustomer_CustomerId(Integer customerId);
	
}
