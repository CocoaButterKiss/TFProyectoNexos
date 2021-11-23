package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	@Query("select count(u.nombreUsuario) from Usuario u where u.nombreUsuario=:name")
	public int usuariosExistentes(@Param("name") String nombre);
}
