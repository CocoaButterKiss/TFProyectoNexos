package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Valoracion;

@Repository
public interface IValoracionRepository extends JpaRepository<Valoracion,Integer>{
	

	@Query("select count(v.nombreValoracion) from Valoracion v where v.nombreValoracion=:name")
	public int buscarValoracion(@Param("name") String nombre);
	
	List<Valoracion> findBynombreValoracion(String nombre);
}
