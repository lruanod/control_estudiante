package com.app.web.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.web.entidad.Curso;
import com.app.web.repositorio.CursoRepositorio;


@Service
public class CursoServicioImpl implements CursoServicio {

	/// ojo crear primero el repositorio

			@Autowired
			private CursoRepositorio repositorio;
			
			@Override
			@Transactional(readOnly = true)
			public Page<Curso> findAll(Pageable pageable, String palabraClave) {
				//return repositorio.findAll(pageable);	
				if(palabraClave != null) {
					return repositorio.findAll(pageable,palabraClave);
					}
					return  repositorio.findAll(pageable);
			}

			
			//listar todos
			 @Override
			 @Transactional(readOnly = true)
			 public List<Curso> listarTodosLosCursos() {
				return (List<Curso>) repositorio.findAll();
			}
			
			// para registro 
			@Override
			public Curso guardarCurso(Curso curso) {
				return repositorio.save(curso);
			}
			// para obtener 
			@Override
			public Curso obtenerCursoPorId(Long id) {
				return repositorio.findById(id).get();
			}

			// para actualizar e
			@Override
			public Curso actualizarCurso(Curso curso) {
				return repositorio.save(curso);
			}

			// para eliminar
			@Override
			public void eliminarCurso(Long id) {
				repositorio.deleteById(id);;

			}



}
