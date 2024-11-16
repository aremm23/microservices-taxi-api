package com.artsem.api.passengerservice.unit.service.impl;

import com.artsem.api.passengerservice.exceptions.EmptyIdsListException;
import com.artsem.api.passengerservice.exceptions.PassengerNotCreatedException;
import com.artsem.api.passengerservice.exceptions.PassengerNotFoundException;
import com.artsem.api.passengerservice.exceptions.PassengerNotUpdatedException;
import com.artsem.api.passengerservice.exceptions.PassengersNotFoundException;
import com.artsem.api.passengerservice.filter.PassengerFilter;
import com.artsem.api.passengerservice.model.Passenger;
import com.artsem.api.passengerservice.model.dto.request.PassengerRequestDto;
import com.artsem.api.passengerservice.model.dto.request.PassengerUpdateRequestDto;
import com.artsem.api.passengerservice.model.dto.response.ListResponseDto;
import com.artsem.api.passengerservice.model.dto.response.PassengerResponseDto;
import com.artsem.api.passengerservice.repository.PassengerRepository;
import com.artsem.api.passengerservice.service.impl.PassengerServiceImpl;
import com.artsem.api.passengerservice.util.PassengerServiceImplTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PassengerServiceImplTest {

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Test
    void getList_shouldHandlePaginationCorrectly() {
        PassengerFilter filter = new PassengerFilter(PassengerServiceImplTestUtil.SURNAME_LIKE);
        Pageable pageable = PageRequest.of(0, 10);
        List<Passenger> passengerList = PassengerServiceImplTestUtil.getPassengerList();
        Page<Passenger> passengerPage = new PageImpl<>(passengerList, pageable, passengerList.size());
        List<PassengerResponseDto> expectedDtoList = PassengerServiceImplTestUtil.getPassengerResponseDtoList().list();

        when(passengerRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(passengerPage);
        when(mapper.map(any(Passenger.class), eq(PassengerResponseDto.class)))
                .thenAnswer(invocation -> {
                    Passenger passenger = invocation.getArgument(0);
                    return expectedDtoList.stream()
                            .filter(dto -> dto.getId().equals(passenger.getId()))
                            .findFirst()
                            .orElse(null);
                });

        Page<PassengerResponseDto> result = passengerService.getList(filter, pageable);

        assertEquals(passengerPage.getTotalElements(), result.getTotalElements());
        assertEquals(passengerPage.getNumber(), result.getNumber());
        assertEquals(passengerPage.getSize(), result.getSize());
        assertEquals(expectedDtoList, result.getContent());
        verify(passengerRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(mapper, times(passengerList.size())).map(any(Passenger.class), eq(PassengerResponseDto.class));
    }


    @Test
    void getOne_existingPassenger_returnsPassengerResponseDto() {
        Long existingPassengerId = PassengerServiceImplTestUtil.FIRST_ID;
        Passenger existingPassenger = PassengerServiceImplTestUtil.getFirstPassenger();
        PassengerResponseDto expectedResponse = PassengerServiceImplTestUtil.getFirstPassengerResponseDto();

        when(passengerRepository.findById(existingPassengerId)).thenReturn(Optional.of(existingPassenger));
        when(mapper.map(existingPassenger, PassengerResponseDto.class)).thenReturn(expectedResponse);

        PassengerResponseDto actualResponse = passengerService.getOne(existingPassengerId);

        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    void getOne_nonExistingPassenger_throwsPassengerNotFoundException() {
        when(passengerRepository.findById(PassengerServiceImplTestUtil.FIRST_ID)).thenReturn(Optional.empty());

        assertThrows(PassengerNotFoundException.class, () -> passengerService.getOne(PassengerServiceImplTestUtil.FIRST_ID));
    }

    @Test
    void create_validPassenger_returnsPassengerResponseDto() {
        PassengerRequestDto requestDto = PassengerServiceImplTestUtil.getFirstPassengerRequestDto();
        Passenger passenger = PassengerServiceImplTestUtil.getFirstPassenger();
        PassengerResponseDto responseDto = PassengerServiceImplTestUtil.getFirstPassengerResponseDto();

        when(passengerRepository.existsByEmail(requestDto.getEmail())).thenReturn(false);
        when(mapper.map(requestDto, Passenger.class)).thenReturn(passenger);
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        when(mapper.map(passenger, PassengerResponseDto.class)).thenReturn(responseDto);

        PassengerResponseDto result = passengerService.create(requestDto);

        assertEquals(responseDto, result);
    }

    @Test
    void create_existingEmail_throwsPassengerNotCreatedException() {
        PassengerRequestDto requestDto = PassengerServiceImplTestUtil.getFirstPassengerRequestDto();

        when(passengerRepository.existsByEmail(requestDto.getEmail())).thenReturn(true);

        assertThrows(PassengerNotCreatedException.class, () -> passengerService.create(requestDto));
    }

    @Test
    void patch_existingPassenger_updatesFieldsAndReturnsUpdatedPassengerResponseDto() {
        Passenger passenger = PassengerServiceImplTestUtil.getFirstPassenger();
        PassengerUpdateRequestDto updateRequestDto = PassengerServiceImplTestUtil.getUpdatePassengerRequestDto();
        Passenger updatedPassenger = PassengerServiceImplTestUtil.getFirstPassenger();
        updatedPassenger.setEmail(PassengerServiceImplTestUtil.UPDATED_EMAIL);
        PassengerResponseDto updatedResponseDto = PassengerServiceImplTestUtil.getUpdatedPassengerResponseDto();

        when(passengerRepository.findById(PassengerServiceImplTestUtil.FIRST_ID)).thenReturn(Optional.of(passenger));
        when(passengerRepository.findIdByEmail(updateRequestDto.getEmail())).thenReturn(null);
        when(passengerRepository.save(passenger)).thenReturn(updatedPassenger);
        when(mapper.map(updatedPassenger, PassengerResponseDto.class)).thenReturn(updatedResponseDto);

        PassengerResponseDto result = passengerService.patch(PassengerServiceImplTestUtil.FIRST_ID, updateRequestDto);

        assertEquals(updatedResponseDto, result);
    }

    @Test
    void patch_existingEmail_throwsPassengerNotUpdatedException() {
        Passenger passenger = PassengerServiceImplTestUtil.getFirstPassenger();
        PassengerUpdateRequestDto updateRequestDto = PassengerServiceImplTestUtil.getUpdatePassengerRequestDto();

        when(passengerRepository.findById(PassengerServiceImplTestUtil.FIRST_ID)).thenReturn(Optional.of(passenger));
        when(passengerRepository.findIdByEmail(updateRequestDto.getEmail())).thenReturn(PassengerServiceImplTestUtil.SECOND_ID);

        assertThrows(PassengerNotUpdatedException.class, () -> passengerService.patch(PassengerServiceImplTestUtil.FIRST_ID, updateRequestDto));
    }

    @Test
    void delete_existingPassenger_deletesPassenger() {
        when(passengerRepository.existsById(PassengerServiceImplTestUtil.FIRST_ID)).thenReturn(true);

        passengerService.delete(PassengerServiceImplTestUtil.FIRST_ID);

        verify(passengerRepository).deleteById(PassengerServiceImplTestUtil.FIRST_ID);
    }

    @Test
    void delete_nonExistingPassenger_throwsPassengerNotFoundException() {
        when(passengerRepository.existsById(PassengerServiceImplTestUtil.FIRST_ID)).thenReturn(false);

        assertThrows(PassengerNotFoundException.class, () -> passengerService.delete(PassengerServiceImplTestUtil.FIRST_ID));
    }

    @Test
    void create_withExistingEmailDifferentCase_throwsPassengerNotCreatedException() {
        PassengerRequestDto requestDto = PassengerServiceImplTestUtil.getFirstPassengerRequestDto();
        String existingEmail = requestDto.getEmail();

        when(passengerRepository.existsByEmail(existingEmail)).thenReturn(true);

        assertThrows(PassengerNotCreatedException.class, () -> passengerService.create(requestDto));

        verify(passengerRepository).existsByEmail(existingEmail);
        verify(passengerRepository, never()).save(any(Passenger.class));
    }

    @Test
    void deleteMany_whenNotAllIdsExist_throwsPassengersNotFoundException() {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<Passenger> existingPassengers = List.of(
                PassengerServiceImplTestUtil.getFirstPassenger(),
                PassengerServiceImplTestUtil.getSecondPassenger()
        );

        when(passengerRepository.findAllById(ids)).thenReturn(existingPassengers);

        assertThrows(PassengersNotFoundException.class, () -> passengerService.deleteMany(ids));

        verify(passengerRepository).findAllById(ids);
        verify(passengerRepository, never()).deleteAll(any());
    }

    @Test
    void deleteMany_withEmptyIdsList_throwsEmptyIdsListException() {
        List<Long> emptyList = List.of();

        assertThrows(EmptyIdsListException.class, () -> passengerService.deleteMany(emptyList));
    }

    @Test
    void getMany_withExistingIds_returnsListOfPassengerResponseDto() {
        List<Long> ids = List.of(PassengerServiceImplTestUtil.FIRST_ID, PassengerServiceImplTestUtil.SECOND_ID);
        List<Passenger> passengers = PassengerServiceImplTestUtil.getPassengerList();
        ListResponseDto<PassengerResponseDto> expectedResponseDtos = PassengerServiceImplTestUtil.getPassengerResponseDtoList();
        List<PassengerResponseDto> passengerResponseDtoList = expectedResponseDtos.list();

        when(passengerRepository.findAllById(ids)).thenReturn(passengers);
        when(mapper.map(passengers.get(0), PassengerResponseDto.class)).thenReturn(passengerResponseDtoList.get(0));
        when(mapper.map(passengers.get(1), PassengerResponseDto.class)).thenReturn(passengerResponseDtoList.get(1));

        ListResponseDto<PassengerResponseDto> result = passengerService.getMany(ids);

        assertEquals(expectedResponseDtos, result);
    }

    @Test
    void getMany_withNonExistingIds_returnsEmptyList() {
        List<Long> ids = List.of(PassengerServiceImplTestUtil.NON_EXISTING_ID, PassengerServiceImplTestUtil.NON_EXISTING_ID_2);

        when(passengerRepository.findAllById(ids)).thenReturn(List.of());

        ListResponseDto<PassengerResponseDto> result = passengerService.getMany(ids);

        assertTrue(result.list().isEmpty());
    }
}