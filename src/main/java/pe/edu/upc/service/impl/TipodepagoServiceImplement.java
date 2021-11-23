package pe.edu.upc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Tipodepago;
import pe.edu.upc.repository.ITipodepagoRepository;
import pe.edu.upc.service.ITipodepagoService;

@Service
public class TipodepagoServiceImplement implements ITipodepagoService{

	@Autowired 
	private ITipodepagoRepository tR;
	
	@Override
	public Integer insert(Tipodepago tipodepago) {
		int rpta= tR.tipodepagosExistentes(tipodepago.getNombreTipodepago());
		if(rpta==0) {
			tR.save(tipodepago);
		}
		return rpta;
	}

	@Override
	public List<Tipodepago> list() {
		// TODO Auto-generated method stub
		return tR.findAll();
	}

}
