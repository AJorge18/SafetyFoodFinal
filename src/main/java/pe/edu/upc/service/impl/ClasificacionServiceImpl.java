package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.entity.Clasificacion;
import pe.edu.upc.repository.IClasificacionRepository;
import pe.edu.upc.service.IClasificacionService;

@Service
public class ClasificacionServiceImpl implements IClasificacionService {
	@Autowired
	private IClasificacionRepository claR;

	@Override
	@Transactional
	public Integer insert(Clasificacion clasificacion) {
		int rpta = claR.buscarClasificacion(clasificacion.getClasificaciondesc());
		if (rpta == 0) {
			claR.save(clasificacion);
		}
		return rpta;
	}
	@Override
	@Transactional
	public void delete(long idClasificacion) {
		claR.deleteById(idClasificacion);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Clasificacion> list() {
		// TODO Auto-generated method stub
		return claR.findAll(Sort.by(Sort.Direction.ASC, "clasificaciondesc"));
	}
	

	@Override
	public Optional<Clasificacion> findById(Long idClasificacion) {
		// TODO Auto-generated method stub
		return claR.findById(idClasificacion);
	}

	@Override
	public List<Clasificacion> fetchClasificaciondescByClasi(String clasificaciondesc) {
		// TODO Auto-generated method stub
		return claR.fetchClasificacionByClasi(clasificaciondesc);
	}

	@Override
	public List<Clasificacion> fetchClasificacionByClienteName(String nameCliente) {
		// TODO Auto-generated method stub
		return claR.findClasificacionByNameCliente(nameCliente);
	}

	@Override
	public List<Clasificacion> findByclasificaciondescClasificacionLikeIgnoreCase(String clasiClasificacion) {
		// TODO Auto-generated method stub
		return claR.findByClasificaciondescLikeIgnoreCase(clasiClasificacion);
	}

	


	@Override
	public Optional<Clasificacion> listarId(long idClasificacion) {
		// TODO Auto-generated method stub
		return claR.findById(idClasificacion);
	}


	
	
}
