package com.app.web.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.app.web.entidad.Curso;


@Repository
public interface CursoRepositorio extends PagingAndSortingRepository<Curso,Long> {

	/// para  buscar 
	
		 @Query("SELECT c FROM Curso c WHERE c.nombrec LIKE %?1%"
				+ "OR c.creditos LIKE %?1%")
		 public Page<Curso> findAll(Pageable pageable,String palabraClave);
		//public List<Estudiante> findAll(String palabraClave);
	
}
