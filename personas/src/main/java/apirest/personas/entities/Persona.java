package apirest.personas.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "Personas")
public class Persona extends RepresentationModel<Persona> {

	@ApiModelProperty(value="Id de Persona", required=true, example="1", position=1)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long personid;
	
	@ApiModelProperty(value="Person name", required=true, example="MyName", position=2)
	@NotBlank(message = "Name cannot be empty")
	private String name;
	
	@ApiModelProperty(value="Person address", required=false, example="21 High Road", position=3)
	@NotBlank(message = "Address cannot be empty")
	private String address;
	
	@ApiModelProperty(value="Person phone", required=false, example="634339445", position=4)
	@NotBlank(message = "Phone cannot be empty")
	private String phone;

	Persona() {
	}

	public Persona(String name, String address, String phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	
	public Persona(Long personid, String name, String address, String phone) {
		this.personid = personid;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public Long getPersonid() {
		return personid;
	}

	public void setPersonid(Long personid) {
		this.personid = personid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
