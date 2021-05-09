package assignment.test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.owen.assignment.model.Route;
import com.owen.assignment.repository.RouteRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RouteRepoTests {

    @Autowired
    private RouteRepository routeRepository;

    @Test
    public void testSaveRoute() {

    	Route route = new Route(100, "T1", "T2", 100);
    	routeRepository.save(route);
    	Route route2 = routeRepository.findById(100).orElse(null);
        assertNotNull(route);
        assertEquals(route2.getRoute(), route.getRoute());
        assertEquals(route2.getPlanetOrigin(), route.getPlanetOrigin());
        assertEquals(route2.getPlanetDestination(), route.getPlanetDestination());
        assertEquals(route2.getDistance(), route.getDistance());
    }

    @Test
    public void testPutRoute() {

        Route route = new Route(100, "T1", "T2", 100);
        routeRepository.save(route);
        Route route2 = routeRepository.findById(100).orElse(null);
        assertNotNull(route2);
        route2.setDistance(200);
        routeRepository.save(route2);
        Route updatedRoute = routeRepository.findById(100).orElse(null);
        assertThat(updatedRoute.getDistance()).isEqualTo(200);
    }

    @Test
    public void findAllRoutes() {
        Route route = new Route(100, "T1", "T2", 100);
        routeRepository.save(route);
        assertNotNull(routeRepository.findAll());
    }
    
    @Test
    public void deleteById() {
    	Route route = new Route(100, "T1", "T2", 100);
    	routeRepository.save(route);
        assertNotNull(routeRepository.findById(100).orElse(null));
        routeRepository.deleteById(100);
        assertTrue(routeRepository.findById(100).isEmpty());
    }
}
