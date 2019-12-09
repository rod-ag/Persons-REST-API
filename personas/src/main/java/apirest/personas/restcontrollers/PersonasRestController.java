package apirest.personas.restcontrollers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import apirest.personas.entities.Persona;
import apirest.personas.exceptions.BadRequestException;
import apirest.personas.exceptions.InternalServerErrorException;
import apirest.personas.exceptions.ResourceNotFoundException;
import apirest.personas.services.PersonaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The PersonasRestController Class
 */
@EnableResourceServer
@RestController
@RequestMapping("/api")
public class PersonasRestController extends ResourceServerConfigurerAdapter {

    /** The Persona Service interface */
    @Autowired
    PersonaService personaService;
    
    /**
     * getAll
     * 
     * @return List<Persona>
     */
    @CrossOrigin
    @ApiOperation(value="Get the list of all persons")
    @ApiResponses(value={
        @ApiResponse(code=200, message="OK"),
        @ApiResponse(code=404, message="Not Found", response=ResourceNotFoundException.class),
        @ApiResponse(code=500, message="Internal Server Error", response=InternalServerErrorException.class)})
    @GetMapping("/personas")
    public List<Persona> getAll() {
        return personaService.findAll();
    }
    
    /**
     * getPersona by Id
     * 
     * @param personaId
     * @return Persona
     */
    @CrossOrigin
    @ApiOperation(value="Get person by Id")
    @ApiResponses(value={
        @ApiResponse(code=200, message="OK"),
        @ApiResponse(code=404, message="Not Found", response=ResourceNotFoundException.class),
        @ApiResponse(code=500, message="Internal Server Error", response=InternalServerErrorException.class)})
    @GetMapping("/personas/{personaid}")
    public Persona getPersona(@PathVariable(value = "personaid") Long personaId) {
        Persona persona = null;
        
        try {
            persona = personaService.findById(personaId);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException();
        }
        
        // Hateoas constraint: Include self rel link and collection rel link in the response
        addLinks(persona);
        
        return persona;
    }

    /**
     * newPersona create a person resource
     * 
     * @param persona
     * @return ResponseEntity<Persona>
     */
    @CrossOrigin
    @ApiOperation(value="Create Person")
    @ApiResponses(value={
        @ApiResponse(code=201, message="Created"),
        @ApiResponse(code=400, message="Bad Request", response=BadRequestException.class),
        @ApiResponse(code=500, message="Internal Server Error", response=InternalServerErrorException.class)})
    @PostMapping("/personas")
    public ResponseEntity<Persona> newPersona(@RequestBody @Valid Persona persona) {
        if (persona.getPersonid() != null ) {
            throw new BadRequestException();
        }
        
        personaService.saveNewPersona(persona);
        
        // Hateoas constraint: include Location header in the response with the URI of the newly created resource
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(persona.getPersonid())
            .toUri();
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        
        return new ResponseEntity<Persona>(persona, responseHeaders, HttpStatus.CREATED);
    }

    /**
     * actualizaPersona update a person resource
     * 
     * @param persona
     * @return Persona
     */
    @CrossOrigin
    @ApiOperation(value="Update Person")
    @ApiResponses(value={
        @ApiResponse(code=200, message="OK"),
        @ApiResponse(code=400, message="Bad Request", response=BadRequestException.class),
        @ApiResponse(code=404, message="Not Found", response=ResourceNotFoundException.class),
        @ApiResponse(code=500, message="Internal Server Error", response=InternalServerErrorException.class)})
    @PutMapping("/personas")
    public Persona actualizaPersona(@RequestBody @Valid Persona persona) {
        if (persona.getPersonid() == null ) {
            throw new BadRequestException();
        }
        
        Persona pers = null;
        
        try {
            pers = personaService.savePersona(persona);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException();
        }
        
        return pers;
    }

    /**
     * deletePersona eliminate a person resource by Id
     * 
     * @param personaId
     * @return String
     */
    @CrossOrigin
    @ApiOperation(value="Delete Person")
    @ApiResponses(value={
        @ApiResponse(code=200, message="OK"),
        @ApiResponse(code=400, message="Bad Request", response=BadRequestException.class),
        @ApiResponse(code=404, message="Not Found", response=ResourceNotFoundException.class),
        @ApiResponse(code=500, message="Internal Server Error", response=InternalServerErrorException.class)})
    @DeleteMapping("/personas/{personaid}")
    public String deletePersona(@PathVariable(value = "personaid") Long personaId) {
        try {
            personaService.deleteById(personaId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException();
        }
        
        return "Eliminada persona con Id: " + personaId;
    }
    
    /**
     * addLinks to JSON response
     * 
     * @param Persona resource
     */
    private void addLinks(Persona resource) {
        Persona persona = methodOn(PersonasRestController.class).getPersona(resource.getPersonid());
        Link link1 = linkTo(persona).withSelfRel();
        Link link2 = linkTo(methodOn(PersonasRestController.class).getAll()).withRel("personas");
        resource.add(link1);
        resource.add(link2);
    }
    
    /**
     * Secure POST, PUT, DELETE endpoints, require full authorization with credentials and user roles
     *
     * @param HttpSecurity http
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/oauth/token", "/oauth/authorize**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/personas").permitAll()
            .antMatchers(HttpMethod.GET, "/api/personas/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/personas").access("hasRole('USER')")
            .antMatchers(HttpMethod.PUT, "/api/personas").access("hasRole('USER')")
            .antMatchers(HttpMethod.DELETE, "/api/personas/**").access("hasRole('ADMIN')");
    }
}
