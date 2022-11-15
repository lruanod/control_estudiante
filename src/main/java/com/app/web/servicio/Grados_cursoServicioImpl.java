package com.app.web.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app.web.entidad.Grados_curso;
import com.app.web.repositorio.Grados_cursoRepositorio;

@Service
public class Grados_cursoServicioImpl implements Grados_cursoServicio {

	/// ojo crear primero el repositorio

	@Autowired
	private Grados_cursoRepositorio repositorio;
	
	@Override
	@Transactional(readOnly = true)
	public Page<Grados_curso> findAll(Pageable pageable, String palabraClave) {
		//return repositorio.findAll(pageable);	
		if(palabraClave != null) {
			return repositorio.findAll(pageable,palabraClave);
			}
			return  repositorio.findAll(pageable);
	}

	//listar todos
	 @Override
	 @Transactional(readOnly = true)
	 public List<Grados_curso> listarTodosLosGrados_cursos() {
		return (List<Grados_curso>) repositorio.findAll();
	}
	

	// para registro 
	@Override
	public Grados_curso guardarGrados_curso(Grados_curso grados_curso) {
		return repositorio.save(grados_curso);
	}
	// para obtener 
	@Override
	public Grados_curso obtenerGrados_cursoPorId(Long id) {
		return repositorio.findById(id).get();
	}

	// para actualizar 
	@Override
	public Grados_curso actualizarGrados_curso(Grados_curso grados_curso) {
		return repositorio.save(grados_curso);
	}

	// para eliminar
	@Override
	public void eliminarGrados_curso(Long id) {
		repositorio.deleteById(id);;

	}
}
