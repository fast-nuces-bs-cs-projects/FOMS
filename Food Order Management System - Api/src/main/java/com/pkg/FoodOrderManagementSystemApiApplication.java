package com.pkg;

import com.pkg.Controller.Con_User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class FoodOrderManagementSystemApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderManagementSystemApiApplication.class, args);
	}


	/*@GetMapping
	public List<String> index(){
		ArrayList<String> info = new ArrayList<String>();
		info.add("Food Order Management System - API");
		info.add("Created by Rohan Farooqui");
		info.add("For more visit: http://rohanfarooqui.github.io/");
		return info;
	}*/






}
