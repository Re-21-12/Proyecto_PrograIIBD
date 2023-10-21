package proyecto_prograii;

import java.time.LocalDateTime;
import java.util.*;

public class Entidad implements Interfaz_Generar_Factura {
	private String id;
	private String nombre;
	private String numero_telefono;
	private String Direccion;
	private String Correo;

	public Factura Generar_factura(String id, Double venta, String Nombre_entidad, ArrayList<Producto> productos) {
		LocalDateTime hoy = LocalDateTime.now();
		Factura factura = new Factura(id, hoy, venta, Nombre_entidad, productos);
		return factura;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumero_telefono() {
		return numero_telefono;
	}

	public void setNumero_telefono(String numero_telefono) {
		this.numero_telefono = numero_telefono;
	}

	public String getDireccion() {
		return Direccion;
	}

	public void setDireccion(String direccion) {
		Direccion = direccion;
	}

	public String getCorreo() {
		return Correo;
	}

	public void setCorreo(String correo) {
		Correo = correo;
	}

	/**
	 * @param id
	 * @param nombre
	 * @param numero_telefono
	 * @param direccion
	 * @param correo
	 */
	public Entidad(String id, String nombre, String numero_telefono, String direccion, String correo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.numero_telefono = numero_telefono;
		Direccion = direccion;
		Correo = correo;
	}

	@Override
	public String toString() {
		return "Institucion [id=" + id + ", nombre=" + nombre + ", numero_telefono=" + numero_telefono + ", Direccion="
				+ Direccion + ", Correo=" + Correo + "]";
	}

}
