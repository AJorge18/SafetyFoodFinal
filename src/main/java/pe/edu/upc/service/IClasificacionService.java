package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Clasificacion;

public interface IClasificacionService {
	public Integer insert(Clasificacion clasificacion);

	public void delete(long idClasificacion);

	List<Clasificacion> list();

	Optional<Clasificacion> findById(Long idClasificacion);
	
	Optional<Clasificacion> listarId(long idClasificacion);

	List<Clasificacion> fetchClasificaciondescByClasi(String clasificaciondescClasificacion);

	public List<Clasificacion> fetchClasificacionByClienteName(String nameCliente);

	public List<Clasificacion> findByclasificaciondescClasificacionLikeIgnoreCase(String clasificaciondescClasificacion);
	
	

}
