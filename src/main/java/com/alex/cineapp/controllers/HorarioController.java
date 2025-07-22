package com.alex.cineapp.controllers;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alex.cineapp.models.Horario;
import com.alex.cineapp.models.Pelicula;
import com.alex.cineapp.service.IHorariosService;
import com.alex.cineapp.service.IPeliculaService;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/horarios")
public class HorarioController {

	@Autowired
	private IPeliculaService servicePeliculas;

	@Autowired
	private IHorariosService serviceHorario;

	@GetMapping("/index")
	public String mostrar(Model model, Pageable pageable) {
		Page<Horario> listaHorarios = serviceHorario.buscarTodos(pageable);
		model.addAttribute("horarios", listaHorarios);
		return "horarios/listHorarios";
	}

	@GetMapping(value = "/create")
	public String crear(@ModelAttribute Horario horario) {
		return "horarios/formHorario";
	}

	@PostMapping(value = "/save")
	public String guardar(@ModelAttribute @Valid Horario horario, BindingResult result, Model model,
			RedirectAttributes attributes) {

			String mensaje = "El horario fue guardado!";

		if (result.hasErrors()) {
			List<Pelicula> listaPeliculas = servicePeliculas.buscarActiva();
			model.addAttribute("peliculas", listaPeliculas);
			return "horarios/formHorario";
		}

		if (horario.getId() > 0) {
			mensaje = "El horario fue actualizado!";
		}
		serviceHorario.insertar(horario);
		attributes.addFlashAttribute("msg", mensaje);
		return "redirect:/horarios/index";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		Horario horarioDb = serviceHorario.buscarPorId(id);
		model.addAttribute("horario", horarioDb);
		return "horarios/formHorario";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id,  RedirectAttributes attributes) {
		serviceHorario.eliminarPorId(id);
		attributes.addFlashAttribute("msg", "El elemento fue eliminado");
		return "redirect:/horarios/index";
	}
	

	@ModelAttribute("peliculas")
	public List<Pelicula> getPeliculas() {
		return servicePeliculas.buscarActiva();
	}

	@InitBinder("horario")
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
