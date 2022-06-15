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

import pe.edu.upc.entity.Carta;
import pe.edu.upc.service.ICartaService;
import pe.edu.upc.service.IRestauranteService;
import pe.edu.upc.service.IPlatoService;

@Controller
@RequestMapping("/cartas")

public class CartaController {
	@Autowired
	private ICartaService caService;
	@Autowired
	private IRestauranteService rtService;
	@Autowired
	private IPlatoService plService;

	@GetMapping("/new")
	public String newCarta(Model model) {
		model.addAttribute("carta", new Carta());
		model.addAttribute("listRestaurantes", rtService.list());
		model.addAttribute("listPlatos", plService.list());
		return "carta/carta";
	}

	@PostMapping("/save")
	public String saveCarta(@Valid Carta carta, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listRestaurantes", rtService.list());
			model.addAttribute("listPlatos", plService.list());
			return "carta/carta";
		} else {
			int rpta = caService.insert(carta);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				model.addAttribute("listRestaurantes", rtService.list());
				model.addAttribute("listPlatos", plService.list());
				return "/carta/carta";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listCartas", caService.list());

		return "/carta/listCartas";
	}

	@GetMapping("/list")
	public String listCarta(Model model) {
		try {
			model.addAttribute("carta", new Carta());
			model.addAttribute("listCartas", caService.list());
			
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/carta/listCartas";
	}
	
	@GetMapping("/listvolver")
	public String listCartavolver(Model model) {
		try {
			model.addAttribute("carta", new Carta());
			model.addAttribute("listCartas", caService.list());
			
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/carta/find";
	}

	@GetMapping("/listFind")
	public String listCartaFind(Model model) {
		try {
			model.addAttribute("carta", new Carta());
			model.addAttribute("listCartas", caService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/carta/find";
	}

	@RequestMapping("/find")
	public String find(Map<String, Object> model, @ModelAttribute Carta carta) throws ParseException {
		
		List<Carta> listCartas;
		
		carta.setName(carta.getName());
		listCartas = caService.fetchCartaByName(carta.getName());

		if (listCartas.isEmpty()) {
			listCartas = caService.fetchCartaByRestauranteName(carta.getName());
		}
		
		if (listCartas.isEmpty()) {
			listCartas = caService.findByNameCartaLikeIgnoreCase(carta.getName());
		}

		if (listCartas.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listCartas", listCartas);
		return "carta/find";

	}

	@RequestMapping("/delete")
	public String deleteCarta(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				caService.delete(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un restaurante");
		}
		model.put("listCartas", caService.list());

//		return "redirect:/categories/list";
		return "/carta/listCartas";
	}

	@GetMapping(value = "/list/{name}", produces = { "application/json" })
	public @ResponseBody List<Carta> fetchCartas(@PathVariable String name, Model model) {
		List<Carta> cartas = null;
		try {
			cartas = caService.fetchCartaByName(name);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return cartas;
	}

	@GetMapping(value = "/view/{id}")
	public String ver(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Carta> carta = caService.findById(id);
		if (carta == null) {
			flash.addFlashAttribute("error", "La Carta no existe en la base de datos");
			return "redirect:/cartas/list";
		}

		model.put("carta", carta.get());

		return "carta/view";
	}
	
	@GetMapping(value = "/viewvolver/{id}")
	public String vervolver(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Carta> carta = caService.findById(id);
		if (carta == null) {
			flash.addFlashAttribute("error", "La Carta no existe en la base de datos");
			return "redirect:/cartas/listvolver";
		}

		model.put("carta", carta.get());

		return "carta/viewvolver";
	}
	
	@GetMapping("/detalle/{id}")
	public String detailsCarta(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Carta> carta = caService.listarId(id);
		
			if (!carta.isPresent()) {
				model.addAttribute("info", "Carta no existe");
				return "redirect:/cartas/list";
			} else {
				model.addAttribute("carta", carta.get());
				model.addAttribute("listRestaurantes", rtService.list());
			    model.addAttribute("listPlatos", plService.list());
			    return "/carta/update";
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/carta/update";
	}
	
}
