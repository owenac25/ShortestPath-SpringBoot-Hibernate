package za.co.ssquared.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.ssquared.assignment.model.Planet;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, String> {

}
