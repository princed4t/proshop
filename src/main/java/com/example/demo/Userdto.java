package com.example.demo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Userdto {
	
	
	
	private String name;
	private String email;
	private String product;
	private  double proweight;
	private double prodistance;
	//private List<Consigment> listofconsigment;
	
	

}
