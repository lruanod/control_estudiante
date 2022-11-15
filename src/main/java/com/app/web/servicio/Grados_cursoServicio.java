package com.app.web.servicio;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.app.web.entidad.Grados_curso;

public interface Grados_cursoServicio {

	   // listar 
		public List<Grados_curso> listarTodosLosGrados_cursos();
		
		// guardar 
		public Grados_curso guardarGrados_curso(Grados_curso grados_curso);
		
		// obtener 
		public Grados_curso obtenerGrados_cursoPorId(Long id);
		
		// actualizar 
		public Grados_curso actualizarGrados_curso(Grados_curso grados_curso);
		// eliminar  
		public void eliminarGrados_curso(Long id);
		
		// paginacion 
		public Page<Grados_curso> findAll(Pageable pageable,String palabraClave);
}
