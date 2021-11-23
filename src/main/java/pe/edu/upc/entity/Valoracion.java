package pe.edu.upc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;


@Entity
@Table(name = "valoraciones")
public class Valoracion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idValoracion;
	
	@Pattern(regexp = "[^!\"#$%&'()*+,-./:;<=>?@^_`{|}~]+", message = "El nombre del producto no puede contener un número")
	@Pattern(regexp = "[^0-9]+", message = "El nombre del producto no puede contener un número")
	@Column(name = "nombreValoracion", nullable = false, length = 45)
	private String nombreValoracion;
	
	@DecimalMin("1.00")
	@DecimalMax("10.00")
	@Column(name = "puntos", columnDefinition = "Decimal(8,2)", nullable = false)
	private Double puntos;
	
	@ManyToOne
	@JoinColumn(name = "idReunion", nullable = false)
	private Reunion reunion;

	public Valoracion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Valoracion(int idValoracion,
			@Pattern(regexp = "[^!\"#$%&'()*+,-./:;<=>?@^_`{|}~]+", message = "El nombre del producto no puede contener un número") @Pattern(regexp = "[^0-9]+", message = "El nombre del producto no puede contener un número") String nombreValoracion,
			@DecimalMin("1.00") @DecimalMax("5.00") Double puntos, Reunion reunion) {
		super();
		this.idValoracion = idValoracion;
		this.nombreValoracion = nombreValoracion;
		this.puntos = puntos;
		this.reunion = reunion;
	}

	public int getIdValoracion() {
		return idValoracion;
	}

	public void setIdValoracion(int idValoracion) {
		this.idValoracion = idValoracion;
	}

	public String getNombreValoracion() {
		return nombreValoracion;
	}

	public void setNombreValoracion(String nombreValoracion) {
		this.nombreValoracion = nombreValoracion;
	}

	public Double getPuntos() {
		return puntos;
	}

	public void setPuntos(Double puntos) {
		this.puntos = puntos;
	}

	public Reunion getReunion() {
		return reunion;
	}

	public void setReunion(Reunion reunion) {
		this.reunion = reunion;
	}
	
	
}
