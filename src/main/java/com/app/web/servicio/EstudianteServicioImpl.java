package com.app.web.servicio;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.app.web.entidad.Estudiante;

import com.app.web.repositorio.EstudianteRepositorio;

@Service
public class EstudianteServicioImpl implements EstudianteServicio {

	/// ojo crear primero el repositorio

	@Autowired
	private EstudianteRepositorio repositorio;
	
	
	// para registro estudiante
	@Override
	@Transactional
	public Estudiante guardarEstudiante(Estudiante estudiante) {
		return repositorio.save(estudiante);
	}

	// para obtener estudiante
	@Override
	@Transactional(readOnly = true)
	public Estudiante obtenerEstudiantePorId(Long id) {
		return repositorio.findById(id).orElse(null);
	}

	// para actualizar estudiante
	@Override
	@Transactional
	public Estudiante actualizarEstudiante(Estudiante estudiante) {
		return repositorio.save(estudiante);
	}

	// para eliminar
	@Override
	@Transactional
	public void eliminarEstudiante(Long id) {
		repositorio.deleteById(id);

	}

	
	@Override
	@Transactional(readOnly = true)
	public Page<Estudiante> findAll(Pageable pageable, String palabraClave) {
		//return repositorio.findAll(pageable);	
		if(palabraClave != null) {
			return repositorio.findAll(pageable,palabraClave);
			}
			return  repositorio.findAll(pageable);
	}

	
	 @Override
	 @Transactional(readOnly = true)
	 public List<Estudiante> listarTodosLosEstudiantes() {
		return (List<Estudiante>) repositorio.findAll();
	}
	

	
	

}
