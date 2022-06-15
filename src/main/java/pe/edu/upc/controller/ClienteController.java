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

import pe.edu.upc.entity.Cliente;
import pe.edu.upc.service.IClienteService;

@Controller
@RequestMapping("/clientes")

public class ClienteController {

	@Autowired
	private IClienteService clService;
	@GetMapping("/new")
	public String newRestaurante(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "cliente/cliente";
	}

	@PostMapping("/save")
	public String saveRestaurante(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "cliente/cliente";
		} else {
			int rpta = clService.insert(cliente);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/cliente/cliente";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listClientes", clService.list());

		return "/cliente/listClientes";
	}

	@GetMapping("/list")
	public String listClientes(Model model) {
		try {
			model.addAttribute("cliente", new Cliente());
			model.addAttribute("listClientes", clService.list());
			
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/cliente/listClientes";
	}
	
	@GetMapping("/listvolver")
	public String listClientesvolver(Model model) {
		try {
			model.addAttribute("cliente", new Cliente());
			model.addAttribute("listClientes", clService.list());
			
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/cliente/find";
	}

	@GetMapping("/listFind")
	public String listClienteFind(Model model) {
		try {
			model.addAttribute("cliente", new Cliente());
			model.addAttribute("listClientes", clService.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/cliente/find";
	}

	@RequestMapping("/find")
	public String find(Map<String, Object> model, @ModelAttribute Cliente cliente) throws ParseException {

		List<Cliente> listClientes;

		cliente.setName(cliente.getName());
		listClientes = clService.fetchClienteByName(cliente.getName());


		if (listClientes.isEmpty()) {
			listClientes = clService.findByNameClienteLikeIgnoreCase(cliente.getName());
		}

		if (listClientes.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listClientes", listClientes);
		return "cliente/find";

	}

	@RequestMapping("/delete")
	public String deleteCliente(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				clService.delete(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un Cliente");
		}
		model.put("listClientes", clService.list());

//		return "redirect:/categories/list";
		return "/cliente/listClientes";
	}

	@GetMapping(value = "/list/{name}", produces = { "application/json" })
	public @ResponseBody List<Cliente> fetchClientes(@PathVariable String name, Model model) {
		List<Cliente> clientes = null;
		try {
			clientes = clService.fetchClienteByName(name);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return clientes;
	}

	@GetMapping(value = "/view/{id}")
	public String ver(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {
		Optional<Cliente> cliente = clService.findById(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El Cliente no existe en la base de datos");
			return "redirect:/clientes/list";
		}
		model.put("cliente", cliente.get());
		return "cliente/view";
	}
	
	@GetMapping(value = "/viewvolver/{id}")
	public String vervolver(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {
		Optional<Cliente> cliente = clService.findById(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El Cliente no existe en la base de datos");
			return "redirect:/clientes/listvolver";
		}
		model.put("cliente", cliente.get());
		return "cliente/viewvolver";
	}
	
	@GetMapping("/detalle/{id}")
	public String detailsCliente(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Cliente> cliente = clService.listarId(id);
		
			if (!cliente.isPresent()) {
				model.addAttribute("info", "Cliente no existe");
				return "redirect:/clientes/list";
			} else {

				model.addAttribute("cliente", cliente.get());				
				return "/cliente/update";
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/cliente/update";
	}
	
}
