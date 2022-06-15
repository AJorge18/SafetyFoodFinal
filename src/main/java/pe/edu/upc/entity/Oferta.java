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
@Table(name = "ofertas")
public class Oferta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Ingrese su nombre del empleado*")
	@Column(name = "name", nullable = false, length = 90)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "idPlato", nullable = false)
	private Plato plato;
	
	@ManyToOne
	@JoinColumn(name = "idRestaurante", nullable = false)
	private Restaurante restaurante;

	@Size(min = 1, max = 2)
	@NotEmpty(message = "Ingrese su precio anterior")
	@Column(name = "precioantesplato", nullable = false, length = 2)
	private String precioantesplato;
	
	@Size(min = 1, max = 2)
	@NotEmpty(message = "Ingrese su precio actual")
	@Column(name = "precioactualplato", nullable = false, length = 2)
	private String precioactualplato;
	
	@NotEmpty(message = "Inserte la ubicación de la imagen")
	@Column(name = "imagenoferta", nullable = false, length = 70)
	private String imagenoferta;
	

	

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
	public Plato getPlato() {
		return plato;
	}
	public void setPlato(Plato plato) {
		this.plato = plato;
	}
	public Restaurante getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}
	public String getPrecioantesplato() {
		return precioantesplato;
	}
	public void setPrecioantesplato(String precioantesplato) {
		this.precioantesplato = precioantesplato;
	}
	public String getPrecioactualplato() {
		return precioactualplato;
	}
	public void setPrecioactualplato(String precioactualplato) {
		this.precioactualplato = precioactualplato;
	}
	public String getImagenoferta() {
		return imagenoferta;
	}
	public void setImagenoferta(String imagenoferta) {
		this.imagenoferta = imagenoferta;
	}
	
	
	
}
