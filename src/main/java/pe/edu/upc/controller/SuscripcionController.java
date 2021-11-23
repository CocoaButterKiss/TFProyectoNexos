package pe.edu.upc.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Suscripcion;
import pe.edu.upc.service.ISuscripcionService;
import pe.edu.upc.service.ITipodepagoService;
import pe.edu.upc.service.IUsuarioService;

@Controller
@RequestMapping("/suscripciones")
public class SuscripcionController {

	@Autowired
	private ISuscripcionService sService;
	
	@Autowired
	private IUsuarioService uService;
	
	@Autowired
	private ITipodepagoService tService;
	
	@GetMapping("/new")
	public String newSuscripcion(Model model) {
		model.addAttribute("suscripcion", new Suscripcion());
		model.addAttribute("listaUsuarios", uService.list());
		model.addAttribute("listaTipodepagos", tService.list());
		model.addAttribute("suscripcion", new Suscripcion());
		return "suscripcion/suscripcion";
	}
	
	@GetMapping("/list")
	public String listSuscripciones(Model model) {
		try {
			model.addAttribute("suscripcion", new Suscripcion());
			model.addAttribute("listaSuscripciones", sService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "suscripcion/listSuscripciones";
	}
	
	@RequestMapping("/save")
	public String insertSuscripcion(@ModelAttribute @Valid Suscripcion objSuscri, BindingResult binRes, Model model,
			 RedirectAttributes flash, SessionStatus status)
			throws ParseException {
		if (binRes.hasErrors()) {
			model.addAttribute("listaUsuarios", uService.list());
			model.addAttribute("listaTipodepagos", tService.list());
			return "suscripcion/suscripcion";
		} else {
			
			
			boolean flag = sService.insert(objSuscri);
			if (flag) {
				return "redirect:/suscripciones/list";
			} else {
				model.addAttribute("mensaje", "Ocurrió un error");
				return "redirect:/suscripciones/new";
			}
		}
	}
	
	
	@GetMapping(value = "/view/{id}")
	public String view(@PathVariable(value = "id") int id, Map<String, Object> model, RedirectAttributes flash) {

		Suscripcion suscripcion = sService.listarId(id);

		if (suscripcion == null) {
			flash.addFlashAttribute("error", "La valoracion no existe en la base de datos");
			return "suscripcion/listSuscripciones";
		}

		model.put("suscripcion", suscripcion);
		model.put("titulo", "Detalle de la valoracion: " + suscripcion.getNombreSuscripcion());

		return "suscripcion/view";
	}
	
	@RequestMapping("/list")
	public String listSuscripciones(Map<String, Object> model) {
		model.put("listaSuscripciones", sService.list());
		return "suscripcion/listSuscripciones";
	}
	
	
	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute Suscripcion suscri) {
		sService.listarId(suscri.getIdSuscripcion());
		return "suscripcion/listSuscripciones";
	}
	
	@RequestMapping("/update/{id}")
	public String update(@PathVariable int id, Model model, RedirectAttributes objRedir) {

		Suscripcion objSuscri = sService.listarId(id);
		if (objSuscri == null) {
			objRedir.addFlashAttribute("mensaje", "OcurriÃ³ un error");
			return "redirect:/suscripciones/list";
		} else {
			model.addAttribute("listaSuscripciones", uService.list());
			model.addAttribute("listaTipodepagos", tService.list());
			model.addAttribute("suscripcion", objSuscri);
			return "suscripcion/suscripcion";
		}
	}
	
	
	@RequestMapping("/search")
	public String findSuscripcion(@ModelAttribute Suscripcion suscripcion, Model model) {
		
		List<Suscripcion> listaSuscripciones;
		listaSuscripciones=sService.findBynombreSuscripcion(suscripcion.getNombreSuscripcion());
		model.addAttribute("listaSuscripciones",listaSuscripciones);
		
		return "suscripcion/listSuscripciones";
		
	}
	
}
