package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Departamento;
import pe.edu.upc.repository.IDepartamentoRepository;
import pe.edu.upc.service.IDepartamentoService;

@Service
public class DepartamentoServiceImpl implements IDepartamentoService {
	@Autowired
	private IDepartamentoRepository deR;

	@Override
	@Transactional
	public Integer insert(Departamento departamento) {
		int rpta = deR.buscarNombreDepartamento(departamento.getName());
		if (rpta == 0) {
			deR.save(departamento);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void delete(long idDepartamento) {
		deR.deleteById(idDepartamento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Departamento> list() {
		// TODO Auto-generated method stub
		return deR.findAll(Sort.by(Sort.Direction.DESC, "name"));
	}

	@Override
	public Optional<Departamento> listarId(long idDepartamento) {
		// TODO Auto-generated method stub
		return deR.findById(idDepartamento);
	}

	


}