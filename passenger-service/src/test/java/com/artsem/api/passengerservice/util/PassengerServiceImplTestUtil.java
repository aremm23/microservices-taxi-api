package com.artsem.api.passengerservice.util;

import com.artsem.api.passengerservice.model.Passenger;
import com.artsem.api.passengerservice.model.dto.request.PassengerRequestDto;
import com.artsem.api.passengerservice.model.dto.request.PassengerUpdateRequestDto;
import com.artsem.api.passengerservice.model.dto.response.ListResponseDto;
import com.artsem.api.passengerservice.model.dto.response.PassengerResponseDto;

import java.util.List;

public class PassengerServiceImplTestUtil {
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

    public static Passenger getFirstPassenger() {
        return Passenger.builder()
                .id(FIRST_ID)
                .email(FIRST_EMAIL)
                .firstname(FIRST_FIRSTNAME)
                .surname(FIRST_SURNAME)
                .build();
    }

    public static Passenger getFirstPassengerNoID() {
        return Passenger.builder()
                .email(FIRST_EMAIL)
                .firstname(FIRST_FIRSTNAME)
                .surname(FIRST_SURNAME)
                .build();
    }

    public static Passenger getSecondPassenger() {
        return Passenger.builder()
                .id(SECOND_ID)
                .email(SECOND_EMAIL)
                .firstname(SECOND_FIRSTNAME)
                .surname(SECOND_SURNAME)
                .build();
    }

    public static Passenger getSecondPassengerNoID() {
        return Passenger.builder()
                .email(SECOND_EMAIL)
                .firstname(SECOND_FIRSTNAME)
                .surname(SECOND_SURNAME)
                .build();
    }

    public static Passenger getThirdPassenger() {
        return Passenger.builder()
                .id(THIRD_ID)
                .email(THIRD_EMAIL)
                .firstname(THIRD_FIRSTNAME)
                .surname(THIRD_SURNAME)
                .build();
    }

    public static Passenger getThirdPassengerNoID() {
        return Passenger.builder()
                .email(THIRD_EMAIL)
                .firstname(THIRD_FIRSTNAME)
                .surname(THIRD_SURNAME)
                .build();
    }

    public static List<Passenger> getPassengerList() {
        return List.of(getFirstPassenger(), getSecondPassenger());
    }

    public static PassengerRequestDto getFirstPassengerRequestDto() {
        return PassengerRequestDto.builder()
                .email(FIRST_EMAIL)
                .firstname(FIRST_FIRSTNAME)
                .surname(FIRST_SURNAME)
                .build();
    }

    public static PassengerUpdateRequestDto getUpdatePassengerRequestDto() {
        return PassengerUpdateRequestDto.builder()
                .email(UPDATED_EMAIL)
                .firstname(UPDATED_FIRSTNAME)
                .surname(UPDATED_SURNAME)
                .build();
    }

    public static PassengerResponseDto getUpdatedPassengerResponseDto() {
        return PassengerResponseDto.builder()
                .id(FIRST_ID)
                .email(UPDATED_EMAIL)
                .firstname(UPDATED_FIRSTNAME)
                .surname(UPDATED_SURNAME)
                .build();
    }

    public static PassengerResponseDto getFirstPassengerResponseDto() {
        return PassengerResponseDto.builder()
                .id(FIRST_ID)
                .email(FIRST_EMAIL)
                .firstname(FIRST_FIRSTNAME)
                .surname(FIRST_SURNAME)
                .build();
    }

    public static PassengerResponseDto getSecondPassengerResponseDto() {
        return PassengerResponseDto.builder()
                .id(SECOND_ID)
                .email(SECOND_EMAIL)
                .firstname(SECOND_FIRSTNAME)
                .surname(SECOND_SURNAME)
                .build();
    }

    public static ListResponseDto<PassengerResponseDto> getPassengerResponseDtoList() {
        return ListResponseDto.<PassengerResponseDto>builder()
                .list(List.of(getFirstPassengerResponseDto(), getSecondPassengerResponseDto()))
                .size(2)
                .build();
    }
}
