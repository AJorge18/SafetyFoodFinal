package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Cliente;

public interface IClienteService {
	public Integer insert(Cliente cliente);

	public void delete(long idCliente);

	List<Cliente> list();

	Optional<Cliente> findById(Long idCliente);
	
	Optional<Cliente> listarId(long idCliente);

	List<Cliente> fetchClienteByName(String nameCliente);

	public List<Cliente> findByNameClienteLikeIgnoreCase(String nameCliente);
	
	

}
