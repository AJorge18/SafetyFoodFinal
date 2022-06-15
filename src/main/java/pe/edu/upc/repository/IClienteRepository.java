package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
	@Query("select count(cl.name) from Cliente cl where cl.name =:name")
	public int buscarNombreCliente(@Param("name") String nombreCliente);

	@Query("select cl from Cliente cl where cl.name like %?1%")
	List<Cliente> fetchClienteByName(String nombreCliente);

	public List<Cliente> findByNameLikeIgnoreCase(String nameCliente);
}
