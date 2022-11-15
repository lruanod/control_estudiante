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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name="cursos")
public class Curso {

	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
	    @NotBlank
	    @Size(max =75)
	    @Column(name="nombrec",nullable = false, length=75)
	    private String nombrec;
	    
	    @NotNull
	    @Range(min=0, max=10)
	    @Column(name="creditos",nullable = false)
	    private Integer creditos;
	    
	    @OneToMany(mappedBy="curso")
	    List<Grados_curso> grado_cursoList;

		

		public Curso(Long id, @NotBlank @Size(max = 75) String nombrec,
				@NotBlank @Range(min = 0, max = 10) Integer creditos) {
			this.id = id;
			this.nombrec = nombrec;
			this.creditos = creditos;
		}

		
		public Curso(@NotBlank @Size(max = 75) String nombrec, @NotBlank @Range(min = 0, max = 10) Integer creditos) {
	
			this.nombrec = nombrec;
			this.creditos = creditos;
		}

		public Curso() {
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNombrec() {
			return nombrec;
		}

		public void setNombrec(String nombrec) {
			this.nombrec = nombrec;
		}

		public Integer getCreditos() {
			return creditos;
		}

		public void setCreditos(Integer creditos) {
			this.creditos = creditos;
		}

		@Override
		public String toString() {
			return "Curso [id=" + id + ", nombrec=" + nombrec + ", creditos=" + creditos + "]";
		}
	    
	    
	    
	   
}
