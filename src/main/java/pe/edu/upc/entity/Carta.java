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
@Table(name = "cartas")
public class Carta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Ingrese su nombre del empleado*")
	@Column(name = "name", nullable = false, length = 90)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "idRestaurante", nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name = "idPlato", nullable = false)
	private Plato plato;
	

	@Size(min = 1, max = 2)
	@NotEmpty(message = "Ingrese su precio")
	@Column(name = "precioplato", nullable = false, length = 2)
	private String precioplato;
	

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


	public Restaurante getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}
	
	public Plato getPlato() {
		return plato;
	}
	public void setPlato(Plato plato) {
		this.plato = plato;
	}
	public String getPrecioplato() {
		return precioplato;
	}
	public void setPrecioplato(String precioplato) {
		this.precioplato = precioplato;
	}
	
}
