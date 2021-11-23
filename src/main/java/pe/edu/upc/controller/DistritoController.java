package pe.edu.upc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.Distrito;
import pe.edu.upc.service.IDistritoService;

@Controller
@RequestMapping("/distritos")
public class DistritoController {

	@Autowired
	private IDistritoService dService;
	
	
	@GetMapping("/new")
	public String newDistrito(Model model) {
		
		model.addAttribute("distrito",new Distrito());
		return "distrito/distrito";
	}
	
	
    @GetMapping("/list")
	public String listDistritos(Model model) {
		try {
			model.addAttribute("distrito",new Distrito());
			model.addAttribute("listaDistritos", dService.list());
		} catch(Exception e) {
			model.addAttribute("error",e.getMessage());
		}
		return "distrito/listDistritos";
	}
	
	@PostMapping("/save")
	public String saveDistrito(@Valid Distrito distrito, BindingResult result, Model model, SessionStatus status)
			throws Exception{
		if(result.hasErrors()) {
			return "distrito/distrito";
		}else {
			int rpta=dService.insert(distrito);
			if(rpta>0) {
				model.addAttribute("mensaje","Ya existe");
				return "distrito/distrito";
			}else {
				model.addAttribute("mensaje","Se guardó correctamente");
				status.setComplete();
			}
		}
			model.addAttribute("distrito", new Distrito());
			return "redirect:/distritos/list";	
	}
}
