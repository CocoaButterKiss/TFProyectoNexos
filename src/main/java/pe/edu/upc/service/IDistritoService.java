package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.Distrito;

public interface IDistritoService {

	public Integer insert(Distrito distrito);
	List<Distrito> list();
}
