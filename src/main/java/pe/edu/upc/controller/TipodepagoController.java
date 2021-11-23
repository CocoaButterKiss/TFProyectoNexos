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

import pe.edu.upc.entity.Tipodepago;
import pe.edu.upc.service.ITipodepagoService;

@Controller
@RequestMapping("/tipodepagos")
public class TipodepagoController {

	@Autowired
	private ITipodepagoService tService;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/new")
	public String newTipodepago(Model model) {
		
		model.addAttribute("tipodepago",new Tipodepago());
		return "tipodepago/tipodepago";
	}
	
	
    @GetMapping("/list")
	public String listTipodepagos(Model model) {
		try {
			model.addAttribute("tipodepago",new Tipodepago());
			model.addAttribute("listaTipodepagos", tService.list());
		} catch(Exception e) {
			model.addAttribute("error",e.getMessage());
		}
		return "tipodepago/listTipodepagos";
	}
	
	@PostMapping("/save")
	public String saveTipodepago(@Valid Tipodepago tipodepago, BindingResult result, Model model, SessionStatus status)
			throws Exception{
		if(result.hasErrors()) {
			return "tipodepago/tipodepago";
		}else {
			int rpta=tService.insert(tipodepago);
			if(rpta>0) {
				model.addAttribute("mensaje","Ya existe");
				return "tipodepago/tipodepago";
			}else {
				model.addAttribute("mensaje","Se guardó correctamente");
				status.setComplete();
			}
		}
			model.addAttribute("tipodepago", new Tipodepago());
			return "redirect:/tipodepagos/list";	
	}
	
}
