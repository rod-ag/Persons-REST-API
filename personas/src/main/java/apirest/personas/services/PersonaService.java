package apirest.personas.services;

import java.util.List;

import apirest.personas.entities.Persona;

/**
 * The PersonaService Interface
 */
public interface PersonaService {

    /**
     * findAll return all persons
     * 
     * @return List<Persona>
     */
    public List<Persona> findAll();

    /**
     * findById find persona by Id
     *
     * @param Long persona Id
     * @return Persona
     */
    public Persona findById(Long personaId);

    /**
     * saveNewPersona create a new person
     *
     * @param Persona persona
     * @return Persona
     */
    public Persona saveNewPersona(Persona persona);

    /**
     * savePersona update a person
     *
     * @param Persona persona
     * @return Persona
     */
    public Persona savePersona(Persona persona);

    /**
     * deleteById delete a person by Id
     *
     * @param Long persona Id
     */
    public void deleteById(Long personaId);
}
