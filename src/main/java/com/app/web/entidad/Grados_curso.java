package com.app.web.entidad;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="grados_cursos")
public class Grados_curso {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
	  
	    @ManyToOne
	    @JoinColumn(name="grados_id",nullable = false)
	    @NotNull
	    private  Grado grado;	
	    
	    @ManyToOne
	    @JoinColumn(name="cursos_id",nullable = false)
	    @NotNull
	    private  Curso curso;

		public Grados_curso(Long id, @NotNull Grado grado, @NotNull Curso curso) {
			this.id = id;
			this.grado = grado;
			this.curso = curso;
		}

		public Grados_curso() {
		}

		public Grados_curso(@NotNull Grado grado, @NotNull Curso curso) {
			this.grado = grado;
			this.curso = curso;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Grado getGrado() {
			return grado;
		}

		public void setGrado(Grado grado) {
			this.grado = grado;
		}

		public Curso getCurso() {
			return curso;
		}

		public void setCurso(Curso curso) {
			this.curso = curso;
		}

		@Override
		public String toString() {
			return "Grados_curso [id=" + id + ", grado=" + grado + ", curso=" + curso + "]";
		}
	    
	    
	    
}
