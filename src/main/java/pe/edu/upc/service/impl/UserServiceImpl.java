package pe.edu.upc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Users;
import pe.edu.upc.repository.UserRepository;
import pe.edu.upc.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository tR;

    @Override
    public void insertar(Users u) {
        tR.save(u);
    }

    @Override
    public  List<Users> listar(){
        return tR.findAll();
    }

}