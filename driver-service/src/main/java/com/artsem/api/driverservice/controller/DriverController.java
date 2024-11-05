package com.artsem.api.driverservice.controller;

import com.artsem.api.driverservice.filter.DriverFilter;
import com.artsem.api.driverservice.model.dto.request.DriverRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverStatusUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.responce.DriverAndCarResponseDto;
import com.artsem.api.driverservice.model.dto.responce.DriverResponseDto;
import com.artsem.api.driverservice.model.dto.responce.ListResponseDto;
import com.artsem.api.driverservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @GetMapping
    public ResponseEntity<PagedModel<DriverResponseDto>> getList(@ModelAttribute DriverFilter filter, Pageable pageable) {
        Page<DriverResponseDto> driverResponseDtos = driverService.getList(filter, pageable);
        return ResponseEntity.ok(new PagedModel<>(driverResponseDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDto> getOne(@PathVariable Long id) {
        DriverResponseDto driver = driverService.getOne(id);
        return ResponseEntity.ok(driver);
    }

    @GetMapping("/{id}/car")
    public ResponseEntity<DriverAndCarResponseDto> getOneWithCar(@PathVariable Long id) {
        DriverAndCarResponseDto driver = driverService.getOneWithCar(id);
        return ResponseEntity.ok(driver);
    }

    @GetMapping("/by-ids")
    public ResponseEntity<ListResponseDto<DriverResponseDto>> getMany(@RequestParam List<Long> ids) {
        ListResponseDto<DriverResponseDto> drivers = driverService.getMany(ids);
        return ResponseEntity.ok(drivers);
    }

    @PostMapping
    public ResponseEntity<DriverResponseDto> create(@Valid @RequestBody DriverRequestDto driverDto) {
        DriverResponseDto createdDriver = driverService.create(driverDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDriver);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DriverResponseDto> patch(
            @PathVariable Long id,
            @Valid @RequestBody DriverUpdateRequestDto driverDto
    ) {
        DriverResponseDto updatedDriver = driverService.patch(id, driverDto);
        return ResponseEntity.ok(updatedDriver);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        driverService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMany(@RequestParam List<Long> ids) {
        driverService.deleteMany(ids);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DriverResponseDto> updateDriverStatus(
            @PathVariable Long id,
            @RequestBody DriverStatusUpdateRequestDto statusUpdateDto
    ) {
        DriverResponseDto updatedDriver = driverService.updateDriverStatus(id, statusUpdateDto);
        return ResponseEntity.ok(updatedDriver);
    }

    @PostMapping("/{driverId}/car/{carId}")
    public ResponseEntity<DriverAndCarResponseDto> assignCarToDriver(
            @PathVariable Long driverId,
            @PathVariable Long carId) {
        DriverAndCarResponseDto updatedDriver = driverService.assignCarToDriver(driverId, carId);
        return ResponseEntity.ok(updatedDriver);
    }

    @DeleteMapping("/{driverId}/car")
    public ResponseEntity<DriverAndCarResponseDto> removeCarFromDriver(@PathVariable Long driverId) {
        DriverAndCarResponseDto updatedDriver = driverService.removeCarFromDriver(driverId);
        return ResponseEntity.ok(updatedDriver);
    }
}
