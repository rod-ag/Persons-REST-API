package apirest.personas.repositories;

import org.springframework.data.repository.CrudRepository;

import apirest.personas.entities.Persona;

public interface PersonaRepository extends CrudRepository<Persona, Long> {

}
