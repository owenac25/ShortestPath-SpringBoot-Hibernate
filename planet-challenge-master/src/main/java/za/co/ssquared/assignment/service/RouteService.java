package za.co.ssquared.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.co.ssquared.assignment.model.Route;
import za.co.ssquared.assignment.repository.RouteRepository;

/**
 * 
 * Service class to contain business logic 
 * Currently simple CRUD implementations but may change according to future requirements
 *
 */
@Service
public class RouteService implements IRouteService {

    @Autowired
    private RouteRepository repository;

    @Override
    public List<Route> findAll() {

        return repository.findAll();
    }

    @Override
    public Route findByRoute(Integer route) {
        return repository.findById(route).orElse(null);
    }
    
    @Override
    public void save(Route route) {
        repository.save(route);
    }  
    
    @Override   
    public void saveAll(List<Route> routes) {
        repository.saveAll(routes);
    }
    
    @Override   
	public List<Route> findByPlanetOrigin(String planetOrigin){
		return repository.findByPlanetOrigin(planetOrigin);
		
	}

	@Override
	public void deleteById(Integer route) {
		repository.deleteById(route);	
	}
      
}