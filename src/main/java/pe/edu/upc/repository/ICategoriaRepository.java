package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Categoria;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Integer>{
	
	@Query("select count(c.nombreCategoria) from Categoria c where c.nombreCategoria=:name")
	public int categoriasExistentes(@Param("name") String nombre);
}
