package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Plato;

public interface IPlatoService {
	public Integer insert(Plato plato);

	public void delete(long idPlato);

	List<Plato> list();

	Optional<Plato> listarId(long idPlato);

	

}
