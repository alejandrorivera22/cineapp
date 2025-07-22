package com.alex.cineapp.controllers;

import com.alex.cineapp.models.Pelicula;
import com.alex.cineapp.service.IDetalleService;
import com.alex.cineapp.service.IPeliculaService;
import com.alex.cineapp.util.Utileria;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/peliculas")
public class PeliculasController {

    @Autowired
    IPeliculaService servicePelicula;

    @Autowired
    IDetalleService serviceDetalle;

    @Value("${ciena.ruta.images}")
    String rutaImagenes;

    @GetMapping("/index")
    public String mostrarIndex(Model model, Pageable pageable){
        Page<Pelicula> lista = servicePelicula.buscarTodas(pageable);
        model.addAttribute("peliculas", lista);
        return "peliculas/listPeliculas";
    }


    @GetMapping("/create")
    public String crear(Pelicula pelicul, Model model){
        return "peliculas/formPelicula";
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute("pelicula") @Valid Pelicula pelicula, BindingResult result, RedirectAttributes attributes,
                          @RequestParam("archivoImagen") MultipartFile  multipart, Model model){
        
        String mensaje = "La pelicula a sido guardada con exito";

        if (result.hasErrors()){
            model.addAttribute("pelicula", pelicula);
            return "peliculas/formPelicula";
        }

        if (!multipart.isEmpty()){
            String nombreImagen = Utileria.guardarArchivo(multipart, rutaImagenes);
            if (nombreImagen != null){
                pelicula.setImagen(nombreImagen);
            }
        }

        if(pelicula.getId() > 0){
            mensaje = "La pelicula a sido actualizada con exito";
        }

        serviceDetalle.insertar(pelicula.getDetalle());
        servicePelicula.insertar(pelicula);
        attributes.addFlashAttribute("msg", mensaje);
        return "redirect:/peliculas/index";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable int id, Model model){
        Pelicula pelicula = servicePelicula.buscarPorId(id);
        model.addAttribute("pelicula", pelicula);
        return "peliculas/formPelicula";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable int id, RedirectAttributes attributes){
        Pelicula peliculaDb = servicePelicula.buscarPorId(id);

        servicePelicula.eliminar(id);

        serviceDetalle.eliminar(peliculaDb.getDetalle().getId());

        attributes.addFlashAttribute("msg", "La pelicula fue eliminadda con exito");
        return "redirect:/peliculas/index";
    }
    
    @ModelAttribute("generos")
    public List<String> getGeneros(){
        return servicePelicula.buscarGeneros();
    }

}
