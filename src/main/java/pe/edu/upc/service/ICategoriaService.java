package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.Categoria;

public interface ICategoriaService {

	public Integer insert(Categoria categoria);
	List<Categoria> list();
}
