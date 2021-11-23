package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.Suscripcion;


public interface ISuscripcionService {

public boolean insert(Suscripcion suscripcion);
	
	List<Suscripcion> list();
	
	Suscripcion listarId(int idSuscripcion);
	
	List<Suscripcion> findBynombreSuscripcion(String nombre);
}
