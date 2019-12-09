package apirest.personas.services;

import java.util.List;

import apirest.personas.entities.Persona;

public interface PersonaService {
	
	public List<Persona> findAll();
	
	public Persona findById(Long personaId);
	
	public Persona saveNewPersona(Persona persona);
	
	public Persona savePersona(Persona persona);
	
	public void deleteById(Long personaId);
}
