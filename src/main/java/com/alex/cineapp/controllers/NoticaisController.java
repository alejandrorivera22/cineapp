package com.alex.cineapp.controllers;

import com.alex.cineapp.models.Noticia;
import com.alex.cineapp.service.INoticiasService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/noticias")
public class NoticaisController {

    @Autowired
    INoticiasService serviceNoticia;

    @GetMapping("/index")
    public String mostrarIndex(Model model) {
        List<Noticia> listNoticias = serviceNoticia.buscarTodas();
        model.addAttribute("noticias", listNoticias);
        return "noticias/listNoticias";
    }
    

    @GetMapping("/create")
    public String crear(@ModelAttribute Noticia noticia){
        return "noticias/formNoticia";

    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute @Valid Noticia noticia, BindingResult result ,RedirectAttributes attributes, Model  model){

        String mensaje = "noticia guardada con exito";

        if (result.hasErrors()) {
            model.addAttribute("noticia", noticia);
            return "noticias/formNoticia";
        }

        if (noticia.getId() > 0) {
            mensaje = "noticia actualizada con exito";
        }
        serviceNoticia.guardar(noticia);
        attributes.addFlashAttribute("msg", mensaje);

        return "redirect:/noticias/index";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes attributes) {
        serviceNoticia.eliminar(id);
        attributes.addFlashAttribute("msg", "Noticia eliminada con exito");
        return "redirect:/noticias/index";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable int id, Model model) {
        Noticia noticia = serviceNoticia.buscarPorId(id);
        model.addAttribute("noticia", noticia);
        
        return "noticias/formNoticia";
    }
    
    

}
