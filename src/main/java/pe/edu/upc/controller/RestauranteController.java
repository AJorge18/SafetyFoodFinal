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

import pe.edu.upc.entity.Restaurante;
import pe.edu.upc.service.IDepartamentoService;
import pe.edu.upc.service.IDistritoService;
import pe.edu.upc.service.IRestauranteService;
import pe.edu.upc.service.ITipocertificadoService;
import pe.edu.upc.service.ITiporestauranteService;

@Controller
@RequestMapping("/restaurantes")

public class RestauranteController {

	@Autowired
	private IRestauranteService rtService;
	@Autowired
	private IDepartamentoService deService;
	@Autowired
	private IDistritoService diService;
	@Autowired
	private ITipocertificadoService tcService;
	@Autowired
	private ITiporestauranteService trService;

	@GetMapping("/new")
	public String newRestaurante(Model model) {
		model.addAttribute("restaurante", new Restaurante());
		model.addAttribute("listDepartamentos", deService.list());
		model.addAttribute("listDistritos", diService.list());
		model.addAttribute("listTipocertificados", tcService.list());
		model.addAttribute("listTiporestaurantes", trService.list());
		return "restaurante/restaurante";
	}

	@PostMapping("/save")
	public String saveRestaurante(@Valid Restaurante restaurante, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listDepartamentos", deService.list());
			model.addAttribute("listDistritos", diService.list());
			model.addAttribute("listTipocertificados", tcService.list());
			model.addAttribute("listTiporestaurantes", trService.list());
			return "restaurante/restaurante";
		} else {
			int rpta = rtService.insert(restaurante);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				model.addAttribute("listDepartamentos", deService.list());
				model.addAttribute("listDistritos", diService.list());
				model.addAttribute("listTipocertificados", tcService.list());
				model.addAttribute("listTiporestaurantes", trService.list());
				return "/restaurante/restaurante";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listRestaurantes", rtService.list());

		return "/restaurante/listRestaurantes";
	}

	@GetMapping("/list")
	public String listRestaurante(Model model) {
		try {
			model.addAttribute("restaurante", new Restaurante());
			model.addAttribute("listRestaurantes", rtService.list());
			
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/restaurante/listRestaurantes";
	}
	
	@GetMapping("/listvolver")
	public String listRestaurantevolver(Model model) {
		try {
			model.addAttribute("restaurante", new Restaurante());
			model.addAttribute("listRestaurantes", rtService.list());
			
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/restaurante/find";
	}

	@GetMapping("/listFind")
	public String listRestauranteFind(Model model) {
		try {
			model.addAttribute("restaurante", new Restaurante());
			model.addAttribute("listRestaurantes", rtService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/restaurante/find";
	}

	@RequestMapping("/find")
	public String find(Map<String, Object> model, @ModelAttribute Restaurante restaurante) throws ParseException {

		List<Restaurante> listRestaurantes;

		restaurante.setName(restaurante.getName());
		listRestaurantes = rtService.fetchRestauranteByName(restaurante.getName());

		if (listRestaurantes.isEmpty()) {
			listRestaurantes = rtService.fetchRestauranteByDepartamentoName(restaurante.getName());
		}

		if (listRestaurantes.isEmpty()) {
			listRestaurantes = rtService.findByNameRestauranteLikeIgnoreCase(restaurante.getName());
		}

		if (listRestaurantes.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listRestaurantes", listRestaurantes);
		return "restaurante/find";

	}

	@RequestMapping("/delete")
	public String deleteRestaurante(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				rtService.delete(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un restaurante");
		}
		model.put("listRestaurantes", rtService.list());

//		return "redirect:/categories/list";
		return "/restaurante/listRestaurantes";
	}

	@GetMapping(value = "/list/{name}", produces = { "application/json" })
	public @ResponseBody List<Restaurante> fetchRestaurantes(@PathVariable String name, Model model) {
		List<Restaurante> restaurantes = null;
		try {
			restaurantes = rtService.fetchRestauranteByName(name);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return restaurantes;
	}

	@GetMapping(value = "/view/{id}")
	public String ver(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {
		Optional<Restaurante> restaurante = rtService.findById(id);
		if (restaurante == null) {
			flash.addFlashAttribute("error", "El Restaurante no existe en la base de datos");
			return "redirect:/restaurantes/list";
		}
		model.put("restaurante", restaurante.get());
		return "restaurante/view";
	}
	
	@GetMapping(value = "/viewvolver/{id}")
	public String vervolver(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {
		Optional<Restaurante> restaurante = rtService.findById(id);
		if (restaurante == null) {
			flash.addFlashAttribute("error", "El Restaurante no existe en la base de datos");
			return "redirect:/restaurantes/listvolver";
		}
		model.put("restaurante", restaurante.get());
		return "restaurante/viewvolver";
	}
	
	
	@GetMapping("/detalle/{id}")
	public String detailsRestaurante(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Restaurante> restaurante = rtService.listarId(id);
		
			if (!restaurante.isPresent()) {
				model.addAttribute("info", "Restaurante no existe");
				return "redirect:/restaurantes/list";
			} else {
				model.addAttribute("listDepartamentos", deService.list());
			    model.addAttribute("listDistritos", diService.list());
			    model.addAttribute("listTipocertificados", tcService.list());
			    model.addAttribute("listTiporestaurantes", trService.list());
				model.addAttribute("restaurante", restaurante.get());				
				return "/restaurante/update";
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/restaurante/update";
	}
	
}
