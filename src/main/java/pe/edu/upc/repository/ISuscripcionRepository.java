package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Suscripcion;


@Repository
public interface ISuscripcionRepository extends JpaRepository<Suscripcion,Integer>{

	@Query("select count(s.nombreSuscripcion) from Suscripcion s where s.nombreSuscripcion=:name")
	public int buscarSuscripcion(@Param("name") String nombre);
	
	List<Suscripcion> findBynombreSuscripcion(String nombre);
}
