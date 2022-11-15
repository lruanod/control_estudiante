package com.app.web.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name="estudiantes")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @NotBlank
    @Column(name="nombre",nullable = false, length=75)
    private String nombre;
    
    @NotBlank
    @Size(max =75)
    @Column(name="apellido",nullable = false, length=75)
    private String apellido;
    
    @Email
    @NotBlank
    @Column(name="email",nullable = false, length=75, unique=true)
    private String email;
	
  
    @ManyToOne
    @JoinColumn(name="grados_id",nullable = false)
    @NotNull
    private  Grado grado;
    
    
    public Estudiante() {
    	
    }
    
    


	
	public Estudiante(@NotBlank String nombre, @NotBlank @Size(max = 75) String apellido, @Email @NotBlank String email,
			@NotNull Grado grado) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.grado = grado;
	}





	public Estudiante(Long id, @NotBlank String nombre, @NotBlank @Size(max = 75) String apellido,
			@Email @NotBlank String email, @NotNull Grado grado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.grado = grado;
	}

	public Estudiante(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Grado getGrado() {
		return grado;
	}

	public void setGrado(Grado grado) {
		this.grado = grado;
	}

	@Override
	public String toString() {
		return "Estudiante [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email
				+ ", grado=" + grado + "]";
	}

    
}
