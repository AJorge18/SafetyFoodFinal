package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Distrito;
import pe.edu.upc.repository.IDistritoRepository;
import pe.edu.upc.service.IDistritoService;

@Service
public class DistritoServiceImpl implements IDistritoService {
	@Autowired
	private IDistritoRepository diR;

	@Override
	@Transactional
	public Integer insert(Distrito distrito) {
		int rpta = diR.buscarNombreDistrito(distrito.getName());
		if (rpta == 0) {
			diR.save(distrito);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void delete(long idDistrito) {
		diR.deleteById(idDistrito);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Distrito> list() {
		// TODO Auto-generated method stub
		return diR.findAll(Sort.by(Sort.Direction.DESC, "name"));
	}

	@Override
	public Optional<Distrito> listarId(long idDistrito) {
		// TODO Auto-generated method stub
		return diR.findById(idDistrito);
	}

	


}