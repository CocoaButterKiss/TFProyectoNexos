package pe.edu.upc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="tipodepagos")
public class Tipodepago {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTipodepago;
	
	@Pattern(regexp = "[^!\"#$%&'()*+,-./:;<=>?@^_`{|}~]+", message = "El nombre del producto no puede contener un número")
	@Pattern(regexp = "[^0-9]+", message = "El nombre del producto no puede contener un número")
	@Column(name = "nombreTipodepago", length = 45, nullable = false)
	private String nombreTipodepago;

	public Tipodepago() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tipodepago(int idTipodepago,
			@Pattern(regexp = "[^!\"#$%&'()*+,-./:;<=>?@^_`{|}~]+", message = "El nombre del producto no puede contener un número") @Pattern(regexp = "[^0-9]+", message = "El nombre del producto no puede contener un número") String nombreTipodepago) {
		super();
		this.idTipodepago = idTipodepago;
		this.nombreTipodepago = nombreTipodepago;
	}

	public int getIdTipodepago() {
		return idTipodepago;
	}

	public void setIdTipodepago(int idTipodepago) {
		this.idTipodepago = idTipodepago;
	}

	public String getNombreTipodepago() {
		return nombreTipodepago;
	}

	public void setNombreTipodepago(String nombreTipodepago) {
		this.nombreTipodepago = nombreTipodepago;
	}
	
	
}
