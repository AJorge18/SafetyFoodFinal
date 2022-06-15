package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Carta;
import pe.edu.upc.repository.ICartaRepository;
import pe.edu.upc.service.ICartaService;

@Service
public class CartaServiceImpl implements ICartaService {
	@Autowired
	private ICartaRepository ctR;

	@Override
	@Transactional
	public Integer insert(Carta carta) {
		int rpta = 0;
		if (rpta == 0) {
			ctR.save(carta);
		}
		return rpta;
	}
	
	@Override
	@Transactional
	public void delete(long idCarta) {
		ctR.deleteById(idCarta);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Carta> list() {
		// TODO Auto-generated method stub
		return ctR.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

	@Override
	public Optional<Carta> findById(Long idCarta) {
		// TODO Auto-generated method stub
		return ctR.findById(idCarta);
	}
	
	@Override
	public List<Carta> fetchCartaByName(String name) {
		return ctR.fetchCartaByName(name);

	}
	
	@Override
	public List<Carta> fetchCartaByRestauranteName(String nameCarta) {
		// TODO Auto-generated method stub
		return ctR.findCartaByNameRestaurante(nameCarta);
	}
	
	@Override
	public List<Carta> findByNameCartaLikeIgnoreCase(String nameCarta) {
		// TODO Auto-generated method stub
		return ctR.findByNameLikeIgnoreCase(nameCarta);
	}


	@Override
	public Optional<Carta> listarId(long idCarta) {
		// TODO Auto-generated method stub
		return ctR.findById(idCarta);
	}


}
