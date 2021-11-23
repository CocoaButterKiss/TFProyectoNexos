package pe.edu.upc.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name = "reuniones")
public class Reunion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReunion;
	
	@Column(name = "nombreReu", nullable = false, length = 45)
	private String nombreReu;
	
	@Column(name = "descripcionReu", nullable = false, length = 80)
	private String descripcionReu;
	
	@Column(name = "direccionReu", nullable = false, length = 45)
	private String direccionReu;
	
	@NotNull(message = "La fecha es obligatoria")
	@Future(message = "La fecha debe estar en el futuro")
	@Column(name = "fechaReu")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaReu;
	
	@ManyToOne
	@JoinColumn(name = "idCategoria", nullable = false)
	private Categoria categoria; 
	
	@Positive
	@Column(name = "invitadosmaxReu", nullable = false)
	private int invitadosmaxReu;
	
	@ManyToOne
	@JoinColumn(name = "idUsuario", nullable = false)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "idDistrito", nullable = false)
	private Distrito distrito;
	

	public Reunion() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Reunion(int idReunion, String nombreReu, String descripcionReu, String direccionReu, Date fechaReu,
			int invitadosmaxReu, Usuario usuario, Categoria categoria, Distrito distrito) {
		super();
		this.idReunion = idReunion;
		this.nombreReu = nombreReu;
		this.descripcionReu = descripcionReu;
		this.direccionReu = direccionReu;
		this.fechaReu = fechaReu;
		this.invitadosmaxReu = invitadosmaxReu;
		this.usuario = usuario;
		this.categoria=categoria;
		this.distrito=distrito;
	}


	public int getIdReunion() {
		return idReunion;
	}


	public void setIdReunion(int idReunion) {
		this.idReunion = idReunion;
	}


	public String getNombreReu() {
		return nombreReu;
	}


	public void setNombreReu(String nombreReu) {
		this.nombreReu = nombreReu;
	}


	public String getDescripcionReu() {
		return descripcionReu;
	}


	public void setDescripcionReu(String descripcionReu) {
		this.descripcionReu = descripcionReu;
	}


	public String getDireccionReu() {
		return direccionReu;
	}


	public void setDireccionReu(String direccionReu) {
		this.direccionReu = direccionReu;
	}


	public Date getFechaReu() {
		return fechaReu;
	}


	public void setFechaReu(Date fechaReu) {
		this.fechaReu = fechaReu;
	}


	public int getInvitadosmaxReu() {
		return invitadosmaxReu;
	}


	public void setInvitadosmaxReu(int invitadosmaxReu) {
		this.invitadosmaxReu = invitadosmaxReu;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public Distrito getDistrito() {
		return distrito;
	}


	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}
	
	
	
	
}
