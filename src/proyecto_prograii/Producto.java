package proyecto_prograii;

public class Producto {
private String id;
private String nombre;
private Double costo;
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
public Double getCosto() {
	return costo;
}
public void setCosto(Double costo) {
	this.costo = costo;
}
/**
 * @param id
 * @param nombre
 * @param costo
 */
public Producto(String id, String nombre, Double costo) {
	super();
	this.id = id;
	this.nombre = nombre;
	this.costo = costo;
}
@Override
public String toString() {
	return "Producto [id=" + id + ", nombre=" + nombre + ", costo=" + costo + "]";
}

}
