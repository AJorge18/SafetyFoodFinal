package pe.edu.upc.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.Plato;
import pe.edu.upc.service.IPlatoService;

@Controller
@RequestMapping("/platos")
public class PlatoController {

	@Autowired
	private IPlatoService plService;

	@GetMapping("/new")
	public String newPlato(Model model) {
		model.addAttribute("plato", new Plato());
		return "plato/plato";
	}

	@PostMapping("/save")
	public String savePlato(@Valid Plato plato, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "plato/plato";
		} else {
			int rpta = plService.insert(plato);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/plato/plato";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listPlatos", plService.list());

		return "/plato/plato";
	}

	@GetMapping("/list")
	public String listPlatos(Model model) {
		try {
			model.addAttribute("plato", new Plato());
			model.addAttribute("listPlatos", plService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/plato/listPlatos";
	}

	@RequestMapping("/delete")
	public String delete(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				plService.delete(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un plato");
		}
		model.put("listPlatos", plService.list());

//		return "redirect:/categories/list";
		return "/plato/listPlatos";
	}

	@GetMapping("/detalle/{id}")
	public String detailsPlato(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Plato> plato = plService.listarId(id);
			if (!plato.isPresent()) {
				model.addAttribute("info", "Plato no existe");
				return "redirect:/platos/list";
			} else {
				model.addAttribute("plato", plato.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/plato/update";
	}


}
