package proyecto_prograii;

public class Cliente {
private String id;
private String nombre;
private String numero_telefono;
private Factura factura;
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
public Factura getFactura() {
	return factura;
}
public void setFactura(Factura factura) {
	this.factura = factura;
}
/**
 * @param id
 * @param nombre
 * @param numero_telefono
 * @param factura
 */
public Cliente(String id, String nombre, String numero_telefono, Factura factura) {
	super();
	this.id = id;
	this.nombre = nombre;
	this.numero_telefono = numero_telefono;
	this.factura = factura;
}
@Override
public String toString() {
	return "Cliente [id=" + id + ", nombre=" + nombre + ", numero_telefono=" + numero_telefono + ", factura=" + factura
			+ "]";
}

}
