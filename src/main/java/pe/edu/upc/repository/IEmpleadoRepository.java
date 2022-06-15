package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Empleado;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado,Long>{
	@Query("select count(ep.name) from Empleado ep where ep.name =:name")
	public int buscarNombreEmpleado(@Param("name") String nombreEmpleado);
	
	@Query("select ep from Empleado ep where ep.name like %?1%")
	List<Empleado> fetchEmpleadoByName(String nombreEmpleado);
	
	@Query("select ep from Empleado ep where ep.restaurante.name like %?1%")
	public List<Empleado> findEmpleadoByNameRestaurante(String nameRestaurante);
	
	public List<Empleado> findByNameLikeIgnoreCase(String nombreEmpleado);
}
