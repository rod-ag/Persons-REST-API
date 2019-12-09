package apirest.personas.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apirest.personas.entities.Persona;
import apirest.personas.repositories.PersonaRepository;
import apirest.personas.services.PersonaService;

/**
 * The PersonaService implementaion class
 */
@Service
public class PersonaServiceImpl implements PersonaService {

    /** The PersonaRepository */
    @Autowired
    PersonaRepository personaRepo;

    /**
     * findAll return all persons
     * 
     * @return List<Persona>
     */
    @Override
    public List<Persona> findAll() {
        return (List<Persona>) personaRepo.findAll();
    }
    
    /**
     * findById find persona by Id
     *
     * @param Long persona Id
     * @return Persona
     */
    @Override
    public Persona findById(Long personaId) {
        return personaRepo.findById(personaId).get();
    }

    /**
     * saveNewPersona create a new person
     *
     * @param Persona persona
     * @return Persona
     */
    @Override
    public Persona saveNewPersona(Persona persona) {
        return personaRepo.save(persona);
    }

    /**
     * savePersona update a person
     *
     * @param Persona persona
     * @return Persona
     */
    @Override
    public Persona savePersona(Persona persona) {
        return personaRepo.save(persona);
    }

    /**
     * deleteById delete a person by Id
     *
     * @param Long persona Id
     */
    @Override
    public void deleteById(Long personaId) {
        personaRepo.deleteById(personaId);
    }
}
