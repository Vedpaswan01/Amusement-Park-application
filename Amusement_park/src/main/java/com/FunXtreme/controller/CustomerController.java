package com.FunXtreme.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.FunXtreme.exception.ActivityException;
import com.FunXtreme.exception.CustomerException;
import com.FunXtreme.exception.LoginException;
import com.FunXtreme.model.Activity;
import com.FunXtreme.model.Customer;
import com.FunXtreme.services.CustomerService;

//import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;


	public static boolean isLoggedin = false;
	
	@PostMapping("/registerCustomer")
	public ResponseEntity<Customer> registerCustomer(@Valid @RequestBody Customer customer) throws CustomerException {
		return new ResponseEntity<>(customerService.registerCustomer(customer), HttpStatus.CREATED);
	}
	
	@PutMapping("/updateCustomerById/{id}")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer) throws CustomerException {
		if (isLoggedin) {
			Customer updatedCustomer = customerService.updateCustomer(customer);

			return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);

		} else {

			throw new CustomerException("Please Login First");
		}
	}
	
	@DeleteMapping("/deleteCustomerById/{id}")
	public ResponseEntity<String> deleteCustomer(@Valid @PathVariable("id") Integer id) throws CustomerException {
		return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
	}

	@GetMapping("/viewAllActivity")
	public ResponseEntity<List<Activity>> viewAllActivities() throws ActivityException {
		return new ResponseEntity<>(customerService.getAllActivities(), HttpStatus.OK);
	}
	
	@GetMapping("/getCustomerByEmail/{email}")
	public ResponseEntity<Customer> getCustomerByEmail(@Valid @PathVariable("email") String email) throws CustomerException {
		return new ResponseEntity<>(customerService.getCustomerById(email), HttpStatus.OK);
	}
}
