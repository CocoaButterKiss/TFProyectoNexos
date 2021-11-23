package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Reunion;

@Repository
public interface IReunionRepository extends JpaRepository<Reunion,Integer> {
	@Query("select count(r.nombreReu) from Reunion r where r.nombreReu=:name")
	public int buscarReunion(@Param("name") String nombre);
	
	List<Reunion> findBynombreReu(String nombre);
}
