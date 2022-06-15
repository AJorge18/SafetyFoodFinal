package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Carta;

@Repository
public interface ICartaRepository extends JpaRepository<Carta, Long> {
	@Query("select count(ca.name) from Carta ca where ca.name =:name")
	public int buscarNombreCarta(@Param("name") String nombreCarta);
	
	@Query("select ca from Carta ca where ca.name like %?1%")
	List<Carta> fetchCartaByName(String nombreCarta);

	@Query("select ca from Carta ca where ca.restaurante.name like %?1%")
	public List<Carta> findCartaByNameRestaurante(String nameRestaurante);
	
	public List<Carta> findByNameLikeIgnoreCase(String nameCarta);

}
