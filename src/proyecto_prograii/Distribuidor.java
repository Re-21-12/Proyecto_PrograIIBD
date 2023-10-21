package proyecto_prograii;
import java.util.*;

public class Distribuidor extends Entidad{
	private ArrayList<Silla> productos_distribuidos;
	private String informacion_entrega;
	public ArrayList<Silla> getProductos_distribuidos() {
		return productos_distribuidos;
	}
	public void setProductos_distribuidos(ArrayList<Silla> productos_distribuidos) {
		this.productos_distribuidos = productos_distribuidos;
	}
	public String getInformacion_entrega() {
		return informacion_entrega;
	}
	public void setInformacion_entrega(String informacion_entrega) {
		this.informacion_entrega = informacion_entrega;
	}
	/**
	 * @param id
	 * @param nombre
	 * @param numero_telefono
	 * @param direccion
	 * @param correo
	 * @param productos_distribuidos
	 * @param informacion_entrega
	 */
	public Distribuidor(String id, String nombre, String numero_telefono, String direccion, String correo,
			ArrayList<Silla> productos_distribuidos, String informacion_entrega) {
		super(id, nombre, numero_telefono, direccion, correo);
		this.productos_distribuidos = productos_distribuidos;
		this.informacion_entrega = informacion_entrega;
	}
	@Override
	public String toString() {
		return "Distribuidor [productos_distribuidos=" + productos_distribuidos + ", informacion_entrega="
				+ informacion_entrega + "]";
	}
		
}
