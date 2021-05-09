package com.owen.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.owen.assignment.model.Planet;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, String> {

}
