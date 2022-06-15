package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Plato;

@Repository
public interface IPlatoRepository extends JpaRepository<Plato, Long> {
	@Query("select count(p.name) from Plato p where p.name =:name")
	public int buscarNombrePlato(@Param("name") String nombrePlato);

	
	
}
