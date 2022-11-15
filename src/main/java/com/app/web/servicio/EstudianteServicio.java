package com.app.web.servicio;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.web.entidad.Estudiante;


public interface EstudianteServicio {
	
	
	// guardar estudiante
	public Estudiante guardarEstudiante(Estudiante estudiante);
	
	// obtener estudiante
	public Estudiante obtenerEstudiantePorId(Long id);
	
	// actualizar estudiante
	public Estudiante actualizarEstudiante(Estudiante estudiante);
	// eliminar estudiante 
	public void eliminarEstudiante(Long id);
	
	// paginacion 
	public Page<Estudiante> findAll(Pageable pageable,String palabraClave);
	//listar
	public List<Estudiante> listarTodosLosEstudiantes();

}
