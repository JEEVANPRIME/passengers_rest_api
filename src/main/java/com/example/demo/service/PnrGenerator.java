package com.example.demo.service;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.example.demo.dto.Passengers;

@Component
public class PnrGenerator {

	public String pnrGenerate(Passengers passengers) {
		int random = new Random().nextInt(1000, 10000); 
		String from=passengers.getFromPlace().substring(0, 2); 
		String destination=passengers.getDestinationPlace().substring(0, 2);
		return from+""+destination+""+random; 
	}

	
}
