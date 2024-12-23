package com.artsem.api.driverservice.util;

import com.artsem.api.driverservice.model.Driver;
import com.artsem.api.driverservice.model.dto.request.DriverRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverStatusUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.responce.DriverAndCarResponseDto;
import com.artsem.api.driverservice.model.dto.responce.DriverResponseDto;
import com.artsem.api.driverservice.model.dto.responce.ListResponseDto;

import java.util.List;

public class DriverServiceImplTestUtil {
    public static final Long FIRST_ID = 1L;
    public static final Long SECOND_ID = 2L;
    public static final Long NON_EXISTING_ID = 100L;
    public static final Long NON_EXISTING_ID_2 = 99L;
    public static final String FIRST_EMAIL = "aaa1@aa.com";
    public static final String SECOND_EMAIL = "bbb2@bb.com";
    public static final String FIRST_FIRSTNAME = "aaa1";
    public static final String SECOND_FIRSTNAME = "bbb2";
    public static final String FIRST_SURNAME = "ccc1";
    public static final String SURNAME_LIKE = "ccc";
    public static final String SECOND_SURNAME = "ссс2";
    public static final String UPDATED_SURNAME = "UpdatedSurname";
    public static final String UPDATED_EMAIL = "updated.email@example.com";
    public static final String UPDATED_FIRSTNAME = "UpdatedFirstname";
    public static final String CREATE_INVALID_JSON = "{ \"email\": \"test@example.com\" }";
    public static final String INVALID_JSON_MESSAGE = "{firstname=First name is required., surname=Surname is required.}";
    public static final Long THIRD_ID = 3L;
    public static final String THIRD_EMAIL = "ddd3@bb.com";
    public static final String THIRD_FIRSTNAME = "ddd3";
    public static final String THIRD_SURNAME = "ddd3";
    public static final String PAGE_SIZE_2 = "2";
    public static final String PAGE_NUMBER = "0";
    public static final String DRIVERS_BASE_URL = "/api/v1/drivers";
    public static final String DRIVER_BY_ID_URL = "/api/v1/drivers/{id}";
    public static final String DRIVER_BY_IDS_URL = "/api/v1/drivers/by-ids";
    public static final String PAGE_SIZE_5 = "5";

    public static Driver getFirstDriver() {
        return Driver.builder()
                .id(FIRST_ID)
                .isFree(false)
                .car(null)
                .email(FIRST_EMAIL)
                .firstname(FIRST_FIRSTNAME)
                .surname(FIRST_SURNAME)
                .build();
    }

    public static Driver getFirstDriverNoID() {
        return Driver.builder()
                .email(FIRST_EMAIL)
                .isFree(false)
                .car(null)
                .firstname(FIRST_FIRSTNAME)
                .surname(FIRST_SURNAME)
                .build();
    }

    public static Driver getSecondDriver() {
        return Driver.builder()
                .id(SECOND_ID)
                .isFree(false)
                .email(SECOND_EMAIL)
                .firstname(SECOND_FIRSTNAME)
                .surname(SECOND_SURNAME)
                .build();
    }

    public static Driver getSecondDriverNoID() {
        return Driver.builder()
                .email(SECOND_EMAIL)
                .isFree(false)
                .firstname(SECOND_FIRSTNAME)
                .surname(SECOND_SURNAME)
                .build();
    }

    public static Driver getThirdDriver() {
        return Driver.builder()
                .id(THIRD_ID)
                .isFree(true)
                .email(THIRD_EMAIL)
                .firstname(THIRD_FIRSTNAME)
                .surname(THIRD_SURNAME)
                .build();
    }

    public static Driver getThirdDriverNoID() {
        return Driver.builder()
                .email(THIRD_EMAIL)
                .isFree(true)
                .firstname(THIRD_FIRSTNAME)
                .surname(THIRD_SURNAME)
                .build();
    }

    public static List<Driver> getDriverList() {
        return List.of(getFirstDriver(), getSecondDriver());
    }

    public static DriverRequestDto getFirstDriverRequestDto() {
        return DriverRequestDto.builder()
                .email(FIRST_EMAIL)
                .firstname(FIRST_FIRSTNAME)
                .surname(FIRST_SURNAME)
                .build();
    }

    public static DriverUpdateRequestDto getUpdateDriverRequestDto() {
        return DriverUpdateRequestDto.builder()
                .email(UPDATED_EMAIL)
                .firstname(UPDATED_FIRSTNAME)
                .surname(UPDATED_SURNAME)
                .build();
    }

    public static DriverResponseDto getUpdatedDriverResponseDto() {
        return DriverResponseDto.builder()
                .id(FIRST_ID)
                .email(UPDATED_EMAIL)
                .firstname(UPDATED_FIRSTNAME)
                .surname(UPDATED_SURNAME)
                .build();
    }

    public static DriverResponseDto getFirstDriverResponseDto() {
        return DriverResponseDto.builder()
                .id(FIRST_ID)
                .email(FIRST_EMAIL)
                .firstname(FIRST_FIRSTNAME)
                .surname(FIRST_SURNAME)
                .build();
    }

    public static DriverResponseDto getSecondDriverResponseDto() {
        return DriverResponseDto.builder()
                .id(SECOND_ID)
                .email(SECOND_EMAIL)
                .firstname(SECOND_FIRSTNAME)
                .surname(SECOND_SURNAME)
                .build();
    }

    public static ListResponseDto<DriverResponseDto> getDriverResponseDtoList() {
        return ListResponseDto.<DriverResponseDto>builder()
                .list(List.of(getFirstDriverResponseDto(), getSecondDriverResponseDto()))
                .size(2)
                .build();
    }

    public static DriverAndCarResponseDto getDriverWithoutCarResponseDto() {
        return DriverAndCarResponseDto.builder()
                .email(FIRST_EMAIL)
                .firstname(FIRST_FIRSTNAME)
                .surname(FIRST_SURNAME)
                .id(FIRST_ID)
                .build();
    }

    public static DriverAndCarResponseDto getDriverAndCarResponseDto() {
        return DriverAndCarResponseDto.builder()
                .email(FIRST_EMAIL)
                .firstname(FIRST_FIRSTNAME)
                .surname(FIRST_SURNAME)
                .car(CarServiceImplTestUtil.getFirstCarResponseDto())
                .id(FIRST_ID)
                .build();
    }

    public static DriverStatusUpdateRequestDto getStatusUpdateRequestDto() {
        return DriverStatusUpdateRequestDto.builder()
                .isFree(true)
                .build();
    }
}
