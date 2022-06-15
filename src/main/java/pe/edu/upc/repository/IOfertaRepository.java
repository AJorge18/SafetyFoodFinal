package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Oferta;

@Repository
public interface IOfertaRepository extends JpaRepository<Oferta, Long> {
	@Query("select count(f.name) from Oferta f where f.name =:name")
	public int buscarNombreOferta(@Param("name") String nombreOferta);
	
	@Query("select f from Oferta f where f.name like %?1%")
	List<Oferta> fetchOfertaByName(String nombreOferta);

	@Query("select f from Oferta f where f.restaurante.name like %?1%")
	
	public List<Oferta> findOfertaByNameRestaurante(String nameRestaurante);
	
	public List<Oferta> findByNameLikeIgnoreCase(String nameOferta);

}