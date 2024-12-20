package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
}
