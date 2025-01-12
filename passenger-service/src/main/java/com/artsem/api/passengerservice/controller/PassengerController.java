package com.artsem.api.passengerservice.controller;

import com.artsem.api.passengerservice.controller.api.PassengerApi;
import com.artsem.api.passengerservice.filter.PassengerFilter;
import com.artsem.api.passengerservice.model.dto.request.PassengerRequestDto;
import com.artsem.api.passengerservice.model.dto.request.PassengerUpdateRequestDto;
import com.artsem.api.passengerservice.model.dto.response.ListResponseDto;
import com.artsem.api.passengerservice.model.dto.response.PassengerEmailResponseDto;
import com.artsem.api.passengerservice.model.dto.response.PassengerResponseDto;
import com.artsem.api.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController implements PassengerApi {

    private final PassengerService passengerService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<PassengerResponseDto>> getList(
            @ModelAttribute
            PassengerFilter filter,
            Pageable pageable
    ) {
        Page<PassengerResponseDto> passengerResponseDtos = passengerService.getList(filter, pageable);
        return ResponseEntity.ok(new PagedModel<>(passengerResponseDtos));
    }

    @PreAuthorize("""
            (hasRole('ROLE_PASSENGER') &&
            @userAccessValidator.isUserAuthorizedForId(#id, authentication)) ||
            hasAnyRole('ROLE_ADMIN')
            """)
    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> getOne(@PathVariable Long id) {
        PassengerResponseDto passenger = passengerService.getOne(id);
        return ResponseEntity.ok(passenger);
    }

    @PreAuthorize("""
            (hasRole('ROLE_PASSENGER') &&
            @userAccessValidator.isUserAuthorizedForId(#id, authentication)) ||
            @userAccessValidator.isClientAuthorizedForRequest('notification-service', authentication) ||
            hasRole('ROLE_ADMIN')
            """)
    @GetMapping("/{id}/email")
    public ResponseEntity<PassengerEmailResponseDto> getEmail(@PathVariable Long id) {
        PassengerEmailResponseDto passenger = passengerService.getEmail(id);
        return ResponseEntity.ok(passenger);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/by-ids")
    public ResponseEntity<ListResponseDto<PassengerResponseDto>> getMany(@RequestParam List<Long> ids) {
        ListResponseDto<PassengerResponseDto> passengers = passengerService.getMany(ids);
        return ResponseEntity.ok(passengers);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<PassengerResponseDto> create(
            @Valid
            @RequestBody
            PassengerRequestDto passengerDto
    ) {
        PassengerResponseDto createdPassenger = passengerService.create(passengerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPassenger);
    }

    @PreAuthorize("""
            (hasRole('ROLE_PASSENGER') &&
            @userAccessValidator.isUserAuthorizedForId(#id, authentication)) ||
            hasRole('ROLE_ADMIN')
            """)
    @PatchMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> patch(
            @PathVariable
            Long id,
            @Valid
            @RequestBody
            PassengerUpdateRequestDto passengerDto
    ) {
        PassengerResponseDto updatedPassenger = passengerService.patch(id, passengerDto);
        return ResponseEntity.ok(updatedPassenger);
    }

    @PreAuthorize("""
            (hasRole('ROLE_PASSENGER') &&
            @userAccessValidator.isUserAuthorizedForId(#id, authentication)) ||
            hasRole('ROLE_ADMIN')
            """)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        passengerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteMany(@RequestParam List<Long> ids) {
        passengerService.deleteMany(ids);
        return ResponseEntity.noContent().build();
    }
}