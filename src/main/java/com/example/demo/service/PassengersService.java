package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Passengers;
import com.example.demo.repository.PassengersRepo;

@Service
public class PassengersService {

	@Autowired
	PassengersRepo repo;

	@Autowired
	PnrGenerator generator;

	public ResponseEntity<Object> save(Passengers passengers) {
		Optional<Passengers> existPassenger = repo.findBySeatNO(passengers.getSeatNO());
		Map<String, Object> map = new HashMap<String, Object>();
		if (existPassenger != null) {
			map.put("Seat is already booked with seat no: ", passengers.getSeatNO());

			return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
		} else {
			String pnr = generator.pnrGenerate(passengers);
			passengers.setPnrno(pnr);
			repo.save(passengers);
			map.put("Seat is booked for passenger: ", passengers);
			return new ResponseEntity<Object>(map, HttpStatus.CREATED);
		}
	}

	public ResponseEntity<Object> findBySeatNo(int seatno) {
		Optional<Passengers> foundseatNo = repo.findBySeatNO(seatno);
		if (foundseatNo.isPresent()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("passenger data found with seat no: ", seatno);
			map.put("Data", foundseatNo);
			return new ResponseEntity<Object>(map, HttpStatus.FOUND);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Data not found with seat no: ", seatno);
			return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Object> findById(String pnrno) {
		Optional<Passengers> foundByPnr = repo.findById(pnrno);
		if (foundByPnr.isPresent()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("passenger data found with pnr no: ", pnrno);
			map.put("Data", foundByPnr);
			return new ResponseEntity<Object>(map, HttpStatus.FOUND);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Data not found with pnr no: ", pnrno);
			return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Object> findByRoute(String fromplace, String destinationplace) {
		List<Passengers> list=repo.findByFromPlaceAndDestinationPlace(fromplace,destinationplace); 
		if(list.isEmpty()) {
			Map<String, Object>map =new HashMap<String, Object>();
			map.put("message", "not data found");
			return new ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);
		}else {

			Map<String, Object> map=new HashMap<String, Object>();
			map.put("Passengers travelling from "+fromplace+" to destination "+destinationplace+" are: ", list);
			return new ResponseEntity<Object>(map,HttpStatus.FOUND); 
		}
	}

	

}