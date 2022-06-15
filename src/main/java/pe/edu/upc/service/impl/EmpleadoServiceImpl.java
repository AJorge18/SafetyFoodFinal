package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Empleado;
import pe.edu.upc.repository.IEmpleadoRepository;
import pe.edu.upc.service.IEmpleadoService;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {
	@Autowired
	private IEmpleadoRepository epR;

	@Override
	@Transactional
	public Integer insert(Empleado empleado) {
		int rpta = 0;
		if (rpta == 0) {
			epR.save(empleado);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void delete(long idEmpleado) {
		// TODO Auto-generated method stub
		epR.deleteById(idEmpleado);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Empleado> list() {
		// TODO Auto-generated method stub
		return epR.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

	@Override
	public Optional<Empleado> findById(Long idEmpleado) {
		// TODO Auto-generated method stub
		return epR.findById(idEmpleado);
	}

	

	@Override
	public List<Empleado> fetchEmpleadoByName(String name) {
		
		return epR.fetchEmpleadoByName(name);
	}

	@Override
	public List<Empleado> fetchEmpleadoByRestauranteName(String nameRestaurante) {
		// TODO Auto-generated method stub
		return epR.findEmpleadoByNameRestaurante(nameRestaurante);
	}

	@Override
	public List<Empleado> findByNameEmpleadoLikeIgnoreCase(String nameEmpleado) {
		// TODO Auto-generated method stub
		return epR.findByNameLikeIgnoreCase(nameEmpleado);
	}
	
	@Override
	public Optional<Empleado> listarId(long idEmpleado) {
		// TODO Auto-generated method stub
		return epR.findById(idEmpleado);
	}
}
