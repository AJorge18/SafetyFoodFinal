package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Cliente;
import pe.edu.upc.repository.IClienteRepository;
import pe.edu.upc.service.IClienteService;

@Service
public class ClienteServiceImpl implements IClienteService {
	@Autowired
	private IClienteRepository clR;

	@Override
	@Transactional
	public Integer insert(Cliente cliente) {
		int rpta = clR.buscarNombreCliente(cliente.getName());
		if (rpta == 0) {
			clR.save(cliente);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void delete(long idCliente) {
		clR.deleteById(idCliente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> list() {
		// TODO Auto-generated method stub
		return clR.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

	@Override
	public Optional<Cliente> findById(Long idCliente) {
		// TODO Auto-generated method stub
		return clR.findById(idCliente);
	}

	@Override
	public List<Cliente> fetchClienteByName(String name) {
		return clR.fetchClienteByName(name);

	}
	@Override
	public List<Cliente> findByNameClienteLikeIgnoreCase(String nameCliente) {
		// TODO Auto-generated method stub
		return clR.findByNameLikeIgnoreCase(nameCliente);
	}

	@Override
	public Optional<Cliente> listarId(long idCliente) {
		// TODO Auto-generated method stub
		return clR.findById(idCliente);
	}
	
	
}
