package com.artsem.api.passengerservice.integration;

import com.artsem.api.passengerservice.integration.config.PostgresContainerConfig;
import com.artsem.api.passengerservice.model.Passenger;
import com.artsem.api.passengerservice.model.dto.request.PassengerRequestDto;
import com.artsem.api.passengerservice.model.dto.request.PassengerUpdateRequestDto;
import com.artsem.api.passengerservice.repository.PassengerRepository;
import com.artsem.api.passengerservice.util.PassengerServiceImplTestUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
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
                "classpath:sql/delete_all_passengers.sql",
                "classpath:sql/insert_test_passengers.sql"
        }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@WithMockUser(roles = "ADMIN")
class PassengerIntegrationTest extends PostgresContainerConfig {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:" + port + PassengerServiceImplTestUtil.PASSENGERS_BASE_URL;

    @SneakyThrows
    @Test
    @Sql(scripts = "classpath:sql/delete_all_passengers.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testCreatePassenger() {
        PassengerRequestDto requestDto = PassengerServiceImplTestUtil.getFirstPassengerRequestDto();
        String json = new ObjectMapper().writeValueAsString(requestDto);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname").value(PassengerServiceImplTestUtil.FIRST_FIRSTNAME))
                .andExpect(jsonPath("$.email").value(PassengerServiceImplTestUtil.FIRST_EMAIL));

        List<Passenger> passengers = passengerRepository.findAll();
        assertThat(passengers).hasSize(1);
        assertThat(passengers.get(0).getEmail()).isEqualTo(PassengerServiceImplTestUtil.FIRST_EMAIL);
    }

    @SneakyThrows
    @Test
    void testGetPassengerById() {

        mockMvc.perform(get(baseUrl + "/{id}", PassengerServiceImplTestUtil.FIRST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value(PassengerServiceImplTestUtil.FIRST_FIRSTNAME))
                .andExpect(jsonPath("$.email").value(PassengerServiceImplTestUtil.FIRST_EMAIL));
    }

    @SneakyThrows
    @Test
    void testUpdatePassenger() {
        PassengerUpdateRequestDto updateDto = PassengerServiceImplTestUtil.getUpdatePassengerRequestDto();
        String json = new ObjectMapper().writeValueAsString(updateDto);

        mockMvc.perform(patch(baseUrl + "/{id}", PassengerServiceImplTestUtil.FIRST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value(PassengerServiceImplTestUtil.UPDATED_FIRSTNAME))
                .andExpect(jsonPath("$.surname").value(PassengerServiceImplTestUtil.UPDATED_SURNAME))
                .andExpect(jsonPath("$.email").value(PassengerServiceImplTestUtil.UPDATED_EMAIL));
    }

    @SneakyThrows
    @Test
    void testDeletePassenger() {
        mockMvc.perform(delete(baseUrl + "/{id}", PassengerServiceImplTestUtil.FIRST_ID))
                .andExpect(status().isNoContent());

        Optional<Passenger> passenger = passengerRepository.findById(PassengerServiceImplTestUtil.FIRST_ID);
        assertThat(passenger).isEmpty();
    }

    @SneakyThrows
    @Test
    void testGetAllPassengers() {
        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(3))
                .andExpect(jsonPath("$.content[0].email").value(PassengerServiceImplTestUtil.FIRST_EMAIL))
                .andExpect(jsonPath("$.content[1].email").value(PassengerServiceImplTestUtil.SECOND_EMAIL))
                .andExpect(jsonPath("$.content[2].email").value(PassengerServiceImplTestUtil.THIRD_EMAIL));
    }

    @SneakyThrows
    @Test
    void testCreatePassengerWithInvalidJson() {
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PassengerServiceImplTestUtil.CREATE_INVALID_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(PassengerServiceImplTestUtil.INVALID_JSON_MESSAGE));
    }

    @SneakyThrows
    @Test
    void testGetPassengerByNonExistentId() {
        mockMvc.perform(get(baseUrl + "/{id}", PassengerServiceImplTestUtil.NON_EXISTING_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Passenger not found"));
    }

    @SneakyThrows
    @Test
    void testGetAllPassengersWithPaginationAndSorting() {
        String page = PassengerServiceImplTestUtil.PAGE_NUMBER;
        String size = PassengerServiceImplTestUtil.PAGE_SIZE_5;
        String sort = "email,asc";

        mockMvc.perform(get(baseUrl + "?page=" + page + "&size=" + size + "&sort=" + sort))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(3))
                .andExpect(jsonPath("$.page.size").value(5))
                .andExpect(jsonPath("$.page.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].email").value(PassengerServiceImplTestUtil.FIRST_EMAIL))
                .andExpect(jsonPath("$.content[1].email").value(PassengerServiceImplTestUtil.SECOND_EMAIL))
                .andExpect(jsonPath("$.content[2].email").value(PassengerServiceImplTestUtil.THIRD_EMAIL));
    }

    @SneakyThrows
    @Test
    void testGetAllPassengersWithPaginationAndFiltering() {
        mockMvc.perform(get(baseUrl + "?surnameLike=" + PassengerServiceImplTestUtil.SURNAME_LIKE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].email").value(PassengerServiceImplTestUtil.FIRST_EMAIL))
                .andExpect(jsonPath("$.content[1].email").value(PassengerServiceImplTestUtil.SECOND_EMAIL));
    }

    @SneakyThrows
    @Test
    void testCreatePassengerWithInvalidEmail() {
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
                .andExpect(jsonPath("$.message").value("{email=Invalid email format.}"));
    }

    @SneakyThrows
    @Test
    void testUpdatePassengerInDatabase() {
        PassengerUpdateRequestDto updateDto = PassengerServiceImplTestUtil.getUpdatePassengerRequestDto();
        String json = new ObjectMapper().writeValueAsString(updateDto);

        mockMvc.perform(patch(baseUrl + "/{id}", PassengerServiceImplTestUtil.FIRST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        Passenger updatedPassenger = passengerRepository.findById(PassengerServiceImplTestUtil.FIRST_ID).orElseThrow();
        assertThat(updatedPassenger.getFirstname()).isEqualTo(PassengerServiceImplTestUtil.UPDATED_FIRSTNAME);
        assertThat(updatedPassenger.getEmail()).isEqualTo(PassengerServiceImplTestUtil.UPDATED_EMAIL);
    }

    @SneakyThrows
    @Test
    void testLocalizedEsErrorMessage() {
        mockMvc.perform(get(baseUrl + "/{id}", PassengerServiceImplTestUtil.NON_EXISTING_ID)
                        .header("Accept-Language", "es"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Pasajero no encontrado"));
    }

    @SneakyThrows
    @Test
    void testLocalizedDeErrorMessage() {
        mockMvc.perform(get(baseUrl + "/{id}", PassengerServiceImplTestUtil.NON_EXISTING_ID)
                        .header("Accept-Language", "de"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Passagier nicht gefunden"));
    }

    @SneakyThrows
    @Test
    void testGetAllPassengersWithNoMatchingFilter() {
        mockMvc.perform(get(baseUrl + "?surnameLike=NonExistentSurname"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(0));
    }

}