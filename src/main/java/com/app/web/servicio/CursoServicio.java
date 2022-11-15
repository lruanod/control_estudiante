package com.app.web.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.web.entidad.Curso;



public interface CursoServicio {
	

    // listar 
	public List<Curso> listarTodosLosCursos();
	
	// guardar 
	public Curso guardarCurso(Curso curso);
	
	// obtener 
	public Curso obtenerCursoPorId(Long id);
	
	// actualizar 
	public Curso actualizarCurso(Curso curso);
	// eliminar  
	public void eliminarCurso(Long id);
	
	// paginacion 
	public Page<Curso> findAll(Pageable pageable,String palabraClave);

}
