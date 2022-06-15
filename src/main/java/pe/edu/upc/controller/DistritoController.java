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

import pe.edu.upc.entity.Distrito;
import pe.edu.upc.service.IDistritoService;

@Controller
@RequestMapping("/distritos")
public class DistritoController {

	@Autowired
	private IDistritoService diService;

	@GetMapping("/new")
	public String newDistrito(Model model) {
		model.addAttribute("distrito", new Distrito());
		return "distrito/distrito";
	}

	@PostMapping("/save")
	public String saveCategory(@Valid Distrito distrito, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "distrito/distrito";
		} else {
			int rpta = diService.insert(distrito);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/distrito/distrito";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listDistritos", diService.list());

		return "/distrito/distrito";
	}

	@GetMapping("/list")
	public String listDistritos(Model model) {
		try {
			model.addAttribute("distrito", new Distrito());
			model.addAttribute("listDistritos", diService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/distrito/listDistritos";
	}

	@RequestMapping("/delete")
	public String delete(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				diService.delete(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un distrito");
		}
		model.put("listDistritos", diService.list());

//		return "redirect:/categories/list";
		return "/distrito/listDistritos";
	}

	@GetMapping("/detalle/{id}")
	public String detailsDistrito(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Distrito> distrito = diService.listarId(id);
			if (!distrito.isPresent()) {
				model.addAttribute("info", "Distrito no existe");
				return "redirect:/distritos/list";
			} else {
				model.addAttribute("distrito", distrito.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/distrito/update";
	}


}
