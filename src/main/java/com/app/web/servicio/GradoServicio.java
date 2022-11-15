package com.app.web.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.app.web.entidad.Grado;

public interface GradoServicio {

	     // listar estudiantes
		public List<Grado> listarTodosLosGrados();
		
		// guardar estudiante
		public Grado guardarGrado(Grado grado);
		
		// obtener estudiante
		public Grado obtenerGradoPorId(Long id);
		
		// actualizar estudiante
		public Grado actualizarGrado(Grado grado);
		// eliminar estudiante 
		public void eliminarGrado(Long id);
		
		// paginacion 
		public Page<Grado> findAll(Pageable pageable,String palabraClave);
}
