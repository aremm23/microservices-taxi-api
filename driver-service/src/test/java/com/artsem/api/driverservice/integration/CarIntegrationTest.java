package com.artsem.api.driverservice.integration;

import com.artsem.api.driverservice.integration.config.PostgresContainerConfig;
import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.model.dto.request.CarRequestDto;
import com.artsem.api.driverservice.model.dto.request.CarUpdateRequestDto;
import com.artsem.api.driverservice.repository.CarRepository;
import com.artsem.api.driverservice.util.CarServiceImplTestUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(
        scripts = {
                "classpath:sql/delete_all_cars.sql",
                "classpath:sql/insert_test_cars.sql"
        }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@WithMockUser(roles = "ADMIN")
class CarIntegrationTest extends PostgresContainerConfig {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:" + port + CarServiceImplTestUtil.CARS_BASE_URL;

    @SneakyThrows
    @Test
    @Sql(scripts = "classpath:sql/delete_all_cars.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testCreateCar() {
        CarRequestDto requestDto = CarServiceImplTestUtil.getFirstCarRequestDto();
        String json = new ObjectMapper().writeValueAsString(requestDto);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.model").value(CarServiceImplTestUtil.FIRST_MODEL))
                .andExpect(jsonPath("$.licensePlate").value(CarServiceImplTestUtil.FIRST_LICENSE_PLATE));

        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(1);
        assertThat(cars.get(0).getLicensePlate()).isEqualTo(CarServiceImplTestUtil.FIRST_LICENSE_PLATE);
    }

    @SneakyThrows
    @Test
    void testGetCarById() {
        mockMvc.perform(get(baseUrl + "/{id}", CarServiceImplTestUtil.FIRST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value(CarServiceImplTestUtil.FIRST_MODEL))
                .andExpect(jsonPath("$.licensePlate").value(CarServiceImplTestUtil.FIRST_LICENSE_PLATE));
    }

    @SneakyThrows
    @Test
    void testUpdateCar() {
        CarUpdateRequestDto updateDto = CarServiceImplTestUtil.getUpdateCarRequestDto();
        String json = new ObjectMapper().writeValueAsString(updateDto);

        mockMvc.perform(patch(baseUrl + "/{id}", CarServiceImplTestUtil.FIRST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value(CarServiceImplTestUtil.UPDATED_MODEL))
                .andExpect(jsonPath("$.licensePlate").value(CarServiceImplTestUtil.UPDATED_LICENSE_PLATE));
    }

    @SneakyThrows
    @Test
    void testDeleteCar() {
        mockMvc.perform(delete(baseUrl + "/{id}", CarServiceImplTestUtil.FIRST_ID))
                .andExpect(status().isNoContent());

        Optional<Car> car = carRepository.findById(CarServiceImplTestUtil.FIRST_ID);
        assertThat(car).isEmpty();
    }

    @SneakyThrows
    @Test
    void testGetAllCars() {
        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(3))
                .andExpect(jsonPath("$.content[0].licensePlate").value(CarServiceImplTestUtil.FIRST_LICENSE_PLATE))
                .andExpect(jsonPath("$.content[1].licensePlate").value(CarServiceImplTestUtil.SECOND_LICENSE_PLATE))
                .andExpect(jsonPath("$.content[2].licensePlate").value(CarServiceImplTestUtil.THIRD_LICENSE_PLATE));
    }

    @SneakyThrows
    @Test
    void testCreateCarWithInvalidJson() {
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CarServiceImplTestUtil.INVALID_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("{licensePlate=Invalid license plate format}"));
    }

    @SneakyThrows
    @Test
    void testGetCarByNonExistentId() {
        mockMvc.perform(get(baseUrl + "/{id}", CarServiceImplTestUtil.NON_EXISTING_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Car not found"));
    }

    @SneakyThrows
    @Test
    void testGetAllCarsWithPaginationAndSorting() {
        String page = CarServiceImplTestUtil.PAGE_NUMBER;
        String size = CarServiceImplTestUtil.PAGE_SIZE_5;
        String sort = "licensePlate,asc";

        mockMvc.perform(get(baseUrl + "?page=" + page + "&size=" + size + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(3))
                .andExpect(jsonPath("$.page.size").value(5))
                .andExpect(jsonPath("$.page.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].licensePlate").value(CarServiceImplTestUtil.SECOND_LICENSE_PLATE))
                .andExpect(jsonPath("$.content[1].licensePlate").value(CarServiceImplTestUtil.FIRST_LICENSE_PLATE))
                .andExpect(jsonPath("$.content[2].licensePlate").value(CarServiceImplTestUtil.THIRD_LICENSE_PLATE));
    }

    @SneakyThrows
    @Test
    void testGetAllCarsWithPaginationAndFiltering() {
        mockMvc.perform(get(baseUrl + "?modelLike=" + CarServiceImplTestUtil.MODEL_LIKE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].licensePlate").value(CarServiceImplTestUtil.FIRST_LICENSE_PLATE  ))
                .andExpect(jsonPath("$.content[1].licensePlate").value(CarServiceImplTestUtil.SECOND_LICENSE_PLATE ));
    }


    @SneakyThrows
    @Test
    void testUpdateCarInDatabase() {
        CarUpdateRequestDto updateDto = CarServiceImplTestUtil.getUpdateCarRequestDto();
        String json = new ObjectMapper().writeValueAsString(updateDto);

        mockMvc.perform(patch(baseUrl + "/{id}", CarServiceImplTestUtil.FIRST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        Car updatedCar = carRepository.findById(CarServiceImplTestUtil.FIRST_ID).orElseThrow();
        assertThat(updatedCar.getLicensePlate()).isEqualTo(CarServiceImplTestUtil.UPDATED_LICENSE_PLATE);
        assertThat(updatedCar.getModel()).isEqualTo(CarServiceImplTestUtil.UPDATED_MODEL);
    }

    @SneakyThrows
    @Test
    void testLocalizedEsErrorMessage() {
        mockMvc.perform(get(baseUrl + "/{id}", CarServiceImplTestUtil.NON_EXISTING_ID)
                        .header("Accept-Language", "es"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Coche no encontrado"));
    }

    @SneakyThrows
    @Test
    void testLocalizedDeErrorMessage() {
        mockMvc.perform(get(baseUrl + "/{id}", CarServiceImplTestUtil.NON_EXISTING_ID)
                        .header("Accept-Language", "de"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Auto nicht gefunden"));
    }

    @SneakyThrows
    @Test
    void testGetAllCarsWithNoMatchingFilter() {
        mockMvc.perform(get(baseUrl + "?modelLike=NonExistentModel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(0));
    }

}