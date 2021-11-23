package pe.edu.upc.service;

import java.util.List;


import pe.edu.upc.entity.Tipodepago;

public interface ITipodepagoService {

	public Integer insert(Tipodepago tipodepago);
	List<Tipodepago> list();
}
