package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the proposal database table.
 * 
 */
@Entity
@Table(name="proposal")
public class ProposalDb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String email;

	private float max;

	private float min;

	private String nombre;

	private String organizacion;

	private String telefono;

	public ProposalDb() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public float getMax() {
		return this.max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getMin() {
		return this.min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getOrganizacion() {
		return this.organizacion;
	}

	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}