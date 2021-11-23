package pe.edu.upc.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Valoracion;
import pe.edu.upc.service.IReunionService;
import pe.edu.upc.service.IValoracionService;

@Controller
@RequestMapping("/valoraciones")
public class ValoracionController {

	@Autowired
	private IValoracionService vService;
	
	@Autowired
	private IReunionService rService;
	
	@Secured("ROLE_USER")
	@GetMapping("/new")
	public String newValoracion(Model model) {
		model.addAttribute("valoracion", new Valoracion());
		model.addAttribute("listaReuniones", rService.list());
		model.addAttribute("valoracion", new Valoracion());
		return "valoracion/valoracion";
	}
	
	@GetMapping("/list")
	public String listValoraciones(Model model) {
		try {
			model.addAttribute("valoracion", new Valoracion());
			model.addAttribute("listaValoraciones", vService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "valoracion/listValoraciones";
	}
	
	@RequestMapping("/save")
	public String insertValoracion(@ModelAttribute @Valid Valoracion objValo, BindingResult binRes, Model model,
			 RedirectAttributes flash, SessionStatus status)
			throws ParseException {
		if (binRes.hasErrors()) {
			model.addAttribute("listaReuniones", rService.list());
			return "valoracion/valoracion";
		} else {
			
			
			boolean flag = vService.insert(objValo);
			if (flag) {
				return "redirect:/valoraciones/list";
			} else {
				model.addAttribute("mensaje", "Ocurrió un error");
				return "redirect:/valoraciones/new";
			}
		}
	}
	
	
	@GetMapping(value = "/view/{id}")
	public String view(@PathVariable(value = "id") int id, Map<String, Object> model, RedirectAttributes flash) {

		Valoracion valoracion = vService.listarId(id);

		if (valoracion == null) {
			flash.addFlashAttribute("error", "La valoracion no existe en la base de datos");
			return "valoracion/listValoraciones";
		}

		model.put("valoracion", valoracion);
		model.put("titulo", "Detalle de la valoracion: " + valoracion.getNombreValoracion());

		return "valoracion/view";
	}
	

	@RequestMapping("/list")
	public String listValoraciones(Map<String, Object> model) {
		model.put("listaValoraciones", vService.list());
		return "valoracion/listValoraciones";
	}
	
	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute Valoracion valo) {
		vService.listarId(valo.getIdValoracion());
		return "valoracion/listValoraciones";
	}
	
	
	@RequestMapping("/update/{id}")
	public String update(@PathVariable int id, Model model, RedirectAttributes objRedir) {

		Valoracion objValo = vService.listarId(id);
		if (objValo == null) {
			objRedir.addFlashAttribute("mensaje", "OcurriÃ³ un error");
			return "redirect:/valoraciones/list";
		} else {
			model.addAttribute("listaReuniones", rService.list());

			model.addAttribute("valoracion", objValo);
			return "valoracion/valoracion";
		}
	}
	
	@RequestMapping("/search")
	public String findValoracion(@ModelAttribute Valoracion valoracion, Model model) {
		
		List<Valoracion> listaValoraciones;
		listaValoraciones=vService.findBynombreValoracion(valoracion.getNombreValoracion());
		model.addAttribute("listaValoraciones",listaValoraciones);
		
		return "valoracion/listValoraciones";
		
	}
	
	
}
