package proyecto_prograii;

import java.util.*;
public class Control_Fabrica_de_sillas {
private Inventario inventario;
private Procesos_Fabrica_de_Sillas procesos_fabrica_sillas;
private ArrayList<Distribuidor> distribuidores;
private ArrayList<Proveedor> proveedores;
private ArrayList<Cliente> clientes;
public Inventario getInventario() {
	return inventario;
}
public void setInventario(Inventario inventario) {
	this.inventario = inventario;
}
public Procesos_Fabrica_de_Sillas getProcesos_fabrica_sillas() {
	return procesos_fabrica_sillas;
}
public void setProcesos_fabrica_sillas(Procesos_Fabrica_de_Sillas procesos_fabrica_sillas) {
	this.procesos_fabrica_sillas = procesos_fabrica_sillas;
}
public ArrayList<Distribuidor> getDistribuidores() {
	return distribuidores;
}
public void setDistribuidores(ArrayList<Distribuidor> distribuidores) {
	this.distribuidores = distribuidores;
}
public ArrayList<Proveedor> getProveedores() {
	return proveedores;
}
public void setProveedores(ArrayList<Proveedor> proveedores) {
	this.proveedores = proveedores;
}
public ArrayList<Cliente> getClientes() {
	return clientes;
}
public void setClientes(ArrayList<Cliente> clientes) {
	this.clientes = clientes;
}
/**
 * @param inventario
 * @param procesos_fabrica_sillas
 * @param distribuidores
 * @param proveedores
 * @param clientes
 */
public Control_Fabrica_de_sillas(Inventario inventario, Procesos_Fabrica_de_Sillas procesos_fabrica_sillas,
		ArrayList<Distribuidor> distribuidores, ArrayList<Proveedor> proveedores, ArrayList<Cliente> clientes) {
	super();
	this.inventario = inventario;
	this.procesos_fabrica_sillas = procesos_fabrica_sillas;
	this.distribuidores = distribuidores;
	this.proveedores = proveedores;
	this.clientes = clientes;
}
@Override
public String toString() {
	return "Control_Fabrica_de_sillas [inventario=" + inventario + ", procesos_fabrica_sillas="
			+ procesos_fabrica_sillas + ", distribuidores=" + distribuidores + ", proveedores=" + proveedores
			+ ", clientes=" + clientes + "]";
}



}
