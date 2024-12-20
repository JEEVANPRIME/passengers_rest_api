package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.Passengers;

public interface PassengersRepo extends JpaRepository<Passengers, String>{

	Optional<Passengers> findBySeatNO(int seatno);

	List<Passengers> findByFromPlaceAndDestinationPlace(String fromplace, String destinationplace);

	void deleteBySeatNO(int seatNo); 




}
