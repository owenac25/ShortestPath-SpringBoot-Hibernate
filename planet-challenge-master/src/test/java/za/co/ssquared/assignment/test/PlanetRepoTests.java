package za.co.ssquared.assignment.test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import za.co.ssquared.assignment.model.Planet;
import za.co.ssquared.assignment.repository.PlanetRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlanetRepoTests {

    @Autowired
    private PlanetRepository planetRepository;

    @Test
    public void testSavePlanet() {

    	Planet planet = new Planet("T1", "Test1");
    	planetRepository.save(planet);
    	Planet planet2 = planetRepository.findById("T1").orElse(null);
        assertNotNull(planet);
        assertEquals(planet2.getPlanetNode(), planet.getPlanetNode());
        assertEquals(planet2.getPlanetName(), planet.getPlanetName());
    }

    @Test
    public void testPutPlanet() {

        Planet planet = new Planet("T1", "Test1");
        planetRepository.save(planet);
        Planet planet2 = planetRepository.findById("T1").orElse(null);
        assertNotNull(planet);
        planet2.setPlanetName("Test2Updated");
        planetRepository.save(planet2);
        Planet updatedPlanet = planetRepository.findById("T1").orElse(null);
        assertThat(updatedPlanet.getPlanetName()).isEqualTo("Test2Updated");
    }

    @Test
    public void testDeletePlanet() {
        Planet planet = new Planet("T1", "Test1");
        planetRepository.save(planet);
        assertNotNull(planetRepository.findById("T1").orElse(null));       
        planetRepository.deleteById("T1");
        assertTrue(planetRepository.findById("T1").isEmpty());
    }

    @Test
    public void findAllPlanets() {
        Planet planet = new Planet("T1", "Test1");
        planetRepository.save(planet);
        assertNotNull(planetRepository.findAll());
    }
}