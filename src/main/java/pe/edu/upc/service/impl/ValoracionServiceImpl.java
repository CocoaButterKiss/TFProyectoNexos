package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Valoracion;
import pe.edu.upc.repository.IValoracionRepository;
import pe.edu.upc.service.IValoracionService;

@Service
public class ValoracionServiceImpl implements IValoracionService{

	@Autowired
	private IValoracionRepository vR;

	
	public boolean insert(Valoracion valoracion) {
		Valoracion objValoracion=vR.save(valoracion);
		if(objValoracion== null) {
			return false;
		}else {
			return true;
		}
	}

	
	public List<Valoracion> list() {
		return vR.findAll();
	}

	@Transactional(readOnly = true)
	public Valoracion listarId(int idValoracion) {
		Optional<Valoracion> op=vR.findById(idValoracion);
		return op.isPresent() ? op.get() : new Valoracion();
	}

	@Override
	public List<Valoracion> findBynombreValoracion(String nombre) {
		// TODO Auto-generated method stub
		return vR.findBynombreValoracion(nombre);
	}
}
