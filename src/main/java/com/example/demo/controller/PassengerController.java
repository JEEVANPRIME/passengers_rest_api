package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Passengers;
import com.example.demo.service.PassengersService;

@RestController
public class PassengerController {

	@Autowired
	PassengersService service;

	@PostMapping("/passengers")
	public ResponseEntity<Object> save(@RequestBody Passengers passengers) {
		return service.save(passengers);
	}

	@GetMapping("/passengers/pnrno/{pnrno}")
	public ResponseEntity<Object> findById(@PathVariable String pnrno) {
		return service.findById(pnrno);
	}

	@GetMapping("/passengers/seatno/{seatno}")
	public ResponseEntity<Object> findBySeatNo(@PathVariable int seatno) {
		return service.findBySeatNo(seatno);
	}

	@GetMapping("/passengers")
	public ResponseEntity<Object> findByRoute(@RequestParam String fromplace,@RequestParam String destinationplace){
		return service.findByRoute(fromplace,destinationplace); 
	}
	
	@DeleteMapping("/passengers")
	public ResponseEntity<Object> deleteById(@RequestParam String pnrno){
		return service.deletById(pnrno); 
	}
	
	@PutMapping("/passengers")
	public ResponseEntity<Object> updatePassengers(@RequestBody Passengers passengers){
		return service.updatePassengers(passengers); 
	}
	
	@PatchMapping("/passengers")
	public ResponseEntity<Object> updateDetails(@RequestParam String pnrno,@RequestBody Passengers passengers){
		return service.updateDetails(pnrno,passengers);  
	}
}
