package za.co.ssquared.assignment.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import za.co.ssquared.assignment.model.Planet;
import za.co.ssquared.assignment.service.IPlanetService;
import za.co.ssquared.assignment.service.PlanetService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlanetServiceTests {

    @TestConfiguration
    static class PlanetServiceTestContextConfiguration {
 
        @Bean
        public IPlanetService planetService() {
            return new PlanetService();
        }
    }
    
	@Autowired
    private IPlanetService planetService;  
	
	@Test
    public void testSavePlanet() {

    	Planet planet = new Planet("T1", "Test1");
    	planetService.save(planet);
    	Planet planet2 = planetService.findByPlanetNode("T1");
        assertNotNull(planet2);
        assertEquals(planet2.getPlanetNode(), planet.getPlanetNode());
        assertEquals(planet2.getPlanetName(), planet.getPlanetName());
    }

    @Test
    public void testPutPlanet() {

        Planet planet = new Planet("T1", "Test1");
        planetService.save(planet);
        Planet planet2 = planetService.findByPlanetNode("T1");
        assertNotNull(planet2);
        planet2.setDistance(200);
        planetService.save(planet2);
        Planet updatedPlanet = planetService.findByPlanetNode("T1");
        assertThat(updatedPlanet.getDistance()).isEqualTo(200);
    }

    @Test
    public void findAllPlanets() {
        Planet planet = new Planet("T1", "Test1");
        planetService.save(planet);
        assertNotNull(planetService.findAll());
    }
    
    @Test
    public void deleteByPlanetNode() {
        Planet planet = new Planet("T1", "Test1");
        planetService.save(planet);
        planetService.deleteByPlanetNode("T1");
        assertNull(planetService.findByPlanetNode("T1"));
    }
}
