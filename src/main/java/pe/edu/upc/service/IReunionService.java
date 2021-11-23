package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.Reunion;

public interface IReunionService {

	public boolean insert(Reunion reunion);
	
	List<Reunion> list();
	
	Reunion listarId(int idReunion);
	
	List<Reunion> findBynombreReu(String nombre);
}
