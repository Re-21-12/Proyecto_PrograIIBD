package proyecto_prograii;

import java.util.*;

public interface Interfaz_Generar_Factura {
	public abstract Factura Generar_factura(String id, Double venta, String Nombre_institucion,
			ArrayList<Producto> producto);
}
