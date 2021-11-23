package pe.edu.upc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Distrito;
import pe.edu.upc.repository.IDistritoRepository;
import pe.edu.upc.service.IDistritoService;

@Service
public class DistritoServiceImplement implements IDistritoService{

	@Autowired
	private IDistritoRepository dR;
	
	@Override
	public Integer insert(Distrito distrito) {
		int rpta= dR.distritosExistentes(distrito.getNombreDistrito());
		if(rpta==0) {
			dR.save(distrito);
		}
		return rpta;
	}
	
	@Override
	public List<Distrito> list() {
		
		return dR.findAll();
		}
}
