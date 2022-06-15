package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Distrito;

@Repository
public interface IDistritoRepository extends JpaRepository<Distrito, Long> {
	@Query("select count(i.name) from Distrito i where i.name =:name")
	public int buscarNombreDistrito(@Param("name") String nombreDistrito);

	
	
}
