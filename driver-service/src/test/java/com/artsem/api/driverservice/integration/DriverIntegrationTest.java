package com.artsem.api.driverservice.integration;

import com.artsem.api.driverservice.integration.config.PostgresContainerConfig;
import com.artsem.api.driverservice.model.Driver;
import com.artsem.api.driverservice.model.dto.request.DriverRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverStatusUpdateRequestDto;
import com.artsem.api.driverservice.model.dto.request.DriverUpdateRequestDto;
import com.artsem.api.driverservice.repository.DriverRepository;
import com.artsem.api.driverservice.util.CarServiceImplTestUtil;
import com.artsem.api.driverservice.util.DriverServiceImplTestUtil;
import lombok.SneakyThrows;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
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
                "classpath:sql/delete_all_drivers.sql",
                "classpath:sql/insert_test_drivers.sql"
        }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class DriverIntegrationTest extends PostgresContainerConfig {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:" + port + DriverServiceImplTestUtil.DRIVERS_BASE_URL;

    @SneakyThrows
    @Test
    @Sql(scripts = "classpath:sql/delete_all_drivers.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testCreateDriver() {
        DriverRequestDto requestDto = DriverServiceImplTestUtil.getFirstDriverRequestDto();
        String json = new ObjectMapper().writeValueAsString(requestDto);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname").value(DriverServiceImplTestUtil.FIRST_FIRSTNAME))
                .andExpect(jsonPath("$.email").value(DriverServiceImplTestUtil.FIRST_EMAIL));

        List<Driver> drivers = driverRepository.findAll();
        assertThat(drivers).hasSize(1);
        AssertionsForClassTypes.assertThat(drivers.get(0).getEmail()).isEqualTo(DriverServiceImplTestUtil.FIRST_EMAIL);
    }

    @SneakyThrows
    @Test
    void testGetDriverById() {

        mockMvc.perform(get(baseUrl + "/{id}", DriverServiceImplTestUtil.FIRST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value(DriverServiceImplTestUtil.FIRST_FIRSTNAME))
                .andExpect(jsonPath("$.email").value(DriverServiceImplTestUtil.FIRST_EMAIL));
    }

    @SneakyThrows
    @Test
    void testUpdateDriver() {
        DriverUpdateRequestDto updateDto = DriverServiceImplTestUtil.getUpdateDriverRequestDto();
        String json = new ObjectMapper().writeValueAsString(updateDto);

        mockMvc.perform(patch(baseUrl + "/{id}", DriverServiceImplTestUtil.FIRST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value(DriverServiceImplTestUtil.UPDATED_FIRSTNAME))
                .andExpect(jsonPath("$.surname").value(DriverServiceImplTestUtil.UPDATED_SURNAME))
                .andExpect(jsonPath("$.email").value(DriverServiceImplTestUtil.UPDATED_EMAIL));
    }

    @SneakyThrows
    @Test
    void testDeleteDriver() {
        mockMvc.perform(delete(baseUrl + "/{id}", DriverServiceImplTestUtil.FIRST_ID))
                .andExpect(status().isNoContent());

        Optional<Driver> driver = driverRepository.findById(DriverServiceImplTestUtil.FIRST_ID);
        AssertionsForClassTypes.assertThat(driver).isEmpty();
    }

    @SneakyThrows
    @Test
    void testGetAllDrivers() {
        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(3))
                .andExpect(jsonPath("$.content[0].email").value(DriverServiceImplTestUtil.FIRST_EMAIL))
                .andExpect(jsonPath("$.content[1].email").value(DriverServiceImplTestUtil.SECOND_EMAIL))
                .andExpect(jsonPath("$.content[2].email").value(DriverServiceImplTestUtil.THIRD_EMAIL));
    }

    @SneakyThrows
    @Test
    void testCreateDriverWithInvalidJson() {
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DriverServiceImplTestUtil.CREATE_INVALID_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("{firstname=First name is required, surname=Last name is required}"));
    }

    @SneakyThrows
    @Test
    void testGetDriverByNonExistentId() {
        mockMvc.perform(get(baseUrl + "/{id}", DriverServiceImplTestUtil.NON_EXISTING_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Driver not found"));
    }

    @SneakyThrows
    @Test
    void testGetAllDriversWithPaginationAndSorting() {
        String page = DriverServiceImplTestUtil.PAGE_NUMBER;
        String size = DriverServiceImplTestUtil.PAGE_SIZE_5;
        String sort = "email,asc";

        mockMvc.perform(get(baseUrl + "?page=" + page + "&size=" + size + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(3))
                .andExpect(jsonPath("$.page.size").value(5))
                .andExpect(jsonPath("$.page.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].email").value(DriverServiceImplTestUtil.FIRST_EMAIL))
                .andExpect(jsonPath("$.content[1].email").value(DriverServiceImplTestUtil.SECOND_EMAIL))
                .andExpect(jsonPath("$.content[2].email").value(DriverServiceImplTestUtil.THIRD_EMAIL));
    }

    @SneakyThrows
    @Test
    void testGetAllDriversWithPaginationAndFiltering() {
        mockMvc.perform(get(baseUrl + "?surnameLike=" + DriverServiceImplTestUtil.SURNAME_LIKE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].email").value(DriverServiceImplTestUtil.FIRST_EMAIL))
                .andExpect(jsonPath("$.content[1].email").value(DriverServiceImplTestUtil.SECOND_EMAIL));
    }

    @SneakyThrows
    @Test
    void testCreateDriverWithInvalidEmail() {
        String invalidJson = """
                    {
                        "email": "invalid-email",
                        "firstname": "John",
                        "surname": "Doe"
                    }
                """;

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("{email=Invalid email format}"));
    }

    @SneakyThrows
    @Test
    void testUpdateDriverInDatabase() {
        DriverUpdateRequestDto updateDto = DriverServiceImplTestUtil.getUpdateDriverRequestDto();
        String json = new ObjectMapper().writeValueAsString(updateDto);

        mockMvc.perform(patch(baseUrl + "/{id}", DriverServiceImplTestUtil.FIRST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        Driver updatedDriver = driverRepository.findById(DriverServiceImplTestUtil.FIRST_ID).orElseThrow();
        AssertionsForClassTypes.assertThat(updatedDriver.getFirstname()).isEqualTo(DriverServiceImplTestUtil.UPDATED_FIRSTNAME);
        AssertionsForClassTypes.assertThat(updatedDriver.getEmail()).isEqualTo(DriverServiceImplTestUtil.UPDATED_EMAIL);
    }

    @SneakyThrows
    @Test
    void testLocalizedEsErrorMessage() {
        mockMvc.perform(get(baseUrl + "/{id}", DriverServiceImplTestUtil.NON_EXISTING_ID)
                        .header("Accept-Language", "es"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Conductor no encontrado"));
    }

    @SneakyThrows
    @Test
    void testLocalizedDeErrorMessage() {
        mockMvc.perform(get(baseUrl + "/{id}", DriverServiceImplTestUtil.NON_EXISTING_ID)
                        .header("Accept-Language", "de"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Fahrer nicht gefunden"));
    }

    @SneakyThrows
    @Test
    void testGetAllDriversWithNoMatchingFilter() {
        mockMvc.perform(get(baseUrl + "?surnameLike=NonExistentSurname"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(0));
    }

    @SneakyThrows
    @Test
    void testUpdateDriverStatus() {
        DriverStatusUpdateRequestDto statusUpdateDto = new DriverStatusUpdateRequestDto(true);
        String json = new ObjectMapper().writeValueAsString(statusUpdateDto);

        mockMvc.perform(patch(baseUrl + "/{id}/status", DriverServiceImplTestUtil.FIRST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(DriverServiceImplTestUtil.FIRST_ID));

        Driver updatedDriver = driverRepository.findById(DriverServiceImplTestUtil.FIRST_ID).orElseThrow();
        AssertionsForClassTypes.assertThat(updatedDriver.getIsFree()).isTrue();
    }

    @Sql(
            scripts = {
                    "classpath:sql/delete_all_cars.sql",
                    "classpath:sql/insert_test_cars.sql",
                    "classpath:sql/delete_all_drivers.sql",
                    "classpath:sql/insert_test_drivers.sql"
            }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @SneakyThrows
    @Test
    void testAssignCarToDriver() {

        mockMvc.perform(
                        patch(baseUrl + "/{driverId}/car/{carId}",
                                DriverServiceImplTestUtil.FIRST_ID,
                                CarServiceImplTestUtil.FIRST_ID)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(DriverServiceImplTestUtil.FIRST_ID))
                .andExpect(jsonPath("$.car.id").value(CarServiceImplTestUtil.FIRST_ID));

        Driver updatedDriver = driverRepository.findById(DriverServiceImplTestUtil.FIRST_ID).orElseThrow();
        AssertionsForClassTypes.assertThat(updatedDriver.getCar().getId()).isEqualTo(CarServiceImplTestUtil.FIRST_ID);
    }

    @Sql(
            scripts = {
                    "classpath:sql/delete_all_cars.sql",
                    "classpath:sql/insert_test_cars.sql",
                    "classpath:sql/delete_all_drivers.sql",
                    "classpath:sql/insert_test_drivers.sql",
                    "classpath:sql/add_car_to_driver.sql"
            }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @SneakyThrows
    @Test
    void testRemoveCarFromDriver() {
        mockMvc.perform(delete(baseUrl + "/{driverId}/car", DriverServiceImplTestUtil.FIRST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.car").isEmpty());

        Driver updatedDriver = driverRepository.findById(DriverServiceImplTestUtil.FIRST_ID).orElseThrow();
        AssertionsForClassTypes.assertThat(updatedDriver.getCar()).isNull();
    }

    @Sql(
            scripts = {
                    "classpath:sql/delete_all_cars.sql",
                    "classpath:sql/insert_test_cars.sql",
                    "classpath:sql/delete_all_drivers.sql",
                    "classpath:sql/insert_test_drivers.sql",
                    "classpath:sql/add_car_to_driver.sql"
            }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @SneakyThrows
    @Test
    void testGetOneWithCar() {
        mockMvc.perform(get(baseUrl + "/{id}/car", DriverServiceImplTestUtil.FIRST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(DriverServiceImplTestUtil.FIRST_ID))
                .andExpect(jsonPath("$.car.id").value(CarServiceImplTestUtil.FIRST_ID));
    }

}