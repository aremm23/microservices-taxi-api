package com.artsem.api.driverservice.unit.controller;

import com.artsem.api.driverservice.controller.CarController;
import com.artsem.api.driverservice.filter.CarFilter;
import com.artsem.api.driverservice.model.dto.request.CarRequestDto;
import com.artsem.api.driverservice.model.dto.request.CarUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.responce.CarResponseDto;
import com.artsem.api.driverservice.model.dto.responce.ListResponseDto;
import com.artsem.api.driverservice.service.CarService;
import com.artsem.api.driverservice.util.CarServiceImplTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    void testGetList_Success() throws Exception {
        Page<CarResponseDto> page = new PageImpl<>(List.of(
                CarServiceImplTestUtil.getFirstCarResponseDto(),
                CarServiceImplTestUtil.getSecondCarResponseDto()
        ));
        when(carService.getList(any(CarFilter.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get(CarServiceImplTestUtil.CARS_BASE_URL)
                        .param("page", CarServiceImplTestUtil.PAGE_NUMBER)
                        .param("size", CarServiceImplTestUtil.PAGE_SIZE_5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].licensePlate").value(CarServiceImplTestUtil.FIRST_LICENSE_PLATE))
                .andExpect(jsonPath("$.content[1].id").value(2));

        verify(carService, times(1)).getList(any(CarFilter.class), any(Pageable.class));
    }

    @Test
    void testGetOne_Success() throws Exception {
        CarResponseDto carResponseDto = CarServiceImplTestUtil.getFirstCarResponseDto();
        when(carService.getOne(1L)).thenReturn(carResponseDto);

        mockMvc.perform(get(CarServiceImplTestUtil.CAR_BY_ID_URL, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.licensePlate").value(CarServiceImplTestUtil.FIRST_LICENSE_PLATE));

        verify(carService, times(1)).getOne(1L);
    }

    @Test
    void testGetMany_Success() throws Exception {
        List<Long> ids = List.of(1L, 2L);
        List<CarResponseDto> cars = List.of(
                CarServiceImplTestUtil.getFirstCarResponseDto(),
                CarServiceImplTestUtil.getSecondCarResponseDto()
        );
        ListResponseDto<CarResponseDto> responseDto = new ListResponseDto<>(cars.size(), cars);

        when(carService.getMany(ids)).thenReturn(responseDto);

        mockMvc.perform(get(CarServiceImplTestUtil.CAR_BY_IDS_URL)
                        .param("ids", "1,2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.list[0].id").value(1))
                .andExpect(jsonPath("$.list[1].id").value(2));

        verify(carService, times(1)).getMany(ids);
    }

    @Test
    void testCreate_Success() throws Exception {
        CarRequestDto requestDto = CarServiceImplTestUtil.getFirstCarRequestDto();
        CarResponseDto responseDto = CarServiceImplTestUtil.getFirstCarResponseDto();

        when(carService.create(any(CarRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post(CarServiceImplTestUtil.CARS_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.licensePlate").value(CarServiceImplTestUtil.FIRST_LICENSE_PLATE));

        verify(carService, times(1)).create(any(CarRequestDto.class));
    }

    @Test
    void testPatch_Success() throws Exception {
        CarUpdateRequestDto updateDto = CarServiceImplTestUtil.getUpdateCarRequestDto();
        CarResponseDto responseDto = CarServiceImplTestUtil.getFirstCarResponseDto();
        responseDto.setLicensePlate(CarServiceImplTestUtil.UPDATED_LICENSE_PLATE);

        when(carService.patch(eq(1L), any(CarUpdateRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(patch(CarServiceImplTestUtil.CAR_BY_ID_URL, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.licensePlate").value(CarServiceImplTestUtil.UPDATED_LICENSE_PLATE));

        verify(carService, times(1)).patch(eq(1L), any(CarUpdateRequestDto.class));
    }

    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(carService).delete(1L);

        mockMvc.perform(delete(CarServiceImplTestUtil.CAR_BY_ID_URL, 1))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).delete(1L);
    }

    @Test
    void testDeleteMany_Success() throws Exception {
        List<Long> ids = List.of(1L, 2L);
        doNothing().when(carService).deleteMany(ids);

        mockMvc.perform(delete(CarServiceImplTestUtil.CARS_BASE_URL)
                        .param("ids", "1,2"))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).deleteMany(ids);
    }
}
