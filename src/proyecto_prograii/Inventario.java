package proyecto_prograii;
import java.util.*;
public class Inventario {
private ArrayList<Silla> sillas;
private ArrayList<Material> materiales;
private ArrayList<Proveedor> proveedores;
private ArrayList<Distribuidor> distribuidores;
private ArrayList<Cliente> clientes;
/**
 * @param sillas
 * @param materiales
 * @param proveedores
 * @param distribuidores
 * @param clientes
 */
public Inventario(ArrayList<Silla> sillas, ArrayList<Material> materiales, ArrayList<Proveedor> proveedores,
		ArrayList<Distribuidor> distribuidores, ArrayList<Cliente> clientes) {
	super();
	this.sillas = sillas;
	this.materiales = materiales;
	this.proveedores = proveedores;
	this.distribuidores = distribuidores;
	this.clientes = clientes;
}
@Override
public String toString() {
	return "Inventario [sillas=" + sillas + ", materiales=" + materiales + ", proveedores=" + proveedores
			+ ", distribuidores=" + distribuidores + ", clientes=" + clientes + "]";
}
public ArrayList<Silla> getSillas() {
	return sillas;
}
public void setSillas(ArrayList<Silla> sillas) {
	this.sillas = sillas;
}
public ArrayList<Material> getMateriales() {
	return materiales;
}
public void setMateriales(ArrayList<Material> materiales) {
	this.materiales = materiales;
}
public ArrayList<Proveedor> getProveedores() {
	return proveedores;
}
public void setProveedores(ArrayList<Proveedor> proveedores) {
	this.proveedores = proveedores;
}
public ArrayList<Distribuidor> getDistribuidores() {
	return distribuidores;
}
public void setDistribuidores(ArrayList<Distribuidor> distribuidores) {
	this.distribuidores = distribuidores;
}
public ArrayList<Cliente> getClientes() {
	return clientes;
}
public void setClientes(ArrayList<Cliente> clientes) {
	this.clientes = clientes;
}

}
