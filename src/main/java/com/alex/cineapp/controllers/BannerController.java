package com.alex.cineapp.controllers;

import com.alex.cineapp.models.Banner;
import com.alex.cineapp.service.IBannerService;
import com.alex.cineapp.util.Utileria;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/banners")
public class BannerController {

    @Autowired
    IBannerService serviceBanners;

    @Value("${ciena.ruta.images}")
    String rutaImagenes;

    @GetMapping("/index")
    public String mostrarIndex(Model model) {
        List<Banner> banners = serviceBanners.buscarTodos();
        model.addAttribute("banners", banners);
        return "banners/listBanners";
    }

    @GetMapping("/create")
    public String crear(@ModelAttribute Banner banner) {
        return "banners/formBanner";
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute @Valid Banner banner, BindingResult result, RedirectAttributes attributes,
                          @RequestParam("archivoImagen")MultipartFile multiPart){

                            String mensaje = "El registro fue guardado";

        if (result.hasErrors()){
            return "banners/index";
        }

        if (!multiPart.isEmpty()){
            String nombreImagen = Utileria.guardarArchivo(multiPart, rutaImagenes);
            if (!nombreImagen.isEmpty()){
                banner.setArchivo(nombreImagen);
            }
        }
        if (banner.getId() > 0) {
            mensaje = "El registro fue actualizado";
        }

        serviceBanners.insertar(banner);
        attributes.addFlashAttribute("msg", mensaje);
        return "redirect:/banners/index";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Banner banner, Model model) {
        Banner bannerDb = serviceBanners.buscarPorId(id);
        model.addAttribute("banner", bannerDb);
        return "banners/formBanner";
    }

    @GetMapping("/delete/{id}")
	public String eliminar(@PathVariable int id,  RedirectAttributes attributes) {
		serviceBanners.eliminarPorId(id);
		attributes.addFlashAttribute("msg", "El elemento fue eliminado");
		return "redirect:/banners/index";
	}
    


}
