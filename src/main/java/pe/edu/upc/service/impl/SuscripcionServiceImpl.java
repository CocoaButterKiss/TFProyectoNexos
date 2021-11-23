package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Suscripcion;
import pe.edu.upc.repository.ISuscripcionRepository;
import pe.edu.upc.service.ISuscripcionService;

@Service
public class SuscripcionServiceImpl implements ISuscripcionService {

	@Autowired
	private ISuscripcionRepository sR;
	
	
	
	public boolean insert(Suscripcion suscripcion) {
		Suscripcion objSuscripcion= sR.save(suscripcion);
		if(objSuscripcion== null) {
			return false;
		}else {
			return true;
		}
	}

	
	public List<Suscripcion> list() {
		// TODO Auto-generated method stub
		return sR.findAll();
	}

	@Transactional(readOnly = true)
	public Suscripcion listarId(int idSuscripcion) {
		Optional<Suscripcion> op=sR.findById(idSuscripcion);
		return op.isPresent() ? op.get() : new Suscripcion();
	}

	@Override
	public List<Suscripcion> findBynombreSuscripcion(String nombre) {
		// TODO Auto-generated method stub
		return sR.findBynombreSuscripcion(nombre);
	}

	
}
