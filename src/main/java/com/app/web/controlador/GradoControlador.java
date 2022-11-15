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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.app.web.entidad.Grado;
import com.app.web.servicio.GradoServicio;
import com.app.web.util.GradoExporterExcel;
import com.app.web.util.GradoExporterPDF;
import com.app.web.util.PageRender;
import com.lowagie.text.DocumentException;

@Controller
public class GradoControlador {	
	@Autowired
	private GradoServicio serviciog;

	/// listar los grados
	@GetMapping( "/grados")
	public String listar(Model modelo, @RequestParam(name="page", defaultValue="0")int page,@Param("palabraClave") String palabraClave) {
		Pageable pageRequest= PageRequest.of(page, 5);
		
		Page<Grado> listaGrados= serviciog.findAll(pageRequest,palabraClave);
		PageRender<Grado> pageRender = new PageRender<>("/grados", listaGrados);
		modelo.addAttribute("grados", listaGrados);
		modelo.addAttribute("palabraClave", palabraClave);
		modelo.addAttribute("page", pageRender);
		return "grados/grados"; // nos retorna al archivo frontend html
	}
	

	// ver el formulario grado
	@GetMapping("/grados/nuevo")
	ModelAndView crearGradoFormulario(Model model) {
		return new ModelAndView("grados/crear_grado").addObject("grado", new Grado());
	}

	// guardar grado
	@PostMapping("/grados")
	ModelAndView guardarGrado(@Validated Grado grado,BindingResult bindingResult, RedirectAttributes ra, Model model) {
		if(bindingResult.hasErrors()) {
			return new ModelAndView("grados/crear_grado").addObject("grado", grado);
		} 
			
			serviciog.guardarGrado(grado);
		    ra.addFlashAttribute("msgExito","El grado ha sido registrado correctamente");
			return  new ModelAndView("redirect:/grados"); // nos retorna al archivo frontend html
		
	}

	// ver el formulario editar grado
	@GetMapping("/grados/form/{id}")
	ModelAndView  MostrarFormularioEditar(@PathVariable Long id) {
		return new ModelAndView("grados/editar_grado").addObject("grado", serviciog.obtenerGradoPorId(id));
	}

	// actualizar grado
	@PostMapping("/grados/editar/{id}")
	ModelAndView  actualizarEstudiante(@PathVariable Long id, @Validated  Grado grado,
			BindingResult bindingResult,RedirectAttributes ra) {
		
		
		if(bindingResult.hasErrors()) {
			return new ModelAndView("grados/editar_grado").addObject("grado", grado);
		} 
		
		grado.setId(id);
		grado.setNombreg(grado.getNombreg());
		grado.setSeccion(grado.getSeccion());
		serviciog.actualizarGrado(grado);	
		ra.addFlashAttribute("msgExito","El grado ha sido editado correctamente");
		return  new ModelAndView("redirect:/grados");
	}

	// eliminar grado
	@GetMapping("/grados/eliminar/{id}")
	public String EliminarGrado(@PathVariable Long id,RedirectAttributes ra) {
		serviciog.eliminarGrado(id);
		ra.addFlashAttribute("msgEliminado","El grado ha sido eliminado correctamente");
		return "redirect:/grados"; // nos retorna al archivo frontend html
	}
	
	@GetMapping("/gexportarPDF")
	public void exportarListadoGradosEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("aplication/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual=dateFormatter.format(new Date());
		String cabecera ="Content-Disposition";
		String valor="attachment; filename=Grados_"+fechaActual+".pdf";
		response.setHeader(cabecera, valor);
		List<Grado> grados = serviciog.listarTodosLosGrados();
		
		GradoExporterPDF exporter = new GradoExporterPDF(grados);
		exporter.exportar(response);
	}
	
	@GetMapping("/gexportarExcel")
	public void exportarListadoEstudiantesEnExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("aplication/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual=dateFormatter.format(new Date());
		String cabecera ="Content-Disposition";
		String valor="attachment; filename=Grados_"+fechaActual+".xls";
		response.setHeader(cabecera, valor);
		List<Grado> grados = serviciog.listarTodosLosGrados();
		
		GradoExporterExcel exporter = new GradoExporterExcel(grados);
		exporter.exportar(response);
	}

	
}
