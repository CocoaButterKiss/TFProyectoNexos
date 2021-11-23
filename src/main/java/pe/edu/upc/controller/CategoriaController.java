package pe.edu.upc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.Categoria;
import pe.edu.upc.service.ICategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private ICategoriaService cService;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/new")
	public String newCategoria(Model model) {
	
		model.addAttribute("categoria",new Categoria());
		return "categoria/categoria";
	}
	
	
    @GetMapping("/list")
	public String listCategorias(Model model) {
		try {
			model.addAttribute("categoria",new Categoria());
			model.addAttribute("listaCategorias", cService.list());
		} catch(Exception e) {
			model.addAttribute("error",e.getMessage());
		}
		return "categoria/listCategorias";
	}

	@PostMapping("/save")
	public String saveCategoria(@Valid Categoria categoria, BindingResult result, Model model, SessionStatus status)
			throws Exception{
		if(result.hasErrors()) {
			return "categoria/categoria";
		}else {
			int rpta=cService.insert(categoria);
			if(rpta>0) {
				model.addAttribute("mensaje", "Ya existe");
				return "categoria/categoria";
			}else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();		
		}		
	}
		model.addAttribute("categoria", new Categoria());
		return "redirect:/categorias/list";
	}
	
}
