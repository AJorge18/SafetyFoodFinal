package pe.edu.upc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Role;
import pe.edu.upc.repository.IRoleRepository;
import pe.edu.upc.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {
	@Autowired
	private IRoleRepository rr;

	@Override

	public void insertar(Role r) {
		
			rr.save(r);
	}

	
	@Override
	
	public List<Role> listar() {
		// TODO Auto-generated method stub
		return rr.findAll();
	}



	


}