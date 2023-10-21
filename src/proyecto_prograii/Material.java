package proyecto_prograii;

public class Material extends Producto{
//se usaran tipados en string que posteriormente seran convertidos para realizar las distintias operaciones
	//cuanto quiero de cada cosa, establecer precios:
	//double
	private Double cantidad;

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @param id
	 * @param nombre
	 * @param costo
	 * @param cantidad
	 */
	public Material(String id, String nombre, Double costo, Double cantidad) {
		super(id, nombre, costo);
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "Material [cantidad=" + cantidad + "]";
	}



	

}
