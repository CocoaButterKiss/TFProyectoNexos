package pe.edu.upc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Categoria;
import pe.edu.upc.repository.ICategoriaRepository;
import pe.edu.upc.service.ICategoriaService;

@Service
public class CategoriaServiceImplement implements ICategoriaService{

	@Autowired
	private ICategoriaRepository cR;

	@Override
	public Integer insert(Categoria categoria) {
		int rpta= cR.categoriasExistentes(categoria.getNombreCategoria());
		if(rpta==0) {
			cR.save(categoria);
		}
		return rpta;
	}

	@Override
	public List<Categoria> list() {
		// TODO Auto-generated method stub
		return cR.findAll();
	}
}
