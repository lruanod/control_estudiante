package com.app.web.repositorio;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.app.web.entidad.Grado;

@Repository
public interface GradoRepositorio  extends PagingAndSortingRepository<Grado,Long> {

	/// para  buscar 
	
	 @Query("SELECT g FROM Grado g WHERE g.nombreg LIKE %?1%"
			+ "OR g.seccion LIKE %?1%")
	 public Page<Grado> findAll(Pageable pageable,String palabraClave);
	//public List<Estudiante> findAll(String palabraClave);
	 
}
