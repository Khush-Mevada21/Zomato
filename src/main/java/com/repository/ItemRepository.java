package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer>{

	

}
