package pe.edu.upc.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Oferta;
import pe.edu.upc.service.IOfertaService;
import pe.edu.upc.service.IRestauranteService;
import pe.edu.upc.service.IPlatoService;

@Controller
@RequestMapping("/ofertas")

public class OfertaController {

	@Autowired
	private IOfertaService fService;
	@Autowired
	private IRestauranteService rtService;
	@Autowired
	private IPlatoService plService;

	@GetMapping("/new")
	public String newOferta(Model model) {
		model.addAttribute("oferta", new Oferta());
		model.addAttribute("listRestaurantes", rtService.list());
		model.addAttribute("listPlatos", plService.list());
		return "oferta/oferta";
	}

	@PostMapping("/save")
	public String saveOferta(@Valid Oferta oferta, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listRestaurantes", rtService.list());
			model.addAttribute("listPlatos", plService.list());
			return "oferta/oferta";
		} else {
			int rpta = fService.insert(oferta);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				model.addAttribute("listRestaurantes", rtService.list());
				model.addAttribute("listPlatos", plService.list());
				return "/oferta/oferta";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listOfertas", fService.list());

		return "/oferta/listOfertas";
	}

	@GetMapping("/list")
	public String listOferta(Model model) {
		try {
			model.addAttribute("oferta", new Oferta());
			model.addAttribute("listOfertas", fService.list());
			
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/oferta/listOfertas";
	}
	
	@GetMapping("/listvolver")
	public String listOfertavolver(Model model) {
		try {
			model.addAttribute("oferta", new Oferta());
			model.addAttribute("listOfertas", fService.list());
			
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/oferta/find";
	}

	@GetMapping("/listFind")
	public String listOfertaFind(Model model) {
		try {
			model.addAttribute("oferta", new Oferta());
			model.addAttribute("listOfertas", fService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/oferta/find";
	}

	@RequestMapping("/find")
	public String find(Map<String, Object> model, @ModelAttribute Oferta oferta) throws ParseException {
		
		List<Oferta> listOfertas;
		
		oferta.setName(oferta.getName());
		listOfertas = fService.fetchOfertaByName(oferta.getName());

		if (listOfertas.isEmpty()) {
			listOfertas = fService.fetchOFertaByRestauranteName(oferta.getName());
		}
		
		if (listOfertas.isEmpty()) {
			listOfertas = fService.findByNameOfertaLikeIgnoreCase(oferta.getName());
		}

		if (listOfertas.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listOfertas", listOfertas);
		return "oferta/find";

	}

	@RequestMapping("/delete")
	public String deleteOferta(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				fService.delete(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un restaurante");
		}
		model.put("listOfertas", fService.list());

//		return "redirect:/categories/list";
		return "/oferta/listOfertas";
	}

	@GetMapping(value = "/list/{name}", produces = { "application/json" })
	public @ResponseBody List<Oferta> fetchOfertas(@PathVariable String name, Model model) {
		List<Oferta> ofertas = null;
		try {
			ofertas = fService.fetchOfertaByName(name);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return ofertas;
	}

	@GetMapping(value = "/view/{id}")
	public String ver(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Oferta> oferta = fService.findById(id);
		if (oferta == null) {
			flash.addFlashAttribute("error", "La Oferta no existe en la base de datos");
			return "redirect:/ofertas/list";
		}

		model.put("oferta", oferta.get());

		return "oferta/view";
	}
	
	@GetMapping(value = "/viewvolver/{id}")
	public String vervolver(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Oferta> oferta = fService.findById(id);
		if (oferta == null) {
			flash.addFlashAttribute("error", "La Oferta no existe en la base de datos");
			return "redirect:/ofertas/listvolver";
		}

		model.put("oferta", oferta.get());

		return "oferta/viewvolver";
	}
	
	@GetMapping("/detalle/{id}")
	public String detailsOferta(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Oferta> oferta = fService.listarId(id);
		
			if (!oferta.isPresent()) {
				model.addAttribute("info", "Oferta no existe");
				return "redirect:/ofertas/list";
			} else {
				model.addAttribute("oferta", oferta.get());
				model.addAttribute("listRestaurantes", rtService.list());
			    model.addAttribute("listPlatos", plService.list());
			    return "/oferta/update";
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/oferta/update";
	}
	
}
