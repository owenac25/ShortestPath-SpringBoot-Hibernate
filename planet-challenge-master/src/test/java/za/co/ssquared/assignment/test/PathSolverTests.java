package za.co.ssquared.assignment.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import za.co.ssquared.assignment.model.Planet;
import za.co.ssquared.assignment.model.Route;
import za.co.ssquared.assignment.service.IPlanetService;
import za.co.ssquared.assignment.service.IRouteService;
import za.co.ssquared.assignment.service.PlanetService;
import za.co.ssquared.assignment.service.RouteService;
import za.co.ssquared.assignment.util.PathSolver;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PathSolverTests {

    private List<Planet> nodes;
    private List<Route> edges;

    @TestConfiguration
    static class ServiceTestContextConfiguration {
 
        @Bean
        public IPlanetService planetService() {
            return new PlanetService();
        }
        
        @Bean
        public IRouteService routeService() {
            return new RouteService();
        }
    }
    
	@Autowired
    private IPlanetService planetService;  
	
	@Autowired
    private IRouteService routeService;

    @Test
    public void testLoad() throws Exception {
        nodes = new ArrayList<Planet>();
        edges = new ArrayList<Route>();
 
        Planet planet = new Planet("A", "Node_" + 0);
        nodes.add(planet);
        
        for (int i = 1; i < 6; i++) {
            planet = new Planet(""+i, "Node_" + i);
            nodes.add(planet);
        }
        
        addPath(1, "A", "1", 4);
        addPath(1, "A", "2", 2);
        addPath(2, "1", "2", 1);
        addPath(3, "1", "4", 3);
        addPath(4, "1", "3", 2);
        addPath(6, "2", "4", 2);
        addPath(7, "2", "3", 4);
        addPath(8, "3", "4", 1);

        planetService.saveAll(nodes);
        routeService.saveAll(edges);
       
        assertNotNull(planetService);
        assertNotNull(routeService);
        
        new PathSolver(planetService, routeService);
        
    }
 
    private void addPath(Integer pathId, String sourceNode, String destNode,double distance) {
        Route path = new Route(pathId,sourceNode, destNode, distance);
        edges.add(path);
    }
}