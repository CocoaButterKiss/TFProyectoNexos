package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Tipodepago;

@Repository
public interface ITipodepagoRepository extends JpaRepository<Tipodepago, Integer>{

	@Query("select count(t.nombreTipodepago) from Tipodepago t where t.nombreTipodepago=:name")
	public int tipodepagosExistentes(@Param("name") String nombre);
}
