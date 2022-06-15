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

import pe.edu.upc.entity.Departamento;
import pe.edu.upc.service.IDepartamentoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

	@Autowired
	private IDepartamentoService deService;

	@GetMapping("/new")
	public String newDepartamento(Model model) {
		model.addAttribute("departamento", new Departamento());
		return "departamento/departamento";
	}

	@PostMapping("/save")
	public String saveDepartamento(@Valid Departamento departamento, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "departamento/departamento";
		} else {
			int rpta = deService.insert(departamento);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/departamento/departamento";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listDepartamentos", deService.list());

		return "/departamento/departamento";
	}

	@GetMapping("/list")
	public String listDepartamentos(Model model) {
		try {
			model.addAttribute("departamento", new Departamento());
			model.addAttribute("listDepartamentos", deService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/departamento/listDepartamentos";
	}

	@RequestMapping("/delete")
	public String delete(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				deService.delete(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un departamento");
		}
		model.put("listDepartamentos", deService.list());

//		return "redirect:/categories/list";
		return "/departamento/listDepartamentos";
	}

	@GetMapping("/detalle/{id}")
	public String detailsDepartamento(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Departamento> departamento = deService.listarId(id);
			if (!departamento.isPresent()) {
				model.addAttribute("info", "Departamento no existe");
				return "redirect:/departamentos/list";
			} else {
				model.addAttribute("departamento", departamento.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/departamento/update";
	}


}
