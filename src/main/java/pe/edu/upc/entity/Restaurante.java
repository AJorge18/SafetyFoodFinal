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
@Table(name = "restaurantes")
public class Restaurante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Ingrese su nombre del restaurante*")
	@Column(name = "name", nullable = false, length = 90)
	private String name;

	

	@ManyToOne
	@JoinColumn(name = "idDepartamento", nullable = false)
	private Departamento departamento;
	
	@ManyToOne
	@JoinColumn(name = "idDistrito", nullable = false)
	private Distrito distrito;
	
	@ManyToOne
	@JoinColumn(name = "idTipocertificado", nullable = false)
	private Tipocertificado tipocertificado;
	
	@ManyToOne
	@JoinColumn(name = "idTiporestaurante", nullable = false)
	private Tiporestaurante tiporestaurante;
	
	@NotEmpty(message = "Ingrese su Direccion")
	@Column(name = "direccionrestaurante", nullable = false, length = 70)
	private String direccionrestaurante;
	
	@NotEmpty(message = "Ingrese el protocolo del restaurante")
	@Column(name = "protocolo", nullable = false, length = 70)
	private String protocolo;
	
	@Column(name = "estado", nullable = false)
	private String estado;
	
	@Size(min = 9, max = 9)
	@NotEmpty(message = "Ingrese el numero del restaurante")
	@Column(name = "numerorestaurante", nullable = false, length = 9)
	private String numerorestaurante;
	

	@Size(min = 1, max = 2)
	@NotEmpty(message = "Ingrese el aforo total")
	@Column(name = "aforototal", nullable = false, length = 2)
	private String aforototal;
	
	@Size(min = 1, max = 2)
	@NotEmpty(message = "Ingrese el aforo actual")
	@Column(name = "aforoactual", nullable = false, length = 2)
	private String aforoactual;
	
	

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
	
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public Distrito getDistrito() {
		return distrito;
	}
	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}
	
	public Tipocertificado getTipocertificado() {
		return tipocertificado;
	}
	public void setTipocertificado(Tipocertificado tipocertificado) {
		this.tipocertificado = tipocertificado;
	}
	
	public Tiporestaurante getTiporestaurante() {
		return tiporestaurante;
	}
	public void setTiporestaurante(Tiporestaurante tiporestaurante) {
		this.tiporestaurante = tiporestaurante;
	}
	public String getDireccionrestaurante() {
		return direccionrestaurante;
	}
	public void setDireccionrestaurante(String direccionrestaurante) {
		this.direccionrestaurante = direccionrestaurante;
	}
	
	public String getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getNumerorestaurante() {
		return numerorestaurante;
	}
	public void setNumerorestaurante(String numerorestaurante) {
		this.numerorestaurante = numerorestaurante;
	}
	
	public String getAforototal() {
		return aforototal;
	}
	public void setAforototal(String aforototal) {
		this.aforototal = aforototal;
	}
	public String getAforoactual() {
		return aforoactual;
	}
	public void setAforoactual(String aforoactual) {
		this.aforoactual = aforoactual;
	}
	
	


	
}
