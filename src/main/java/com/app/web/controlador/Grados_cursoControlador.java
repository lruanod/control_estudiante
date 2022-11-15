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
import com.app.web.entidad.Curso;
import com.app.web.entidad.Grado;
import com.app.web.entidad.Grados_curso;
import com.app.web.servicio.CursoServicio;
import com.app.web.servicio.GradoServicio;
import com.app.web.servicio.Grados_cursoServicio;
import com.app.web.util.Grados_cursoExporterExcel;
import com.app.web.util.Grados_cursoExporterPDF;
import com.app.web.util.PageRender;
import com.lowagie.text.DocumentException;

@Controller
public class Grados_cursoControlador {
	@Autowired
	private Grados_cursoServicio servicio;
	
	@Autowired
	private GradoServicio serviciog;
	
	@Autowired
	private CursoServicio servicioc;

	/// listar los grados_curso
	@GetMapping( "/grados_cursos")
	public String listar(Model modelo, @RequestParam(name="page", defaultValue="0")int page,@Param("palabraClave") String palabraClave) {
		Pageable pageRequest= PageRequest.of(page, 5);
		
		Page<Grados_curso> listaGrados_cursos= servicio.findAll(pageRequest,palabraClave);
		PageRender<Grados_curso> pageRender = new PageRender<>("/grados_cursos", listaGrados_cursos);
		modelo.addAttribute("grados_cursos", listaGrados_cursos);
		modelo.addAttribute("palabraClave", palabraClave);
		modelo.addAttribute("page", pageRender);
		return "grados_cursos/list"; // nos retorna al archivo frontend html
	}
	
	// ver el formulario 
	@GetMapping("/grados_cursos/nuevo")
	ModelAndView crearGrados_cursoFormulario(Model model) {
		List<Grado> listGrados = serviciog.listarTodosLosGrados();
		model.addAttribute("grados", listGrados);
		List<Curso> listCursos = servicioc.listarTodosLosCursos();
		model.addAttribute("cursos", listCursos);
		return new ModelAndView("grados_cursos/crear").addObject("grados_curso", new Grados_curso());
	}

	// guardar 
	@PostMapping("/grados_cursos")
	ModelAndView guardarEstudiante(@Validated Grados_curso grados_curso,BindingResult bindingResult, RedirectAttributes ra, Model model) {
		if(bindingResult.hasErrors()) {
			List<Grado> listGrados = serviciog.listarTodosLosGrados();
			model.addAttribute("grados", listGrados);
			List<Curso> listCursos = servicioc.listarTodosLosCursos();
			model.addAttribute("crusos", listCursos);
			return new ModelAndView("grados_cursos/crear").addObject("grados_curso", grados_curso);
		} 
			
			servicio.guardarGrados_curso(grados_curso);
		    ra.addFlashAttribute("msgExito","La Asignación ha sido registrada correctamente");
			return  new ModelAndView("redirect:/grados_cursos"); // nos retorna al archivo frontend html
		
	}

	// ver el formulario
	@GetMapping("/grados_cursos/form/{id}")
	ModelAndView  MostrarFormularioEditar(@PathVariable Long id, Model model) {
		List<Grado> listGrados = serviciog.listarTodosLosGrados();
		model.addAttribute("grados", listGrados);
		List<Curso> listCursos = servicioc.listarTodosLosCursos();
		model.addAttribute("cursos", listCursos);
		Grados_curso grados_curso=servicio.obtenerGrados_cursoPorId(id);
		return new ModelAndView("grados_cursos/editar").addObject("grados_curso", grados_curso);
	}

	// actualizar 
	@PostMapping("/grados_cursos/editar/{id}")
	ModelAndView  actualizarGrados_curso(@PathVariable Long id, @Validated Grados_curso grados_curso, 
			BindingResult bindingResult,RedirectAttributes ra, Model model) {
		
		if(bindingResult.hasErrors()) {
			List<Grado> listGrados = serviciog.listarTodosLosGrados();
			model.addAttribute("grados", listGrados);
			List<Curso> listCursos = servicioc.listarTodosLosCursos();
			model.addAttribute("cursos", listCursos);
			return new ModelAndView("grados_cursos/editar").addObject("grados_curso", grados_curso);
		} 
		
		    grados_curso.setId(id);
		    grados_curso.setGrado(grados_curso.getGrado());
		    grados_curso.setCurso(grados_curso.getCurso());
			servicio.actualizarGrados_curso(grados_curso);
		ra.addFlashAttribute("msgExito","La asignación ha sido editada correctamente");
		return  new ModelAndView("redirect:/grados_cursos");
	}

	// eliminar estudiante
	@GetMapping("/grados_cursos/eliminar/{id}")
	public String EliminarGrados_curso(@PathVariable Long id,RedirectAttributes ra) {
		servicio.eliminarGrados_curso(id);
		ra.addFlashAttribute("msgEliminado","La asignación ha sido eliminada correctamente");
		return "redirect:/grados_cursos"; // nos retorna al archivo frontend html
	}
	
	@GetMapping("/gcexportarPDF")
	public void exportarListadoGradosEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("aplication/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual=dateFormatter.format(new Date());
		String cabecera ="Content-Disposition";
		String valor="attachment; filename=Grados_cursos_"+fechaActual+".pdf";
		response.setHeader(cabecera, valor);
		List<Grados_curso> grados_cursos = servicio.listarTodosLosGrados_cursos();
		
		Grados_cursoExporterPDF exporter = new Grados_cursoExporterPDF(grados_cursos);
		exporter.exportar(response);
	}
	
	@GetMapping("/gcexportarExcel")
	public void exportarListadoEstudiantesEnExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("aplication/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual=dateFormatter.format(new Date());
		String cabecera ="Content-Disposition";
		String valor="attachment; filename=Grados_cursos_"+fechaActual+".xls";
		response.setHeader(cabecera, valor);
		List<Grados_curso> grados_cursos = servicio.listarTodosLosGrados_cursos();
		
		Grados_cursoExporterExcel exporter = new Grados_cursoExporterExcel(grados_cursos);
		exporter.exportar(response);
	}

}
