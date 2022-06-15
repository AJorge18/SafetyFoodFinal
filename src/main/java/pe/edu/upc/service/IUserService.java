package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.Users;

public interface IUserService {

        public void insertar(Users u);

        List<Users> listar();



}