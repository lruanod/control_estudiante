package com.app.web.controlador;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.web.entidad.Estudiante;
import com.app.web.entidad.Grado;
import com.app.web.servicio.EstudianteServicio;
import com.app.web.servicio.GradoServicio;
import com.app.web.util.EstudianteExporterExcel;
import com.app.web.util.EstudianteExporterPDF;
import com.app.web.util.PageRender;
import com.lowagie.text.DocumentException;


@Controller
public class EstudianteControlador {
	@Autowired
	private EstudianteServicio servicio;
	
	@Autowired
	private GradoServicio serviciog;

	/// listar los estudiantes
	@RequestMapping({ "/estudiantes", "/" })
	public String listarEstudiantes(Model modelo, @RequestParam(name="page", defaultValue="0")int page,@Param("palabraClave") String palabraClave) {
		Pageable pageRequest= PageRequest.of(page, 5);
		
		Page<Estudiante> listaEstudiantes= servicio.findAll(pageRequest,palabraClave);
		PageRender<Estudiante> pageRender = new PageRender<>("/estudiantes", listaEstudiantes);
		modelo.addAttribute("estudiantes", listaEstudiantes);
		modelo.addAttribute("palabraClave", palabraClave);
		modelo.addAttribute("page", pageRender);
		return "estudiantes/estudiantes"; // nos retorna al archivo frontend html
	}

	// ver el formulario estudiante
	@GetMapping("/estudiantes/nuevo")
	ModelAndView crearEstudianteFormulario(Model model) {
		List<Grado> listGrados = serviciog.listarTodosLosGrados();
		model.addAttribute("grados", listGrados);
		return new ModelAndView("estudiantes/crear_estudiante").addObject("estudiante", new Estudiante());
	}

	// guardar estudiante
	@PostMapping("/estudiantes")
	ModelAndView guardarEstudiante(@Validated Estudiante estudiante,BindingResult bindingResult, RedirectAttributes ra, Model model, SessionStatus status) {
		if(bindingResult.hasErrors()) {
			List<Grado> listGrados = serviciog.listarTodosLosGrados();
			model.addAttribute("grados", listGrados);
			return new ModelAndView("estudiantes/crear_estudiante").addObject("estudiante", estudiante);
		} 
			
			servicio.guardarEstudiante(estudiante);
			status.setComplete();
		    ra.addFlashAttribute("msgExito","El estudiante ha sido registrado correctamente");
		    
			return  new ModelAndView("redirect:/"); // nos retorna al archivo frontend html
		
	}

	// ver el formulario editar estudiante
	@GetMapping("/estudiantes/form/{id}")
	ModelAndView  MostrarFormularioEditar(@PathVariable Long id, Model model) {
		List<Grado> listGrados = serviciog.listarTodosLosGrados();
		Estudiante estudiante=servicio.obtenerEstudiantePorId(id);
		model.addAttribute("grados", listGrados);
		return new ModelAndView("estudiantes/editar_estudiante").addObject("estudiante", estudiante);
	}

	// actualizar estudiante
	@PostMapping("/estudiantes/editar/{id}")
	ModelAndView  actualizarEstudiante(@PathVariable Long id, @Validated Estudiante estudiante, 
			BindingResult bindingResult,RedirectAttributes ra, Model model) {
		
		if(bindingResult.hasErrors()) {
			List<Grado> listGrados = serviciog.listarTodosLosGrados();
			model.addAttribute("grados", listGrados);
			return new ModelAndView("estudiantes/editar_estudiante").addObject("estudiante", estudiante);
		} 
		
		 	estudiante.setId(id);
		    estudiante.setNombre(estudiante.getNombre());
			estudiante.setApellido(estudiante.getApellido());
			estudiante.setEmail(estudiante.getEmail());
		    estudiante.setGrado(estudiante.getGrado());
			servicio.actualizarEstudiante(estudiante);
		ra.addFlashAttribute("msgExito","El estudiante ha sido editado correctamente");
		return  new ModelAndView("redirect:/");
	}

	// eliminar estudiante
	@GetMapping("/estudiantes/eliminar/{id}")
	public String EliminarEstudiante(@PathVariable Long id,RedirectAttributes ra) {
		servicio.eliminarEstudiante(id);
		ra.addFlashAttribute("msgEliminado","El estudiante ha sido eliminado correctamente");
		return "redirect:/estudiantes"; // nos retorna al archivo frontend html
	}

	
	@GetMapping("/exportarPDF")
	public void exportarListadoEstudiantesEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("aplication/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual=dateFormatter.format(new Date());
		String cabecera ="Content-Disposition";
		String valor="attachment; filename=Estudiantes_"+fechaActual+".pdf";
		response.setHeader(cabecera, valor);
		List<Estudiante> empleados = servicio.listarTodosLosEstudiantes();
		
		EstudianteExporterPDF exporter = new EstudianteExporterPDF(empleados);
		exporter.exportar(response);
	}
	
	@GetMapping("/exportarExcel")
	public void exportarListadoEstudiantesEnExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("aplication/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual=dateFormatter.format(new Date());
		String cabecera ="Content-Disposition";
		String valor="attachment; filename=Estudiantes_"+fechaActual+".xls";
		response.setHeader(cabecera, valor);
		List<Estudiante> empleados = servicio.listarTodosLosEstudiantes();
		
		EstudianteExporterExcel exporter = new EstudianteExporterExcel(empleados);
		exporter.exportar(response);
	}
	
}
