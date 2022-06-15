package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Carta;

public interface ICartaService {
	public Integer insert(Carta carta);

	public void delete(long idCarta);

	List<Carta> list();

	Optional<Carta> findById(Long idCarta);
	
	Optional<Carta> listarId(long idCarta);
	
	List<Carta> fetchCartaByName(String nameCarta);

	public List<Carta> fetchCartaByRestauranteName(String nameRestaurante);
	
	public List<Carta> findByNameCartaLikeIgnoreCase(String nameCarta);

}
