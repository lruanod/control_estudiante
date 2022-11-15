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
import com.app.web.servicio.CursoServicio;
import com.app.web.util.CursoExporterExcel;
import com.app.web.util.CursoExporterPDF;
import com.app.web.util.PageRender;
import com.lowagie.text.DocumentException;


@Controller
public class CursoControlador {
	@Autowired
	private CursoServicio servicio;
		
		/// listar los grados
		@GetMapping( "/cursos")
		public String listar(Model modelo, @RequestParam(name="page", defaultValue="0")int page,@Param("palabraClave") String palabraClave) {
			Pageable pageRequest= PageRequest.of(page, 5);
			
			Page<Curso> listaCursos= servicio.findAll(pageRequest,palabraClave);
			PageRender<Curso> pageRender = new PageRender<>("/cursos", listaCursos);
			modelo.addAttribute("cursos", listaCursos);
			modelo.addAttribute("palabraClave", palabraClave);
			modelo.addAttribute("page", pageRender);
			return "cursos/list"; // nos retorna al archivo frontend html
		}
		

		// ver el formulario 
		@GetMapping("/cursos/nuevo")
		ModelAndView crearCursoFormulario(Model model) {
			return new ModelAndView("cursos/crear").addObject("curso", new Curso());
		}

		// guardar 
		@PostMapping("/cursos")
		ModelAndView guardarEstudiante(@Validated Curso curso,BindingResult bindingResult, RedirectAttributes ra, Model model) {
			if(bindingResult.hasErrors()) {
				return new ModelAndView("cursos/crear").addObject("curso", curso);
			} 
				
				servicio.guardarCurso(curso);
			    ra.addFlashAttribute("msgExito","El curso ha sido registrado correctamente");
				return  new ModelAndView("redirect:/cursos"); // nos retorna al archivo frontend html
			
		}

		// ver el formulario editar 
		@GetMapping("/cursos/form/{id}")
		ModelAndView  MostrarFormularioEditar(@PathVariable Long id, Model model) {
		
			Curso curso=servicio.obtenerCursoPorId(id);
			
			return new ModelAndView("cursos/editar").addObject("curso", curso);
		}

		// actualizar 
		@PostMapping("/cursos/editar/{id}")
		ModelAndView  actualizarEstudiante(@PathVariable Long id, @Validated Curso curso, 
				BindingResult bindingResult,RedirectAttributes ra, Model model) {
			
			if(bindingResult.hasErrors()) {
				return new ModelAndView("cursos/editar").addObject("curso", curso);
			} 
			
			 	curso.setId(id);
			    curso.setNombrec(curso.getNombrec());
				curso.setCreditos(curso.getCreditos());
				servicio.actualizarCurso(curso);
			ra.addFlashAttribute("msgExito","El curso ha sido editado correctamente");
			return  new ModelAndView("redirect:/cursos");
		}

		// eliminar 
		@GetMapping("/cursos/eliminar/{id}")
		public String EliminarCurso(@PathVariable Long id,RedirectAttributes ra) {
			servicio.eliminarCurso(id);
			ra.addFlashAttribute("msgEliminado","El curso ha sido eliminado correctamente");
			return "redirect:/cursos"; // nos retorna al archivo frontend html
		}
		
		
		@GetMapping("/cexportarPDF")
		public void exportarListadoGradosEnPDF(HttpServletResponse response) throws DocumentException, IOException {
			response.setContentType("aplication/pdf");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String fechaActual=dateFormatter.format(new Date());
			String cabecera ="Content-Disposition";
			String valor="attachment; filename=Cursos_"+fechaActual+".pdf";
			response.setHeader(cabecera, valor);
			List<Curso> cursos = servicio.listarTodosLosCursos();
			
			CursoExporterPDF exporter = new CursoExporterPDF(cursos);
			exporter.exportar(response);
		}
		
		@GetMapping("/cexportarExcel")
		public void exportarListadoEstudiantesEnExcel(HttpServletResponse response) throws DocumentException, IOException {
			response.setContentType("aplication/octet-stream");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String fechaActual=dateFormatter.format(new Date());
			String cabecera ="Content-Disposition";
			String valor="attachment; filename=Cursos_"+fechaActual+".xls";
			response.setHeader(cabecera, valor);
			List<Curso> cursos = servicio.listarTodosLosCursos();
			
			CursoExporterExcel exporter = new CursoExporterExcel(cursos);
			exporter.exportar(response);
		}

}
