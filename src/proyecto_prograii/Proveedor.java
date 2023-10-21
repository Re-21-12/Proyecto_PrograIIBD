package proyecto_prograii;
import java.util.*;
public class Proveedor extends Entidad {
private ArrayList<Material> productos_proveidos;
private String informacion_compra;
public ArrayList<Material> getProductos_proveidos() {
	return productos_proveidos;
}
public void setProductos_proveidos(ArrayList<Material> productos_proveidos) {
	this.productos_proveidos = productos_proveidos;
}
public String getInformacion_compra() {
	return informacion_compra;
}
public void setInformacion_compra(String informacion_compra) {
	this.informacion_compra = informacion_compra;
}
/**
 * @param id
 * @param nombre
 * @param numero_telefono
 * @param direccion
 * @param correo
 * @param productos_proveidos
 * @param informacion_compra
 */
public Proveedor(String id, String nombre, String numero_telefono, String direccion, String correo,
		ArrayList<Material> productos_proveidos, String informacion_compra) {
	super(id, nombre, numero_telefono, direccion, correo);
	this.productos_proveidos = productos_proveidos;
	this.informacion_compra = informacion_compra;
}
@Override
public String toString() {
	return "Proveedor [productos_proveidos=" + productos_proveidos + ", informacion_compra=" + informacion_compra + "]";
}


}
