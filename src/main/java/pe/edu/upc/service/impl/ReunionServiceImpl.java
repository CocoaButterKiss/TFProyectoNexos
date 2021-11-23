package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Reunion;
import pe.edu.upc.repository.IReunionRepository;
import pe.edu.upc.service.IReunionService;

@Service
public class ReunionServiceImpl implements IReunionService{
	@Autowired
	private IReunionRepository rR;
	
	
	public boolean insert(Reunion reunion) {
		Reunion objReunion =rR.save(reunion);
		if(objReunion == null) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public List<Reunion> list(){
		return rR.findAll();
	}
	
	
	@Transactional(readOnly = true)
	public Reunion listarId(int idReunion) {
		Optional<Reunion> op=rR.findById(idReunion);
		return op.isPresent() ? op.get() : new Reunion();
	}


	@Override
	public List<Reunion> findBynombreReu(String nombre) {
		// TODO Auto-generated method stub
		return rR.findBynombreReu(nombre);
	}
	
}
