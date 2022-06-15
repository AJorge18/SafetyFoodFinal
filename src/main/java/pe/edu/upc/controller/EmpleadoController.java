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

import pe.edu.upc.entity.Empleado;
import pe.edu.upc.service.IEmpleadoService;
import pe.edu.upc.service.IRestauranteService;
import pe.edu.upc.service.ITipocertificadoService;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {
	@Autowired
	private IEmpleadoService epService;
	@Autowired
	private IRestauranteService rtService;
	@Autowired
	private ITipocertificadoService tcService;
	
	@GetMapping("/new")
	public String newEmpleado(Model model) {
		model.addAttribute("empleado", new Empleado());
		model.addAttribute("listRestaurantes", rtService.list());
		model.addAttribute("listTipocertificados", tcService.list());
		return "empleado/empleado";
	}
	@PostMapping("/save")
	public String saveEmpleado(@Valid Empleado empleado, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {			
			model.addAttribute("listRestaurantes", rtService.list());
			model.addAttribute("listTipocertificados", tcService.list());
			return "empleado/empleado";
		} else {
			int rpta = epService.insert(empleado);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				model.addAttribute("listRestaurantes", rtService.list());
				model.addAttribute("listTipocertificados", tcService.list());
				return "empleado/empleado";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listEmpleados", epService.list());

		return "empleado/listEmpleados";
	}
	@GetMapping("/list")
	public String listEmpleado(Model model) {
		try {
			model.addAttribute("empleado", new Empleado());
			model.addAttribute("listEmpleados", epService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/empleado/listEmpleados";
	}
	
	@GetMapping("/listvolver")
	public String listEmpleadovolver(Model model) {
		try {
			model.addAttribute("empleado", new Empleado());
			model.addAttribute("listEmpleados", epService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/empleado/find";
	}
	
	@GetMapping("/listFind")
	public String listEmpleadoFind(Model model) {
		try {
			model.addAttribute("empleado", new Empleado());
			model.addAttribute("listEmpleados", epService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/empleado/find";
	}
	@RequestMapping("/find")
	public String find(Map<String, Object> model, @ModelAttribute Empleado empleado) throws ParseException {

		List<Empleado> listEmpleados;

		empleado.setName(empleado.getName());
		listEmpleados = epService.fetchEmpleadoByName(empleado.getName());

		if (listEmpleados.isEmpty()) {
			listEmpleados = epService.fetchEmpleadoByRestauranteName(empleado.getName());
		}

		if (listEmpleados.isEmpty()) {
			listEmpleados = epService.findByNameEmpleadoLikeIgnoreCase(empleado.getName());
		}

		if (listEmpleados.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listEmpleados", listEmpleados);
		return "/empleado/find";
	}
	@RequestMapping("/delete")
	public String deleteEmpleado(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				epService.delete(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un empleado");
		}
		model.put("listEmpleados", epService.list());

//		return "redirect:/categories/list";
		return "/empleado/listEmpleados";
	}
	@GetMapping(value = "/list/{name}", produces = { "application/json" })
	public @ResponseBody List<Empleado> fetchEmpleados(@PathVariable String name, Model model) {
		List<Empleado> empleados = null;
		try {
			empleados = epService.fetchEmpleadoByName(name);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return empleados;
	}
	@GetMapping(value = "/view/{id}")
	public String ver(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Empleado> empleado = epService.findById(id);
		if (empleado == null) {
			flash.addFlashAttribute("error", "El Empleado no existe en la base de datos");
			return "redirect:/empleados/list";
		}

		model.put("empleado", empleado.get());

		return "empleado/view";
	}
	
	@GetMapping(value = "/viewvolver/{id}")
	public String vervolver(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Empleado> empleado = epService.findById(id);
		if (empleado == null) {
			flash.addFlashAttribute("error", "El Empleado no existe en la base de datos");
			return "redirect:/empleados/listvolver";
		}

		model.put("empleado", empleado.get());

		return "empleado/viewvolver";
	}
	@GetMapping("/detalle/{id}")
	public String detailsEmpleado(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Empleado> empleado = epService.listarId(id);
			if (!empleado.isPresent()) {
				model.addAttribute("info", "Empleado no existe");
				return "redirect:/empleados/list";
			} else {
				model.addAttribute("empleado", empleado.get());
				model.addAttribute("listRestaurantes", rtService.list());
				model.addAttribute("listTipocertificados", tcService.list());
				return "/empleado/update";
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/empleado/update";
	}
}
