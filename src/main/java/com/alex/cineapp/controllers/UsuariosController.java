package com.alex.cineapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.alex.cineapp.models.Perfil;
import com.alex.cineapp.models.Usuario;
import com.alex.cineapp.service.IPerfilesService;
import com.alex.cineapp.service.IUsuarioService;


@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private IUsuarioService serviceUsuario;

    @Autowired
    private IPerfilesService servicePerfil;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/create")
    public String crear(@ModelAttribute Usuario usuario) {
        return "usuarios/formUsuario";
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Usuario> usuarios = serviceUsuario.buscarTodos();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/listUsuarios";
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute Usuario usuario, @RequestParam String perfil) {
        
        String tempPass = usuario.getPwd();
        String enciriptado = encoder.encode(tempPass);
        usuario.setPwd(enciriptado);
        usuario.setActivo(1);

        serviceUsuario.guardar(usuario);

        Perfil perfilTemp = new Perfil();
        perfilTemp.setCuenta(usuario.getCuenta());
        perfilTemp.setPerfil(perfil);
        servicePerfil.guardar(perfilTemp);

        return "redirect:/usuarios/index";
    }
    
      @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes attributes){
        
        servicePerfil.eliminarPorId(id);
        serviceUsuario.eliminarPorId(id);


        attributes.addFlashAttribute("msg", "El Usuario fue eliminadda con exito");
        return "redirect:/usuarios/index";
    }

    @GetMapping("/p")
    public String encoder(){
        String password = "mari123";
        String enriptado = encoder.encode(password);
        System.out.println("============================== " + enriptado);
        return "prueba";
    }
    
}
