package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Customer;
import com.example.demo.Custrepo;
import com.example.demo.dto.Customerdto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class Servicess {
	
	@Autowired
	Custrepo custrepo;
	@Autowired
	PasswordEncoder passwordencoder;
	
	
	
	public String registercustomer(Customerdto cust) {
		
	   Customer customer=new Customer();
	   customer.setName(cust.getName());
	   customer.setEmail(cust.getEmail());
	   
	   customer.setRoles("ROLE_USER");
		
	customer.setPassword(passwordencoder.encode(cust.getPassword()));
		custrepo.save(customer);
		return "scuccesfulladded";
		
		
	}
	
	
	
	

}
