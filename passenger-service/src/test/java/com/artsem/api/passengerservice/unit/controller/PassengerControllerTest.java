package com.artsem.api.passengerservice.unit.controller;

import com.artsem.api.passengerservice.controller.PassengerController;
import com.artsem.api.passengerservice.filter.PassengerFilter;
import com.artsem.api.passengerservice.model.dto.request.PassengerRequestDto;
import com.artsem.api.passengerservice.model.dto.request.PassengerUpdateRequestDto;
import com.artsem.api.passengerservice.model.dto.response.ListResponseDto;
import com.artsem.api.passengerservice.model.dto.response.PassengerResponseDto;
import com.artsem.api.passengerservice.service.PassengerService;
import com.artsem.api.passengerservice.util.PassengerServiceImplTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PassengerController.class)
class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PassengerService passengerService;

    @Test
    void testGetList_Success() throws Exception {
        Page<PassengerResponseDto> page = new PageImpl<>(List.of(
                PassengerServiceImplTestUtil.getFirstPassengerResponseDto(),
                PassengerServiceImplTestUtil.getSecondPassengerResponseDto()
        ));
        when(passengerService.getList(any(PassengerFilter.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/passengers")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].firstname").value(PassengerServiceImplTestUtil.FIRST_FIRSTNAME))
                .andExpect(jsonPath("$.content[1].email").value(PassengerServiceImplTestUtil.SECOND_EMAIL));

        verify(passengerService, times(1)).getList(any(PassengerFilter.class), any(Pageable.class));
    }

    @Test
    void testGetPassengerById_Success() throws Exception {
        PassengerResponseDto passengerResponseDto = PassengerServiceImplTestUtil.getFirstPassengerResponseDto();
        when(passengerService.getOne(1L)).thenReturn(passengerResponseDto);

        mockMvc.perform(get("/api/v1/passengers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstname").value(PassengerServiceImplTestUtil.FIRST_FIRSTNAME))
                .andExpect(jsonPath("$.email").value(PassengerServiceImplTestUtil.FIRST_EMAIL));

        verify(passengerService, times(1)).getOne(1L);
    }

    @Test
    void testCreatePassenger_Success() throws Exception {
        PassengerRequestDto requestDto = PassengerServiceImplTestUtil.getFirstPassengerRequestDto();
        PassengerResponseDto responseDto = PassengerServiceImplTestUtil.getFirstPassengerResponseDto();
        when(passengerService.create(any(PassengerRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstname").value(PassengerServiceImplTestUtil.FIRST_FIRSTNAME));

        verify(passengerService, times(1)).create(any(PassengerRequestDto.class));
    }

    @Test
    void testUpdatePassenger_Success() throws Exception {
        PassengerUpdateRequestDto updateRequestDto = PassengerServiceImplTestUtil.getUpdatePassengerRequestDto();
        PassengerResponseDto responseDto = PassengerServiceImplTestUtil.getFirstPassengerResponseDto();
        when(passengerService.patch(eq(1L), any(PassengerUpdateRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(patch("/api/v1/passengers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstname").value(PassengerServiceImplTestUtil.FIRST_FIRSTNAME));

        verify(passengerService, times(1)).patch(eq(1L), any(PassengerUpdateRequestDto.class));
    }

    @Test
    void testDeletePassenger_Success() throws Exception {
        doNothing().when(passengerService).delete(1L);

        mockMvc.perform(delete("/api/v1/passengers/1"))
                .andExpect(status().isNoContent());

        verify(passengerService, times(1)).delete(1L);
    }

    @Test
    void testGetManyPassengers_Success() throws Exception {
        List<Long> ids = List.of(1L, 2L);
        List<PassengerResponseDto> listDtos = List.of(
                PassengerServiceImplTestUtil.getFirstPassengerResponseDto(),
                PassengerServiceImplTestUtil.getSecondPassengerResponseDto()
        );
        ListResponseDto<PassengerResponseDto> responseDtos = ListResponseDto.<PassengerResponseDto>builder()
                .size(listDtos.size())
                .list(listDtos)
                .build();

        when(passengerService.getMany(ids)).thenReturn(responseDtos);

        mockMvc.perform(get("/api/v1/passengers/by-ids")
                        .param("ids", "1,2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.list", hasSize(2)))
                .andExpect(jsonPath("$.list[0].id").value(PassengerServiceImplTestUtil.FIRST_ID))
                .andExpect(jsonPath("$.list[1].id").value(PassengerServiceImplTestUtil.SECOND_ID));

        verify(passengerService, times(1)).getMany(ids);
    }

    @Test
    void testDeleteManyPassengers_Success() throws Exception {
        List<Long> ids = List.of(1L, 2L);
        doNothing().when(passengerService).deleteMany(ids);

        mockMvc.perform(delete("/api/v1/passengers")
                        .param("ids", "1,2"))
                .andExpect(status().isNoContent());

        verify(passengerService, times(1)).deleteMany(ids);
    }

}