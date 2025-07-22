package com.alex.cineapp.controllers;

import com.alex.cineapp.models.Banner;
import com.alex.cineapp.models.Horario;
import com.alex.cineapp.models.Noticia;
import com.alex.cineapp.models.Pelicula;
import com.alex.cineapp.service.IBannerService;
import com.alex.cineapp.service.IHorariosService;
import com.alex.cineapp.service.INoticiasService;
import com.alex.cineapp.service.IPeliculaService;
import com.alex.cineapp.util.Utileria;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    @Autowired
    private IPeliculaService servicePelicula;

    @Autowired
    private IHorariosService serviceHorario;

    @Autowired
    private IBannerService serviceBanner;

    @Autowired
    private INoticiasService serviceNoticias;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @GetMapping("/")
    public String mostrarPrincipal(Model model) {

        try {
            Date fechaSinHora = dateFormat.parse(dateFormat.format(new Date()));
    
            List<String> listaFechas = Utileria.getNextDays(4);
    
            List<Pelicula> peliculas = servicePelicula.buscarPorFecha(fechaSinHora);
            model.addAttribute("fechas", listaFechas);
            model.addAttribute("fechaBuscqueda", dateFormat.format(new Date()));
            model.addAttribute("peliculas", peliculas);
        } catch (ParseException e) {
            e.printStackTrace();
        }

      

        return "home";
    }

    @GetMapping("/index")
    public String mostrarIndex(Authentication authentication) {
        return "redirect:/";
    }
    

    @PostMapping("/search")
    public String buscar(@RequestParam("fecha") Date fecha, Model model) {

        try {			
			Date fechaSinHora = dateFormat.parse(dateFormat.format(fecha));
			List<String> listaFechas = Utileria.getNextDays(4);
			List<Pelicula> peliculas  = servicePelicula.buscarPorFecha(fechaSinHora);
			model.addAttribute("fechas", listaFechas);			
			// Regresamos la fecha que selecciono el usuario con el mismo formato
			model.addAttribute("fechaBuscqueda",dateFormat.format(fecha));			
			model.addAttribute("peliculas", peliculas);			
			return "home";
		} catch (ParseException e) {
			System.out.println("Error: HomeController.buscar" + e.getMessage());
		}
		return "home";
    }

    @GetMapping("/detail/{id}/{fecha}")
    public String mostrarDetalle(Model model, @PathVariable("id") int idPelicula,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yy") Date fecha) {

        List<Horario> horarios = serviceHorario.buscarPorIdPelicula(idPelicula, fecha);
        model.addAttribute("pelicula", servicePelicula.buscarPorId(idPelicula));
        model.addAttribute("horarios", horarios);
        model.addAttribute("fechaBuscqueda", fecha);

        return "detalle";

    }

    @GetMapping("/acerca")
    public String acercva() {
        return "acerca";
    }

    @GetMapping("/login")
    public String login() {
        return "formLogin";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/login";
    }
    
    

    @ModelAttribute("noticias")
    public List<Noticia> getNoticias() {
        return serviceNoticias.buscarUltimas();
    }

    @ModelAttribute("banners")
    public List<Banner> getBanners() {
        return serviceBanner.buscarTodos();
    }

    @InitBinder
	public void initBinder(WebDataBinder webDataBinder) {				
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
