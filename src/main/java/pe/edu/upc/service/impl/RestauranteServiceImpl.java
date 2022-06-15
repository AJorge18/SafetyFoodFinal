package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Restaurante;
import pe.edu.upc.repository.IRestauranteRepository;
import pe.edu.upc.service.IRestauranteService;

@Service
public class RestauranteServiceImpl implements IRestauranteService {
	@Autowired
	private IRestauranteRepository rtR;

	@Override
	@Transactional
	public Integer insert(Restaurante restaurante) {
		int rpta = 0;
		if (rpta == 0) {
			rtR.save(restaurante);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void delete(long idRestaurante) {
		rtR.deleteById(idRestaurante);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Restaurante> list() {
		// TODO Auto-generated method stub
		return rtR.findAll(Sort.by(Sort.Direction.ASC, "name"));
			
	}
	

	@Override
	public Optional<Restaurante> findById(Long idRestaurante) {
		// TODO Auto-generated method stub
		return rtR.findById(idRestaurante);
	}

	@Override
	public List<Restaurante> fetchRestauranteByName(String name) {
		return rtR.fetchRestauranteByName(name);

	}

	@Override
	public List<Restaurante> fetchRestauranteByDepartamentoName(String nameDepartamento) {
		// TODO Auto-generated method stub
		return rtR.findRestauranteByNameDepartamento(nameDepartamento);
	}

	@Override
	public List<Restaurante> findByNameRestauranteLikeIgnoreCase(String nameRestaurante) {
		// TODO Auto-generated method stub
		return rtR.findByNameLikeIgnoreCase(nameRestaurante);
	}

	@Override
	public Optional<Restaurante> listarId(long idRestaurante) {
		// TODO Auto-generated method stub
		return rtR.findById(idRestaurante);
	}
	
	
}
