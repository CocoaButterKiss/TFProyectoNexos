package pe.edu.upc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Usuario;
import pe.edu.upc.repository.IUsuarioRepository;
import pe.edu.upc.service.IUsuarioService;

@Service
public class UsuarioServiceImplement implements IUsuarioService {

	@Autowired
    private  IUsuarioRepository uR;


    @Override
    public Integer insert(Usuario usuario) {
    	int rpta = uR.usuariosExistentes(usuario.getNombreUsuario());
		if (rpta == 0) {
			uR.save(usuario);
		}
		return rpta;
		}
    

    @Override
	public List<Usuario> list() {
		// TODO Auto-generated method stub
		return uR.findAll();
	}
	

}
