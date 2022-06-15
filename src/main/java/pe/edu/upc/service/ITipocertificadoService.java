package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Tipocertificado;

public interface ITipocertificadoService {
	public Integer insert(Tipocertificado tipocertificado);

	public void delete(long idTipocertificado);

	List<Tipocertificado> list();

	Optional<Tipocertificado> listarId(long idTipocertificado);

	

}
