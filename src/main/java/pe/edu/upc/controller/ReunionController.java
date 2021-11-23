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

import pe.edu.upc.entity.Reunion;
import pe.edu.upc.service.ICategoriaService;
import pe.edu.upc.service.IDistritoService;
import pe.edu.upc.service.IReunionService;
import pe.edu.upc.service.IUsuarioService;

@Controller
@RequestMapping("/reuniones")
public class ReunionController {
	@Autowired
	private IReunionService rService;
	
	@Autowired
	private IUsuarioService uService;
	
	@Autowired
	private ICategoriaService cService;
	
	@Autowired
	private IDistritoService dService;

	
	@GetMapping("/new")
	public String newReunion(Model model) {
		model.addAttribute("reunion", new Reunion());
		model.addAttribute("listaUsuarios", uService.list());
		model.addAttribute("listaCategorias", cService.list());
		model.addAttribute("listaDistritos", dService.list());
		model.addAttribute("reunion", new Reunion());
		return "reunion/reunion";
	}
	
	
	@GetMapping("/list")
	public String listReuniones(Model model) {
		try {
			model.addAttribute("reunion", new Reunion());
			model.addAttribute("listaReuniones", rService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "reunion/listReuniones";
	}
	
	
	@RequestMapping("/save")
	public String insertReunion(@ModelAttribute @Valid Reunion objReu, BindingResult binRes, Model model,
			 RedirectAttributes flash, SessionStatus status)
			throws ParseException {
		if (binRes.hasErrors()) {
			model.addAttribute("listaUsuarios", uService.list());
			model.addAttribute("listaCategorias", cService.list());
			model.addAttribute("listaDistritos", dService.list());
			return "reunion/reunion";
		} else {
			
			
			boolean flag = rService.insert(objReu);
			if (flag) {
				return "redirect:/reuniones/list";
			} else {
				model.addAttribute("mensaje", "Ocurrió un error");
				return "redirect:/reuniones/new";
			}
		}
	}
	
	@GetMapping(value = "/view/{id}")
	public String view(@PathVariable(value = "id") int id, Map<String, Object> model, RedirectAttributes flash) {

		Reunion reunion = rService.listarId(id);

		if (reunion == null) {
			flash.addFlashAttribute("error", "La reunion no existe en la base de datos");
			return "reunion/listReuniones";
		}

		model.put("reunion", reunion);
		model.put("titulo", "Detalle de la reunion: " + reunion.getNombreReu());

		return "reunion/view";
	}
	
	@RequestMapping("/list")
	public String listReuniones(Map<String, Object> model) {
		model.put("listaReuniones", rService.list());
		return "reunion/listReuniones";
	}
	
	
	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute Reunion reu) {
		rService.listarId(reu.getIdReunion());
		return "reunion/listReuniones";
	}
	
	@RequestMapping("/update/{id}")
	public String update(@PathVariable int id, Model model, RedirectAttributes objRedir) {

		Reunion objReu = rService.listarId(id);
		if (objReu == null) {
			objRedir.addFlashAttribute("mensaje", "OcurriÃ³ un error");
			return "redirect:/reuniones/list";
		} else {
			model.addAttribute("listaUsuarios", uService.list());
			model.addAttribute("listaCategorias", cService.list());
			model.addAttribute("listaDistritos", dService.list());
			model.addAttribute("reunion", objReu);
			return "reunion/reunion";
		}
	}
	
	@RequestMapping("/search")
	public String findReunion(@ModelAttribute Reunion reunion, Model model) {
		
		List<Reunion> listaReuniones;
		listaReuniones=rService.findBynombreReu(reunion.getNombreReu());
		model.addAttribute("listaReuniones",listaReuniones);
		
		return "reunion/listReuniones";
		
	}
	
}
