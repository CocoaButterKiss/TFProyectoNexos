package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.Valoracion;

public interface IValoracionService {

public boolean insert(Valoracion valoracion);
	
	List<Valoracion> list();
	
	Valoracion listarId(int idValoracion);
	
	List<Valoracion> findBynombreValoracion(String nombre);
}
