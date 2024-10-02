package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.RestaurantEntity;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {

	List<RestaurantEntity> findByActive(Integer active);
	
	Optional<RestaurantEntity> findByPincodeAndActive(Integer pincode, Integer active);

	Optional<RestaurantEntity> findByEmailAndPassword(String email, String password);
}
