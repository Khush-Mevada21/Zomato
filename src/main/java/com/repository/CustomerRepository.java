package com.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

	Optional<CustomerEntity> findByEmailAndPassword(String email, String password);
	
	Optional<CustomerEntity> findByEmail(String email);
}

