package pe.edu.upc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "suscripciones")
public class Suscripcion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idSuscripcion;
	
	@Pattern(regexp = "[^!\"#$%&'()*+,-./:;<=>?@^_`{|}~]+", message = "El nombre del producto no puede contener un número")
	@Pattern(regexp = "[^0-9]+", message = "El nombre del producto no puede contener un número")
	@Column(name = "nombreSuscripcion", nullable = false, length = 45)
	private String nombreSuscripcion;
	
	@ManyToOne
	@JoinColumn(name = "idUsuario", nullable = false)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "idTipodepago", nullable = false)
	private Tipodepago tipodepago;
	public Suscripcion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Suscripcion(int idSuscripcion,
			@Pattern(regexp = "[^!\"#$%&'()*+,-./:;<=>?@^_`{|}~]+", message = "El nombre del producto no puede contener un número") @Pattern(regexp = "[^0-9]+", message = "El nombre del producto no puede contener un número") String nombreSuscripcion,
			Usuario usuario,Tipodepago tipodepago) {
		super();
		this.idSuscripcion = idSuscripcion;
		this.nombreSuscripcion = nombreSuscripcion;
		this.usuario = usuario;
		this.tipodepago=tipodepago;
	}

	public int getIdSuscripcion() {
		return idSuscripcion;
	}

	public void setIdSuscripcion(int idSuscripcion) {
		this.idSuscripcion = idSuscripcion;
	}

	public String getNombreSuscripcion() {
		return nombreSuscripcion;
	}

	public void setNombreSuscripcion(String nombreSuscripcion) {
		this.nombreSuscripcion = nombreSuscripcion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Tipodepago getTipodepago() {
		return tipodepago;
	}

	public void setTipodepago(Tipodepago tipodepago) {
		this.tipodepago = tipodepago;
	}
}
