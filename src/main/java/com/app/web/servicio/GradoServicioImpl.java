package com.app.web.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app.web.entidad.Grado;
import com.app.web.repositorio.GradoRepositorio;

@Service
public class GradoServicioImpl  implements GradoServicio {
	/// ojo crear primero el repositorio

		@Autowired
		private GradoRepositorio repositorio;

		@Override
		@Transactional(readOnly = true)
		public Page<Grado> findAll(Pageable pageable, String palabraClave) {
			//return repositorio.findAll(pageable);	
			if(palabraClave != null) {
				return repositorio.findAll(pageable,palabraClave);
				}
				return  repositorio.findAll(pageable);
		}

		// para registro estudiante
		@Override
		@Transactional
		public Grado guardarGrado(Grado grado) {
			return repositorio.save(grado);
		}

		// para obtener estudiante
		@Override
		@Transactional(readOnly = true)
		public Grado obtenerGradoPorId(Long id) {
			return repositorio.findById(id).get();
		}

		// para actualizar estudiante
		@Override
		@Transactional
		public Grado actualizarGrado(Grado grado) {
			return repositorio.save(grado);
		}

		// para eliminar
		@Override
		@Transactional
		public void eliminarGrado(Long id) {
			repositorio.deleteById(id);

		}
		//listar todos
		 @Override
		 @Transactional(readOnly = true)
		 public List<Grado> listarTodosLosGrados() {
			return (List<Grado>) repositorio.findAll();
		}
		

}
