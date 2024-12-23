package com.artsem.api.driverservice.unit.repository;

import com.artsem.api.driverservice.model.Car;
import com.artsem.api.driverservice.repository.CarRepository;
import com.artsem.api.driverservice.util.CarServiceImplTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Sql(scripts = "classpath:sql/schema-test-car.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    void testExistsByLicensePlate() {
        Car car = CarServiceImplTestUtil.getFirstCar();
        carRepository.save(car);

        boolean exists = carRepository.existsByLicensePlate(CarServiceImplTestUtil.FIRST_LICENSE_PLATE);

        assertThat(exists).isTrue();
    }

    @Test
    void testExistsByLicensePlateWhenNotExists() {
        boolean exists = carRepository.existsByLicensePlate(CarServiceImplTestUtil.FIRST_LICENSE_PLATE);

        assertThat(exists).isFalse();
    }

    @Test
    void testFindIdByLicensePlate() {
        Car car = CarServiceImplTestUtil.getFirstCar();
        Car savedCar = carRepository.save(car);

        Long foundId = carRepository.findIdByLicensePlate(CarServiceImplTestUtil.FIRST_LICENSE_PLATE);

        assertThat(foundId).isEqualTo(savedCar.getId());
    }

    @Test
    void testFindIdByLicensePlateWhenNotExists() {
        Long foundId = carRepository.findIdByLicensePlate(CarServiceImplTestUtil.FIRST_LICENSE_PLATE);

        assertThat(foundId).isNull();
    }
}
