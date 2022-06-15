package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Empleado;

public interface IEmpleadoService {
	public Integer insert(Empleado empleado);

	public void delete(long idEmpleado);

	List<Empleado> list();

	Optional<Empleado> findById(Long idEmpleado);
	
	Optional<Empleado> listarId(long idEmpleado);

	List<Empleado> fetchEmpleadoByName(String nameEmpleado);

	public List<Empleado> fetchEmpleadoByRestauranteName(String nameRestaurante);

	public List<Empleado> findByNameEmpleadoLikeIgnoreCase(String nameEmpleado);
}
