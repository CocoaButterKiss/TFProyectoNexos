package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.Usuario;

public interface IUsuarioService {

	public Integer insert(Usuario usuario);
	List<Usuario> list();
}
