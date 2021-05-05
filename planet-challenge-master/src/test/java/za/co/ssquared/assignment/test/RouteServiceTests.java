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

import za.co.ssquared.assignment.model.Route;
import za.co.ssquared.assignment.service.IRouteService;
import za.co.ssquared.assignment.service.RouteService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RouteServiceTests {

    @TestConfiguration
    static class RouteServiceTestContextConfiguration {
 
        @Bean
        public IRouteService routeService() {
            return new RouteService();
        }
    }
    
	@Autowired
    private IRouteService routeService;  
	
	@Test
    public void testSaveRoute() {

    	Route route = new Route(100, "T1", "T2", 100);
    	routeService.save(route);
    	Route route2 = routeService.findByRoute(100);
        assertNotNull(route2);
        assertEquals(route2.getRoute(), route.getRoute());
        assertEquals(route2.getPlanetOrigin(), route.getPlanetOrigin());
        assertEquals(route2.getPlanetDestination(), route.getPlanetDestination());
        assertEquals(route2.getDistance(), route.getDistance());
    }

    @Test
    public void testPutRoute() {

        Route route = new Route(100, "T1", "T2", 100);
        routeService.save(route);
        Route route2 = routeService.findByRoute(100);
        assertNotNull(route2);
        route2.setDistance(200);
        routeService.save(route2);
        Route updatedRoute = routeService.findByRoute(100);
        assertThat(updatedRoute.getDistance()).isEqualTo(200);
    }

    @Test
    public void findAllRoutes() {
        Route route = new Route(100, "T1", "T2", 100);
        routeService.save(route);
        assertNotNull(routeService.findAll());
    }
    
    @Test
    public void deleteById() {
    	Route route = new Route(100, "T1", "T2", 100);
    	routeService.save(route);
        assertNotNull(routeService.findByRoute(100));
        routeService.deleteById(100);
        assertNull(routeService.findByRoute(100));
    }
}