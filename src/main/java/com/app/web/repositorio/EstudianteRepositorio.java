package com.app.web.repositorio;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.app.web.entidad.Estudiante;

@Repository
public interface EstudianteRepositorio extends PagingAndSortingRepository<Estudiante,Long>{
	
/// para  buscar 
	
 @Query("SELECT e FROM Estudiante e WHERE e.nombre LIKE %?1%"
		+ "OR e.apellido LIKE %?1%"
		+ "OR e.grado.nombreg LIKE %?1%")
 public Page<Estudiante> findAll(Pageable pageable,String palabraClave);
//public List<Estudiante> findAll(String palabraClave);
 


}


