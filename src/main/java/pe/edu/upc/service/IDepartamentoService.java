package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Departamento;

public interface IDepartamentoService {
	public Integer insert(Departamento departamento);

	public void delete(long idDepartamento);

	List<Departamento> list();

	Optional<Departamento> listarId(long idDepartamento);

	

}
