package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Plato;
import pe.edu.upc.repository.IPlatoRepository;
import pe.edu.upc.service.IPlatoService;

@Service
public class PlatoServiceImpl implements IPlatoService {
	@Autowired
	private IPlatoRepository plR;

	@Override
	@Transactional
	public Integer insert(Plato plato) {
		int rpta = plR.buscarNombrePlato(plato.getName());
		if (rpta == 0) {
			plR.save(plato);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void delete(long idPlato) {
		plR.deleteById(idPlato);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Plato> list() {
		// TODO Auto-generated method stub
		return plR.findAll(Sort.by(Sort.Direction.DESC, "name"));
	}

	@Override
	public Optional<Plato> listarId(long idPlato) {
		// TODO Auto-generated method stub
		return plR.findById(idPlato);
	}

	


}