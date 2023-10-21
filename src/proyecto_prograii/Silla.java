package proyecto_prograii;

import java.util.*;
public class Silla extends Producto{
/*
	 * si una persona en un dia hace mano de obra Q.3,323.60 + Q250 = 119.12
	 * operador de maquina diario 357.36 Q por 3 dias pero en ese dia hace 10 sillas
	 * 
	 * precion de venta-> costo de la silla *
	 * 
	 * la silla se va a armar despues de ver el inventairo clase facturacion
	 * 
	 * mano de obra / total de sillas + iva = facturacion Por ejemplo: 0.25kg ->
	 * Q27,06 = (cantidad_pegamento en kg * precio)/0.25kg -> 12.96 0.1250 * 27.00 =
	 * 3.3825Q 0.9463kg -> Q75,00 = (cantidad_barniz en kg * precio)/0.9463kg = 39
	 * Quetzales por 1/4 de barniz 3 pedazos de madera -> Q100 necesitamos 10 clavos
	 * de una bolsa de 40 de 0.454clavos -> 2.25 Q 2 lijas 10Q
	 */
	// el precio de venta es : costo despues de cortar materiales + mano de obra =
	// total *
	private Double precio_venta ; //"195.90" <- precio de venta 
	private ArrayList<Material> materiales;
	private Estado estado;
	public Double getPrecio_venta() {
		return precio_venta;
	}
	public void setPrecio_venta(Double precio_venta) {
		this.precio_venta = precio_venta;
	}
	public ArrayList<Material> getMateriales() {
		return materiales;
	}
	public void setMateriales(ArrayList<Material> materiales) {
		this.materiales = materiales;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	/**
	 * @param id
	 * @param nombre
	 * @param costo
	 * @param precio_venta
	 * @param materiales
	 * @param estado
	 */
	public Silla(String id, String nombre, Double costo, Double precio_venta, ArrayList<Material> materiales,
			Estado estado) {
		super(id, nombre, costo);
		this.precio_venta = precio_venta;
		this.materiales = materiales;
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "Silla [precio_venta=" + precio_venta + ", materiales=" + materiales + ", estado=" + estado + "]";
	}

}
