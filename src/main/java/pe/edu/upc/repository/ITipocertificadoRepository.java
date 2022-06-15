package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Tipocertificado;

@Repository
public interface ITipocertificadoRepository extends JpaRepository<Tipocertificado, Long> {
	@Query("select count(r.name) from Tipocertificado r where r.name =:name")
	public int buscarNombreTipocertificado(@Param("name") String nombreTipocertificado);

	
	
}
