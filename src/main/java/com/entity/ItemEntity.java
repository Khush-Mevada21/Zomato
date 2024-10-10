package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "item")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemEntity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer itemId;
	Integer active;
	String title;
	String description;
	String itemImagePath;
	Float price;
	Integer isOffer;
	Integer offerQty;
	Integer offerPercentage;
	Integer offerQtyCount;
	Integer uptoAmt;
	String uptoAmtCondition;
	
	@ManyToOne
	@JoinColumn(name = "restaurantId")
	RestaurantEntity restaurant_item;
	
	@ManyToOne
	@JoinColumn(name = "menuId")
	MenuEntity menu_item;
	
}
