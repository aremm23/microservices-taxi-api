package com.artsem.api.driverservice.util;

import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.model.CarCategory;
import com.artsem.api.driverservice.model.dto.request.CarRequestDto;
import com.artsem.api.driverservice.model.dto.request.CarUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.responce.CarResponseDto;
import com.artsem.api.driverservice.model.dto.responce.ListResponseDto;

import java.util.List;

public class CarServiceImplTestUtil {

    public static final Long FIRST_ID = 1L;
    public static final Long SECOND_ID = 2L;
    public static final Long NON_EXISTING_ID = 100L;
    public static final String FIRST_MODEL = "Bmw M5 F90 rest";
    public static final String SECOND_MODEL = " Bmw 340i G20";
    public static final String UPDATED_MODEL = "Volkswagen passat B5";
    public static final String FIRST_LICENSE_PLATE = "5555MM-5";
    public static final String SECOND_LICENSE_PLATE = "3333MM-3";
    public static final String UPDATED_LICENSE_PLATE = "1111II-1";
    public static final CarCategory FIRST_CATEGORY = CarCategory.BUSINESS;
    public static final CarCategory SECOND_CATEGORY = CarCategory.COMFORT;
    public static final CarCategory UPDATED_CATEGORY = CarCategory.ECONOM;
    public static final String PAGE_SIZE_2 = "2";
    public static final String PAGE_NUMBER = "0";
    public static final String CARS_BASE_URL = "/api/v1/cars";
    public static final String CAR_BY_ID_URL = "/api/v1/cars/{id}";
    public static final String CAR_BY_IDS_URL = "/api/v1/cars/by-ids";
    public static final String PAGE_SIZE_5 = "5";
    public static final String INVALID_JSON = """
                    {
                        "model": "Ford F150",
                        "licensePlate": "invalid",
                        "carCategory": "BUSINESS"
                    }
                """;
    public static final String MODEL_LIKE = "Bmw";
    public static final String THIRD_MODEL = "Volvo V90 II";
    public static final String THIRD_LICENSE_PLATE = "8888OO-7";
    public static final CarCategory THIRD_CATEGORY = CarCategory.BUSINESS;

    public static Car getFirstCar() {
        return Car.builder()
                .id(FIRST_ID)
                .model(FIRST_MODEL)
                .licensePlate(FIRST_LICENSE_PLATE)
                .carCategory(FIRST_CATEGORY)
                .build();
    }

    public static Car getFirstCarNoID() {
        return Car.builder()
                .model(FIRST_MODEL)
                .licensePlate(FIRST_LICENSE_PLATE)
                .carCategory(FIRST_CATEGORY)
                .build();
    }

    public static Car getSecondCar() {
        return Car.builder()
                .id(SECOND_ID)
                .model(SECOND_MODEL)
                .licensePlate(SECOND_LICENSE_PLATE)
                .carCategory(SECOND_CATEGORY)
                .build();
    }

    public static CarRequestDto getFirstCarRequestDto() {
        return CarRequestDto.builder()
                .model(FIRST_MODEL)
                .licensePlate(FIRST_LICENSE_PLATE)
                .carCategory(FIRST_CATEGORY)
                .build();
    }

    public static CarUpdateRequestDto getUpdateCarRequestDto() {
        return CarUpdateRequestDto.builder()
                .model(UPDATED_MODEL)
                .licensePlate(UPDATED_LICENSE_PLATE)
                .carCategory(UPDATED_CATEGORY)
                .build();
    }

    public static CarResponseDto getFirstCarResponseDto() {
        return CarResponseDto.builder()
                .id(FIRST_ID)
                .model(FIRST_MODEL)
                .licensePlate(FIRST_LICENSE_PLATE)
                .carCategory(FIRST_CATEGORY)
                .build();
    }

    public static CarResponseDto getUpdatedCarResponseDto() {
        return CarResponseDto.builder()
                .id(FIRST_ID)
                .model(UPDATED_MODEL)
                .licensePlate(UPDATED_LICENSE_PLATE)
                .carCategory(UPDATED_CATEGORY)
                .build();
    }

    public static List<Car> getCarList() {
        return List.of(getFirstCar(), getSecondCar());
    }

    public static ListResponseDto<CarResponseDto> getCarResponseDtoList() {
        return ListResponseDto.<CarResponseDto>builder()
                .list(List.of(getFirstCarResponseDto(), getSecondCarResponseDto()))
                .size(1)
                .build();
    }

    public static CarResponseDto getSecondCarResponseDto() {
        return CarResponseDto.builder()
                .id(SECOND_ID)
                .model(SECOND_MODEL)
                .licensePlate(SECOND_LICENSE_PLATE)
                .carCategory(SECOND_CATEGORY)
                .build();
    }

}
