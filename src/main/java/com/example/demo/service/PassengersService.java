package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
		if (existPassenger.isEmpty()) {
			String pnr = generator.pnrGenerate(passengers);
			passengers.setPnrno(pnr);
			repo.save(passengers);
			map.put("Seat is booked for passenger: ", passengers);
			return new ResponseEntity<Object>(map, HttpStatus.CREATED);
		} else {
			map.put("Seat is already booked with seat no: ", passengers.getSeatNO());

			return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST); 

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
		List<Passengers> list = repo.findByFromPlaceAndDestinationPlace(fromplace, destinationplace);
		if (list.isEmpty()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "not data found");
			return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
		} else {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Passengers travelling from " + fromplace + " to destination " + destinationplace + " are: ", list);
			return new ResponseEntity<Object>(map, HttpStatus.FOUND);
		}
	}

	public ResponseEntity<Object> deletById(String pnrno) {
		repo.deleteById(pnrno);
		return new ResponseEntity<Object>("passenger details deleted with pnrno: " + pnrno, HttpStatus.GONE);
	}

	public ResponseEntity<Object> updatePassengers(Passengers passengers) {
		Optional<Passengers> existPassenger = repo.findBySeatNO(passengers.getSeatNO());
		Map<String, Object> map = new HashMap<String, Object>();
		if (existPassenger.isEmpty()) {
			String pnr = generator.pnrGenerate(passengers);
			passengers.setPnrno(pnr);
			repo.save(passengers);
			map.put("Seat is booked for passenger: ", passengers);
			return new ResponseEntity<Object>(map, HttpStatus.CREATED);
		} else {
			map.put("Seat is already booked with seat no: ", passengers.getSeatNO());

			return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST); 

		}
	}

	public ResponseEntity<Object> updateDetails(String pnrno, Passengers passengers) {
		Optional<Passengers> foundExist = repo.findById(pnrno);
		if (foundExist.isPresent()) {
			Passengers existingPassenger = foundExist.get();
			Map<String, Object> map = new HashMap<String, Object>();
			if (passengers.getDestinationPlace() != null) {
				existingPassenger.setDestinationPlace(passengers.getDestinationPlace());
			} else if (passengers.getFromPlace() != null) {
				existingPassenger.setFromPlace(passengers.getFromPlace());
			} else if (passengers.getName() != null) {
				existingPassenger.setName(passengers.getName());
			} else if (passengers.getPhNo() != 0) {
				existingPassenger.setPhNo(passengers.getPhNo());
			} else if (passengers.getSeatNO() != 0) {
				existingPassenger.setSeatNO(passengers.getSeatNO());
			}

			repo.save(existingPassenger);

			map.put("Before update: ", foundExist);  
			map.put("After update: ", existingPassenger);

			return new ResponseEntity<Object>(map, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Data not found and updated", HttpStatus.NOT_FOUND);
		}
	}

}
