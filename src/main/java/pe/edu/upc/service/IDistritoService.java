package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Distrito;

public interface IDistritoService {
	public Integer insert(Distrito distrito);

	public void delete(long idDistrito);

	List<Distrito> list();

	Optional<Distrito> listarId(long idDistrito);

	

}
