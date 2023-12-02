package com.hotel.pethotel.controller;

import com.hotel.pethotel.dto.AnimalDto;
import com.hotel.pethotel.model.AnimalModel;
import com.hotel.pethotel.service.AnimalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AnimalController {

    private final AnimalService animalService;
//    @PostMapping("/addAnimals")
//    public ResponseEntity<AnimalDto> addAnimal(@RequestBody AnimalDto animalDto) {
//        try {
//            AnimalDto addAnimal = animalService.addAnimal(animalDto);
//            return ResponseEntity.ok(addAnimal);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//
//    }
}