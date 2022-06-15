package pe.edu.upc.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.upc.entity.Users;
import pe.edu.upc.service.IUserService;

@Controller
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private IUserService uService;

    @Autowired
    private PasswordEncoder  passwordEncoder;

    @GetMapping("/nuevo")
    public String newUser(Model model) {
        model.addAttribute("u", new Users());
        return "user/usuario";
    }

    @PostMapping("/guardar")
    public String registrarUser(@Valid Users objuser, BindingResult binRes, Model model) 
            throws ParseException {
        if (binRes.hasErrors()) {
            return "user/usuario";
        } else {
            String p = objuser.getPassword();
            String pE= passwordEncoder.encode(p);
            Users us= new Users();
            us.setUsername(objuser.getUsername());
            us.setEnabled(objuser.getEnabled());
            us.setPassword(pE);

            uService.insertar(us);
            model.addAttribute("mensaje", "Se guardo correctamente");
            return "redirect:/usuarios/listar";
            } 
        }

    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        try {
            model.addAttribute("listaUsuarios", uService.listar());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "/user/listaUsuario";
    }



}