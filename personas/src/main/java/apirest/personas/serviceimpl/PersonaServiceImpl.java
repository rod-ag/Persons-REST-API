package apirest.personas.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apirest.personas.entities.Persona;
import apirest.personas.repositories.PersonaRepository;
import apirest.personas.services.PersonaService;

@Service
public class PersonaServiceImpl implements PersonaService {
	
	@Autowired
	PersonaRepository personaRepo;

	@Override
	public List<Persona> findAll() {
		return (List<Persona>) personaRepo.findAll();
	}

	@Override
	public Persona findById(Long personaId) {
		return personaRepo.findById(personaId).get();
	}

	@Override
	public Persona saveNewPersona(Persona persona) {
		return personaRepo.save(persona);
	}

	@Override
	public Persona savePersona(Persona persona) {
		return personaRepo.save(persona);
	}
	
	@Override
	public void deleteById(Long personaId) {
		personaRepo.deleteById(personaId);
	}
}
