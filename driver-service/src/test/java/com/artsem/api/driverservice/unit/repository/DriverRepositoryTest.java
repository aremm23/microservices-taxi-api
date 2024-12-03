package com.artsem.api.driverservice.unit.repository;

import com.artsem.api.driverservice.model.Driver;
import com.artsem.api.driverservice.repository.DriverRepository;
import com.artsem.api.driverservice.util.DriverServiceImplTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = "classpath:sql/schema-test-driver.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DriverRepositoryTest {

    @Autowired
    private DriverRepository driverRepository;

    @Test
    void existsByEmail_existingEmail_returnsTrue() {
        Driver driver = DriverServiceImplTestUtil.getFirstDriverNoID();
        driverRepository.save(driver);

        boolean exists = driverRepository.existsByEmail(DriverServiceImplTestUtil.FIRST_EMAIL);
        assertTrue(exists);
    }

    @Test
    void existsByEmail_nonExistingEmail_returnsFalse() {
        boolean exists = driverRepository.existsByEmail(DriverServiceImplTestUtil.THIRD_EMAIL);
        assertFalse(exists);
    }

    @Test
    void findIdByEmail_existingEmail_returnsDriverId() {
        Driver driver = DriverServiceImplTestUtil.getFirstDriverNoID();
        Driver savedDriver = driverRepository.save(driver);

        Long driverId = driverRepository.findIdByEmail(DriverServiceImplTestUtil.FIRST_EMAIL);
        assertNotNull(driverId);
        assertEquals(savedDriver.getId(), driverId);
    }

    @Test
    void findIdByEmail_nonExistingEmail_returnsNull() {
        Long driverId = driverRepository.findIdByEmail(DriverServiceImplTestUtil.THIRD_EMAIL);
        assertNull(driverId);
    }
}
