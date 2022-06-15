package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Clasificacion;


@Repository
public interface IClasificacionRepository extends JpaRepository<Clasificacion, Long> {
	@Query("select count(cla.clasificaciondesc) from Clasificacion cla where cla.clasificaciondesc =:clasificaciondesc")
	public int buscarClasificacion(@Param("clasificaciondesc") String clasiClasificacion);
	
	@Query("select cla from Clasificacion cla where cla.clasificaciondesc like %?1%")
	List<Clasificacion> fetchClasificacionByClasi(String clasiClasificacion);
	
	@Query("select cla from Clasificacion cla where cla.cliente.name like %?1%")
	public List<Clasificacion> findClasificacionByNameCliente(String nameCliente);
	
	public List<Clasificacion> findByClasificaciondescLikeIgnoreCase(String clasiClasificacion);

}
