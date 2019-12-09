package apirest.personas;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import apirest.personas.entities.Persona;
import apirest.personas.restcontrollers.PersonasRestController;
import apirest.personas.services.PersonaService;

/**
 * The PersonasApplicationTests Class
 */
@SpringBootTest
class PersonasApplicationTests {
    
    /** The MOCK_ID constant */
    private static final Long MOCK_ID = 10001L;

    /** The URI_PREFIX constant */
    private static final String URI_PREFIX = "/api/personas";
    
    /** The MockMvc object for Spring MVC test support */
    private MockMvc mockMvc;
    
    /** The object mapper */
    @Autowired
    private ObjectMapper mapper;
    
    /** The controller bean */
    @InjectMocks
    private PersonasRestController controller = new PersonasRestController();
    
    /** The personas service interface */
    @MockBean
    private PersonaService personaService;
    
    /**
     * Initialize mocks and MockMvc instance
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    
    /**
     * Test the getPersona by Id endpoint
     * 
     * @throws Exception
     */
    @Test
    public void getPersonaTest() throws Exception {
        Persona persona = new Persona(MOCK_ID,"name","address","1234 12345678");
        
        // Mock the service call
        Mockito.when(personaService.findById(Mockito.anyLong())).thenReturn(persona);
        
        // test the endpoint
        MvcResult result = mockMvc.perform(get(URI_PREFIX + "/" + MOCK_ID).contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        
        Assertions.assertNotNull(result);
    }
    
    /**
     * Test the newPersona endpoint
     * 
     * @throws Exception
     */
    @Test
    public void newPersonaTest() throws Exception {
        Persona persona = new Persona("name","address","1234 12345678");
        
        // Mock the service call
        Mockito.when(personaService.saveNewPersona(Mockito.any())).thenReturn(persona);
        
        // test the endpoint
        MvcResult result = mockMvc.perform(post(URI_PREFIX).content(mapper.writeValueAsString(persona))
            .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isCreated()).andReturn();
        
        Assertions.assertNotNull(result);
    }
    
    /**
     * Test the update person endpoint
     * 
     * @throws Exception
     */
    @Test
    public void actualizaPersonaTest() throws Exception {
        Persona persona = new Persona(MOCK_ID,"name","address","1234 12345678");
        
        // Mock the service call
        Mockito.when(personaService.savePersona(Mockito.any())).thenReturn(persona);
        
        // test the endpoint
        MvcResult result = mockMvc.perform(put(URI_PREFIX).content(mapper.writeValueAsString(persona))
            .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk()).andReturn();
        
        Assertions.assertNotNull(result);
    }
    
    /**
     * Test the delete Person by Id endpoint
     * 
     * @throws Exception
     */
    @Test
    public void deletePersonaTest() throws Exception {
        // Mock the service call
        Mockito.doNothing().when(personaService).deleteById(Mockito.anyLong());
        
        // test the endpoint
        MvcResult result = mockMvc.perform(delete(URI_PREFIX + "/" + MOCK_ID).contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk()).andReturn();
        
        Assertions.assertNotNull(result);
    }
}
