package com.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "menu")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer menuId;
	Integer active;
	String title;
	String menuImagePath;

	@ManyToOne
    @JoinColumn(name = "restaurantId")
    RestaurantEntity restaurant_menu;
	
	@OneToMany(mappedBy = "menu_item")
	List<ItemEntity> item;
	
	
}
