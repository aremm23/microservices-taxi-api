package com.artsem.api.driverservice.unit.controller;

import com.artsem.api.driverservice.controller.DriverController;
import com.artsem.api.driverservice.filter.DriverFilter;
import com.artsem.api.driverservice.model.dto.request.DriverRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverStatusUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.responce.DriverAndCarResponseDto;
import com.artsem.api.driverservice.model.dto.responce.DriverResponseDto;
import com.artsem.api.driverservice.model.dto.responce.ListResponseDto;
import com.artsem.api.driverservice.service.DriverService;
import com.artsem.api.driverservice.util.CarServiceImplTestUtil;
import com.artsem.api.driverservice.util.DriverServiceImplTestUtil;
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

@WebMvcTest(DriverController.class)
class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverService driverService;

    @Test
    void testGetList_Success() throws Exception {
        Page<DriverResponseDto> page = new PageImpl<>(List.of(
                DriverServiceImplTestUtil.getFirstDriverResponseDto(),
                DriverServiceImplTestUtil.getSecondDriverResponseDto()
        ));
        when(driverService.getList(any(DriverFilter.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get(DriverServiceImplTestUtil.DRIVERS_BASE_URL)
                        .param("page", DriverServiceImplTestUtil.PAGE_NUMBER)
                        .param("size", DriverServiceImplTestUtil.PAGE_SIZE_2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].firstname").value(DriverServiceImplTestUtil.FIRST_FIRSTNAME))
                .andExpect(jsonPath("$.content[1].email").value(DriverServiceImplTestUtil.SECOND_EMAIL));

        verify(driverService, times(1)).getList(any(DriverFilter.class), any(Pageable.class));
    }

    @Test
    void testGetDriverById_Success() throws Exception {
        DriverResponseDto driverResponseDto = DriverServiceImplTestUtil.getFirstDriverResponseDto();
        when(driverService.getOne(1L)).thenReturn(driverResponseDto);

        mockMvc.perform(get(DriverServiceImplTestUtil.DRIVER_BY_ID_URL, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstname").value(DriverServiceImplTestUtil.FIRST_FIRSTNAME))
                .andExpect(jsonPath("$.email").value(DriverServiceImplTestUtil.FIRST_EMAIL));

        verify(driverService, times(1)).getOne(1L);
    }

    @Test
    void testCreateDriver_Success() throws Exception {
        DriverRequestDto requestDto = DriverServiceImplTestUtil.getFirstDriverRequestDto();
        DriverResponseDto responseDto = DriverServiceImplTestUtil.getFirstDriverResponseDto();
        when(driverService.create(any(DriverRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post(DriverServiceImplTestUtil.DRIVERS_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstname").value(DriverServiceImplTestUtil.FIRST_FIRSTNAME));

        verify(driverService, times(1)).create(any(DriverRequestDto.class));
    }

    @Test
    void testUpdateDriver_Success() throws Exception {
        DriverUpdateRequestDto updateRequestDto = DriverServiceImplTestUtil.getUpdateDriverRequestDto();
        DriverResponseDto responseDto = DriverServiceImplTestUtil.getFirstDriverResponseDto();
        when(driverService.patch(eq(1L), any(DriverUpdateRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(patch(DriverServiceImplTestUtil.DRIVER_BY_ID_URL, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstname").value(DriverServiceImplTestUtil.FIRST_FIRSTNAME));

        verify(driverService, times(1)).patch(eq(1L), any(DriverUpdateRequestDto.class));
    }

    @Test
    void testDeleteDriver_Success() throws Exception {
        doNothing().when(driverService).delete(1L);

        mockMvc.perform(delete(DriverServiceImplTestUtil.DRIVER_BY_ID_URL, 1))
                .andExpect(status().isNoContent());

        verify(driverService, times(1)).delete(1L);
    }

    @Test
    void testGetManyDrivers_Success() throws Exception {
        List<Long> ids = List.of(1L, 2L);
        List<DriverResponseDto> listDtos = List.of(
                DriverServiceImplTestUtil.getFirstDriverResponseDto(),
                DriverServiceImplTestUtil.getSecondDriverResponseDto()
        );
        ListResponseDto<DriverResponseDto> responseDtos = ListResponseDto.<DriverResponseDto>builder()
                .size(listDtos.size())
                .list(listDtos)
                .build();

        when(driverService.getMany(ids)).thenReturn(responseDtos);

        mockMvc.perform(get(DriverServiceImplTestUtil.DRIVER_BY_IDS_URL)
                        .param("ids", "1,2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.list", hasSize(2)))
                .andExpect(jsonPath("$.list[0].id").value(DriverServiceImplTestUtil.FIRST_ID))
                .andExpect(jsonPath("$.list[1].id").value(DriverServiceImplTestUtil.SECOND_ID));

        verify(driverService, times(1)).getMany(ids);
    }

    @Test
    void testDeleteManyDrivers_Success() throws Exception {
        List<Long> ids = List.of(1L, 2L);
        doNothing().when(driverService).deleteMany(ids);

        mockMvc.perform(delete(DriverServiceImplTestUtil.DRIVERS_BASE_URL)
                        .param("ids", "1,2"))
                .andExpect(status().isNoContent());

        verify(driverService, times(1)).deleteMany(ids);
    }

    @Test
    void testUpdateDriverStatus_Success() throws Exception {
        DriverResponseDto responseDto = DriverServiceImplTestUtil.getFirstDriverResponseDto();
        DriverStatusUpdateRequestDto statusUpdateDto = DriverServiceImplTestUtil.getStatusUpdateRequestDto();
        when(driverService.updateDriverStatus(eq(1L), any(DriverStatusUpdateRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(patch(DriverServiceImplTestUtil.DRIVER_BY_ID_URL + "/status", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(statusUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstname").value(DriverServiceImplTestUtil.FIRST_FIRSTNAME));

        verify(driverService, times(1)).updateDriverStatus(eq(1L), any(DriverStatusUpdateRequestDto.class));
    }

    @Test
    void testAssignCarToDriver_Success() throws Exception {
        DriverAndCarResponseDto responseDto = DriverServiceImplTestUtil.getDriverAndCarResponseDto();
        when(driverService.assignCarToDriver(1L, 2L)).thenReturn(responseDto);

        mockMvc.perform(patch(DriverServiceImplTestUtil.DRIVER_BY_ID_URL + "/car/{carId}", 1, 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.car.id").value(CarServiceImplTestUtil.FIRST_ID))
                .andExpect(jsonPath("$.car.licensePlate").value(CarServiceImplTestUtil.FIRST_LICENSE_PLATE));

        verify(driverService, times(1)).assignCarToDriver(1L, 2L);
    }

    @Test
    void testRemoveCarFromDriver_Success() throws Exception {
        DriverAndCarResponseDto responseDto = DriverServiceImplTestUtil.getDriverWithoutCarResponseDto();
        when(driverService.removeCarFromDriver(1L)).thenReturn(responseDto);

        mockMvc.perform(delete(DriverServiceImplTestUtil.DRIVER_BY_ID_URL + "/car", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.car").isEmpty());

        verify(driverService, times(1)).removeCarFromDriver(1L);
    }

    @Test
    void testGetOneWithCar_Success() throws Exception {
        DriverAndCarResponseDto responseDto = DriverServiceImplTestUtil.getDriverAndCarResponseDto();
        when(driverService.getOneWithCar(1L)).thenReturn(responseDto);

        mockMvc.perform(get(DriverServiceImplTestUtil.DRIVER_BY_ID_URL + "/car", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.car.id").value(1))
                .andExpect(jsonPath("$.email").value(DriverServiceImplTestUtil.FIRST_EMAIL))
                .andExpect(jsonPath("$.car.licensePlate").value(CarServiceImplTestUtil.FIRST_LICENSE_PLATE));

        verify(driverService, times(1)).getOneWithCar(1L);
    }

}