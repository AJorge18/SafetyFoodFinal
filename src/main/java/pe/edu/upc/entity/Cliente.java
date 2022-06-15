package pe.edu.upc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name = "clientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Ingrese nombre del cliente*")
	@Column(name = "name", nullable = false, length = 90)
	private String name;	
	
	@NotEmpty(message = "Ingrese apellido del cliente")
	@Column(name = "apellido", nullable = false, length = 70)
	private String apellido;
	
	@Size(min = 8, max = 8)
	@NotEmpty(message = "Ingrese su dni del cliente")
	@Column(name = "dni", nullable = false, length = 8)
	private String dni;	
	
	@NotEmpty(message = "Ingrese el correo del cliente")
	@Column(name = "correo", nullable = false, length = 70)
	private String correo;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}	
}
