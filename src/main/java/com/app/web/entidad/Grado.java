package com.app.web.entidad;

import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="grados")
public class Grado {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
	    @NotBlank
	    @Size(max =75)
	    @Column(name="nombreg",nullable = false, length=75)
	    private String nombreg;
	    
	    @NotBlank
	    @Size(max =75)
	    @Column(name="seccion",nullable = false, length=75)
	    private String seccion;
	    
	    @OneToMany(mappedBy="grado")
	    List<Estudiante> estudianteList;
	    
	    @OneToMany(mappedBy="grado")
	    List<Grados_curso> gradoscursoList;

		public Grado() {
			
		}

       
	


		public Grado(@NotBlank @Size(max = 75) String nombreg, @NotBlank @Size(max = 75) String seccion) {
		
			this.nombreg = nombreg;
			this.seccion = seccion;
		}





		public Grado(Long id, @NotBlank @Size(max = 75) String nombreg, @NotBlank @Size(max = 75) String seccion) {
			
			this.id = id;
			this.nombreg = nombreg;
			this.seccion = seccion;
		}






		public Long getId() {
			return id;
		}





		public void setId(Long id) {
			this.id = id;
		}





		public String getNombreg() {
			return nombreg;
		}





		public void setNombreg(String nombreg) {
			this.nombreg = nombreg;
		}





		public String getSeccion() {
			return seccion;
		}





		public void setSeccion(String seccion) {
			this.seccion = seccion;
		}





		@Override
		public String toString() {
			return "Grados [id=" + id + ", nombreg=" + nombreg + ", seccion=" + seccion + "]";
		}
	    
	  	
	
	
	
}
