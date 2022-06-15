package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Oferta;
import pe.edu.upc.repository.IOfertaRepository;
import pe.edu.upc.service.IOfertaService;

@Service
public class OfertaServiceImpl implements IOfertaService {
	@Autowired
	private IOfertaRepository fR;

	@Override
	@Transactional
	public Integer insert(Oferta oferta) {
		int rpta = 0;
		if (rpta == 0) {
			fR.save(oferta);
		}
		return rpta;
	}
	
	@Override
	@Transactional
	public void delete(long idOferta) {
		fR.deleteById(idOferta);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Oferta> list() {
		// TODO Auto-generated method stub
		return fR.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

	@Override
	public Optional<Oferta> findById(Long idOferta) {
		// TODO Auto-generated method stub
		return fR.findById(idOferta);
	}
	
	@Override
	public List<Oferta> fetchOfertaByName(String name) {
		return fR.fetchOfertaByName(name);

	}
	
	@Override
	public List<Oferta> fetchOFertaByRestauranteName(String nameRestaurante) {
		// TODO Auto-generated method stub
		return fR.findOfertaByNameRestaurante(nameRestaurante);
	}
	
	@Override
	public List<Oferta> findByNameOfertaLikeIgnoreCase(String nameOferta) {
		// TODO Auto-generated method stub
		return fR.findByNameLikeIgnoreCase(nameOferta);
	}


	@Override
	public Optional<Oferta> listarId(long idOferta) {
		// TODO Auto-generated method stub
		return fR.findById(idOferta);
	}

	


}
