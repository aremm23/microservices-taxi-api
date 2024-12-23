package com.artsem.api.driverservice.controller;

import com.artsem.api.driverservice.controller.api.CarApi;
import com.artsem.api.driverservice.filter.CarFilter;
import com.artsem.api.driverservice.model.dto.request.CarRequestDto;
import com.artsem.api.driverservice.model.dto.request.CarUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.response.CarResponseDto;
import com.artsem.api.driverservice.model.dto.response.ListResponseDto;
import com.artsem.api.driverservice.service.CarService;
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
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController implements CarApi {

    private final CarService carService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<PagedModel<CarResponseDto>> getList(@ModelAttribute CarFilter filter, Pageable pageable) {
        Page<CarResponseDto> carResponseDtos = carService.getList(filter, pageable);
        return ResponseEntity.ok(new PagedModel<>(carResponseDtos));
    }

    @PreAuthorize("hasAnyRole('ROLE_DRIVER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> getOne(@PathVariable Long id) {
        CarResponseDto car = carService.getOne(id);
        return ResponseEntity.ok(car);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/by-ids")
    public ResponseEntity<ListResponseDto<CarResponseDto>> getMany(@RequestParam List<Long> ids) {
        ListResponseDto<CarResponseDto> cars = carService.getMany(ids);
        return ResponseEntity.ok(cars);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CarResponseDto> create(@Valid @RequestBody CarRequestDto carDto) {
        CarResponseDto createdCar = carService.create(carDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<CarResponseDto> patch(
            @PathVariable Long id,
            @Valid
            @RequestBody
            CarUpdateRequestDto carDto
    ) {
        CarResponseDto updatedCar = carService.patch(id, carDto);
        return ResponseEntity.ok(updatedCar);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteMany(@RequestParam List<Long> ids) {
        carService.deleteMany(ids);
        return ResponseEntity.noContent().build();
    }
}
