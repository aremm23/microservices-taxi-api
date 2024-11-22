package com.artsem.api.passengerservice.unit.repository;

import com.artsem.api.passengerservice.model.Passenger;
import com.artsem.api.passengerservice.repository.PassengerRepository;
import com.artsem.api.passengerservice.util.PassengerServiceImplTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Sql(scripts = "classpath:sql/schema-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class PassengerRepositoryTest {

    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    void testExistsByEmail() {
        Passenger passenger = PassengerServiceImplTestUtil.getFirstPassenger();
        passengerRepository.save(passenger);

        boolean exists = passengerRepository.existsByEmail(PassengerServiceImplTestUtil.FIRST_EMAIL);

        assertThat(exists).isTrue();
    }

    @Test
    void testFindIdByEmail() {
        Passenger passenger = PassengerServiceImplTestUtil.getSecondPassenger();
        Passenger savedPassenger = passengerRepository.save(passenger);

        Long foundId = passengerRepository.findIdByEmail(PassengerServiceImplTestUtil.SECOND_EMAIL);

        assertThat(foundId).isEqualTo(savedPassenger.getId());
    }

    @Test
    void testFindIdByEmailWhenEmailDoesNotExist() {
        Long foundId = passengerRepository.findIdByEmail("nonexistent@example.com");

        assertThat(foundId).isNull();
    }

    @Test
    void testSaveAndRetrievePassenger() {
        Passenger passenger = PassengerServiceImplTestUtil.getThirdPassenger();
        Passenger savedPassenger = passengerRepository.save(passenger);

        Optional<Passenger> retrievedPassenger = passengerRepository.findById(savedPassenger.getId());

        assertThat(retrievedPassenger).isPresent();
        assertThat(retrievedPassenger.get().getEmail()).isEqualTo(PassengerServiceImplTestUtil.THIRD_EMAIL);
        assertThat(retrievedPassenger.get().getFirstname()).isEqualTo(PassengerServiceImplTestUtil.THIRD_FIRSTNAME);
    }

    @Test
    void testFindAll() {
        passengerRepository.saveAll(List.of(
                PassengerServiceImplTestUtil.getFirstPassenger(),
                PassengerServiceImplTestUtil.getSecondPassenger(),
                PassengerServiceImplTestUtil.getThirdPassenger()
        ));

        List<Passenger> passengers = passengerRepository.findAll();
        assertThat(passengers.size()).isEqualTo(3);
    }

    @Test
    void testDeletePassenger() {
        Passenger passenger = PassengerServiceImplTestUtil.getFirstPassenger();
        Passenger savedPassenger = passengerRepository.save(passenger);

        passengerRepository.deleteById(savedPassenger.getId());

        Optional<Passenger> deletedPassenger = passengerRepository.findById(savedPassenger.getId());

        assertThat(deletedPassenger).isEmpty();
    }
}
