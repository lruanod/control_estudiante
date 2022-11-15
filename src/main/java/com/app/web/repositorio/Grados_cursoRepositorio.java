package com.app.web.repositorio;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.app.web.entidad.Grados_curso;

@Repository
public interface Grados_cursoRepositorio extends PagingAndSortingRepository<Grados_curso,Long>  {

	/// para  buscar 
	
	 @Query("SELECT g FROM Grados_curso g WHERE g.grado.nombreg LIKE %?1%"
			+ "OR g.grado.seccion LIKE %?1%"
			+ "OR g.curso.nombrec LIKE %?1%"
			+ "OR g.curso.creditos LIKE %?1%")
	 public Page<Grados_curso> findAll(Pageable pageable,String palabraClave);
	//public List<Estudiante> findAll(String palabraClave);
	 
}
