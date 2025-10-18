package edu.eci.arsw.blueprints.test.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import edu.eci.arsw.blueprints.main.App;

/**
 * Test class updated to JUnit 5
 */
@SpringBootTest(classes = App.class)
@SpringJUnitConfig
public class ApplicationServicesTests {

    @Test
    public void contextLoads() {
    }

}