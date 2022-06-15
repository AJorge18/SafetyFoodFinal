package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Oferta;

public interface IOfertaService {
	public Integer insert(Oferta oferta);

	public void delete(long idOferta);

	List<Oferta> list();

	Optional<Oferta> findById(Long idOferta);
	
	Optional<Oferta> listarId(long idOferta);
	
	List<Oferta> fetchOfertaByName(String nameOferta);

	public List<Oferta> fetchOFertaByRestauranteName(String nameRestaurante);
	
	public List<Oferta> findByNameOfertaLikeIgnoreCase(String nameOferta);

}