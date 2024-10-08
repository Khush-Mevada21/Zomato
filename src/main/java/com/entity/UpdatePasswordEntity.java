package com.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePasswordEntity {

	String email;
	String newpassword;
	String cpassword;
	String otp;
	
}
