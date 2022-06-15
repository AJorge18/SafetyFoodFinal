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

import pe.edu.upc.entity.Tipocertificado;
import pe.edu.upc.service.ITipocertificadoService;

@Controller
@RequestMapping("/tipocertificados")
public class TipocertificadoController {

	@Autowired
	private ITipocertificadoService tcService;

	@GetMapping("/new")
	public String newTipocertificado(Model model) {
		model.addAttribute("tipocertificado", new Tipocertificado());
		return "tipocertificado/tipocertificado";
	}

	@PostMapping("/save")
	public String saveTipocertificado(@Valid Tipocertificado tipocertificado, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "tipocertificado/tipocertificado";
		} else {
			int rpta = tcService.insert(tipocertificado);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/tipocertificado/tipocertificado";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listTipocertificados", tcService.list());

		return "/tipocertificado/tipocertificado";
	}

	@GetMapping("/list")
	public String listTipocertificados(Model model) {
		try {
			model.addAttribute("tipocertificado", new Tipocertificado());
			model.addAttribute("listTipocertificados", tcService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/tipocertificado/listTipocertificados";
	}

	@RequestMapping("/delete")
	public String delete(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				tcService.delete(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un tipocertificado");
		}
		model.put("listTipocertificados", tcService.list());

//		return "redirect:/categories/list";
		return "/tipocertificado/listTipocertificados";
	}

	@GetMapping("/detalle/{id}")
	public String detailsTipocertificado(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Tipocertificado> tipocertificado = tcService.listarId(id);
			if (!tipocertificado.isPresent()) {
				model.addAttribute("info", "Tipocertificado no existe");
				return "redirect:/tipocertificados/list";
			} else {
				model.addAttribute("tipocertificado", tipocertificado.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/tipocertificado/update";
	}


}
