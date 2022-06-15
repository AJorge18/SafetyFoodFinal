package pe.edu.upc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Tipocertificado;
import pe.edu.upc.repository.ITipocertificadoRepository;
import pe.edu.upc.service.ITipocertificadoService;

@Service
public class TipocertificadoServiceImpl implements ITipocertificadoService {
	@Autowired
	private ITipocertificadoRepository tcR;

	@Override
	@Transactional
	public Integer insert(Tipocertificado tipocertificado) {
		int rpta = tcR.buscarNombreTipocertificado(tipocertificado.getName());
		if (rpta == 0) {
			tcR.save(tipocertificado);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void delete(long idTipocertificado) {
		tcR.deleteById(idTipocertificado);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tipocertificado> list() {
		// TODO Auto-generated method stub
		return tcR.findAll(Sort.by(Sort.Direction.DESC, "name"));
	}

	@Override
	public Optional<Tipocertificado> listarId(long idTipocertificado) {
		// TODO Auto-generated method stub
		return tcR.findById(idTipocertificado);
	}

	


}