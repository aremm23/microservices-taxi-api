package com.artsem.api.passengerservice.controller;

import com.artsem.api.passengerservice.model.dto.PassengerRequestDto;
import com.artsem.api.passengerservice.model.dto.PassengerResponseDto;
import com.artsem.api.passengerservice.model.dto.PassengerUpdateRequestDto;
import com.artsem.api.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> getOne(@PathVariable Long id) {
        PassengerResponseDto passenger = passengerService.getOne(id);
        return ResponseEntity.ok(passenger);
    }

    @GetMapping("/by-ids")
    public ResponseEntity<List<PassengerResponseDto>> getMany(@RequestParam List<Long> ids) {
        List<PassengerResponseDto> passengers = passengerService.getMany(ids);
        return ResponseEntity.ok(passengers);
    }

    @PostMapping
    public ResponseEntity<PassengerResponseDto> create(@Valid @RequestBody PassengerRequestDto passengerDto) {
        PassengerResponseDto createdPassenger = passengerService.create(passengerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPassenger);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> patch(
            @PathVariable Long id,
            @Valid @RequestBody PassengerUpdateRequestDto passengerDto
    ) {
        PassengerResponseDto updatedPassenger = passengerService.patch(id, passengerDto);
        return ResponseEntity.ok(updatedPassenger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        passengerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMany(@RequestParam List<Long> ids) {
        passengerService.deleteMany(ids);
        return ResponseEntity.noContent().build();
    }
}
