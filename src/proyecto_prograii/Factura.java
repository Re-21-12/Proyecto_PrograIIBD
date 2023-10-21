package proyecto_prograii;

import java.time.*;
import java.util.*;

public class Factura {
	private String id;
	private LocalDateTime fecha;
	private Double venta;
	private String nombre_institucion;
	private ArrayList<Producto> productos;

	/**
	 * @param id
	 * @param fecha
	 * @param venta
	 * @param nombre_institucion
	 * @param productos
	 */
	public Factura(String id, LocalDateTime fecha, Double venta, String nombre_institucion,
			ArrayList<Producto> productos) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.venta = venta;
		this.nombre_institucion = nombre_institucion;
		this.productos = productos;
	}

	public Factura() {

	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", fecha=" + fecha + ", venta=" + venta + ", nombre_institucion="
				+ nombre_institucion + ", productos=" + productos + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Double getVenta() {
		return venta;
	}

	public void setVenta(Double venta) {
		this.venta = venta;
	}

	public String getNombre_institucion() {
		return nombre_institucion;
	}

	public void setNombre_institucion(String nombre_institucion) {
		this.nombre_institucion = nombre_institucion;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

}
