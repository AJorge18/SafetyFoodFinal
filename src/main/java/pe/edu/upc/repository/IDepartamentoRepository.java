package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Departamento;

@Repository
public interface IDepartamentoRepository extends JpaRepository<Departamento, Long> {
	@Query("select count(e.name) from Departamento e where e.name =:name")
	public int buscarNombreDepartamento(@Param("name") String nombreDepartamento);

	
	
}
