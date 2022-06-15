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

import pe.edu.upc.entity.Clasificacion;
import pe.edu.upc.service.IRestauranteService;
import pe.edu.upc.service.IClasificacionService;
import pe.edu.upc.service.IClienteService;

@Controller
@RequestMapping("/clasificaciones")

public class ClasificacionController {

	@Autowired
	private IClasificacionService claService;
	@Autowired
	private IRestauranteService rtService;
	@Autowired
	private IClienteService clService;

	@GetMapping("/new")
	public String newClasificacion(Model model) {
		model.addAttribute("clasificacion", new Clasificacion());
		model.addAttribute("listRestaurantes", rtService.list());
		model.addAttribute("listClientes", clService.list());
		return "clasificacion/clasificacion";
	}

	@PostMapping("/save")
	public String saveClasificacion(@Valid Clasificacion clasificacion, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			model.addAttribute("listRestaurantes", rtService.list());
			model.addAttribute("listClientes", clService.list());
			return "clasificacion/clasificacion";
		} else {
			int rpta = claService.insert(clasificacion);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				model.addAttribute("listRestaurantes", rtService.list());
				model.addAttribute("listClientes", clService.list());

				return "/clasificacion/clasificacion";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listClasificaciones", claService.list());

		return "/clasificacion/listClasificaciones";
	}
	@GetMapping("/list")
	public String listClasificacion(Model model) {
		try {
			model.addAttribute("clasificacion", new Clasificacion());
			model.addAttribute("listClasificaciones", claService.list());
			
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/clasificacion/listClasificaciones";
	}
	
	@GetMapping("/listvolver")
	public String listClasificacionvolver(Model model) {
		try {
			model.addAttribute("clasificacion", new Clasificacion());
			model.addAttribute("listClasificaciones", claService.list());
			
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/clasificacion/find";
	}

	@GetMapping("/listFind")
	public String listClasificacionFind(Model model) {
		try {
			model.addAttribute("clasificacion", new Clasificacion());
			model.addAttribute("listClasificaciones", claService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/clasificacion/find";
	}

	@RequestMapping("/find")
	public String find(Map<String, Object> model, @ModelAttribute Clasificacion clasificacion) throws ParseException {

		List<Clasificacion> listClasificaciones;

		clasificacion.setClasificaciondesc(clasificacion.getClasificaciondesc());
		listClasificaciones = claService.fetchClasificaciondescByClasi(clasificacion.getClasificaciondesc());

		if (listClasificaciones.isEmpty()) {
			listClasificaciones = claService.fetchClasificacionByClienteName(clasificacion.getClasificaciondesc());
		}

		if (listClasificaciones.isEmpty()) {
			listClasificaciones = claService.findByclasificaciondescClasificacionLikeIgnoreCase(clasificacion.getClasificaciondesc());
		}

		if (listClasificaciones.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listClasificaciones", listClasificaciones);
		return "clasificacion/find";

	}

	@RequestMapping("/delete")
	public String deleteClasificacion(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				claService.delete(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar clasificacion");
		}
		model.put("listClasificaciones", claService.list());

//		return "redirect:/categories/list";
		return "/clasificacion/listClasificaciones";
	}

	@GetMapping(value = "/list/{clasificaciondesc}", produces = { "application/json" })
	public @ResponseBody List<Clasificacion> fetchClasificaciones(@PathVariable String clasificaciondesc, Model model) {
		List<Clasificacion> clasificaciones = null;
		try {
			clasificaciones = claService.fetchClasificaciondescByClasi(clasificaciondesc);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return clasificaciones;
	}

	@GetMapping(value = "/view/{id}")
	public String ver(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {
		Optional<Clasificacion> clasificacion = claService.findById(id);
		if (clasificacion == null) {
			flash.addFlashAttribute("error", "La Clasificacion no existe en la base de datos");
			return "redirect:/clasificaciones/list";
		}
		model.put("clasificacion", clasificacion.get());
		return "clasificacion/view";
	}
	
	@GetMapping(value = "/viewvolver/{id}")
	public String vervolver(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {
		Optional<Clasificacion> clasificacion = claService.findById(id);
		if (clasificacion == null) {
			flash.addFlashAttribute("error", "La Clasificacion no existe en la base de datos");
			return "redirect:/clasificaciones/listvolver";
		}
		model.put("clasificacion", clasificacion.get());
		return "clasificacion/viewvolver";
	}
	
	@GetMapping("/detalle/{id}")
	public String detailsClasificacion(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Clasificacion> clasificacion = claService.listarId(id);
		
			if (!clasificacion.isPresent()) {
				model.addAttribute("info", "Clasificacion no existe");
				return "redirect:/clasificaciones/list";
			} else {
				model.addAttribute("listRestaurantes", rtService.list());
			    model.addAttribute("listClientes", clService.list());
				model.addAttribute("clasificacion", clasificacion.get());				
				return "/clasificacion/update";
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/clasificacion/update";
	}
	
}
