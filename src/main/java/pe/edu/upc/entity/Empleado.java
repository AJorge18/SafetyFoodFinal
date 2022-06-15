package pe.edu.upc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "empleados")
public class Empleado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Ingrese su nombre del empleado*")
	@Column(name = "name", nullable = false, length = 90)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "idTipocertificado", nullable = false)
	private Tipocertificado tipocertificado;
	
	@ManyToOne
	@JoinColumn(name = "idRestaurante", nullable = false)
	private Restaurante restaurante;
	
	@NotEmpty(message = "Ingrese su apellido del empleado*")
	@Column(name = "lastname", nullable = false, length = 90)
	private String lastname;
	
	@Size(min = 8, max = 8)
	@NotEmpty(message = "Ingrese su dni*")
	@Column(name = "dni", nullable = false, length = 8)
	private String dni;
	
	@NotEmpty(message = "Ingrese su correo del empleado*")
	@Column(name = "correo", nullable = false, length = 90)
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

	public Tipocertificado getTipocertificado() {
		return tipocertificado;
	}
	public void setTipocertificado(Tipocertificado tipocertificado) {
		this.tipocertificado = tipocertificado;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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
