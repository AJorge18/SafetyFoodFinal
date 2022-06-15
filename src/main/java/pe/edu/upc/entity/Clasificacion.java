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
@Table(name = "clasificaciones")
public class Clasificacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
  
	@NotEmpty(message = "Debe ingresar clasificacion*")
	@Column(name="clasificaciondesc",nullable = false,length = 70)
	private String clasificaciondesc;
	

	@ManyToOne
	@JoinColumn(name = "idCliente", nullable = false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "idRestaurante", nullable = false)
	private Restaurante restaurante;
	
	@Size(min = 1, max = 1)
	@NotEmpty(message = "Ingrese el numero de Clasificación")
	@Column(name = "numeroclasificacion", nullable = false, length = 1)
	private String numeroclasificacion;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClasificaciondesc() {
		return clasificaciondesc;
	}
	public void setClasificaciondesc(String clasificaciondesc) {
		this.clasificaciondesc = clasificaciondesc;
	}
	public Restaurante getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getNumeroclasificacion() {
		return numeroclasificacion;
	}
	public void setNumeroclasificacion(String numeroclasificacion) {
		this.numeroclasificacion = numeroclasificacion;
	}
}