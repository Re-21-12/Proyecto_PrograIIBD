package proyecto_prograii;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.time.*;

import java.io.*;
import java.time.LocalDateTime;
//import implementacion_interfaces.Producto;

public class Procesos_Fabrica_de_Sillas implements Interfaz_Procesos_Fabrica_de_Sillas {
	String url = "jdbc:mariadb://127.0.0.1:3304/fabrica_de_sillas";
	String user = "root";
	String password = "Alfredo+123";
	Connection conexion = null;

	PreparedStatement statement = null;
	PreparedStatement statement1 = null;
	ArrayList<Silla> sillas = new ArrayList<Silla>();
	ArrayList<Material> materiales = new ArrayList<Material>();
	ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
	ArrayList<Distribuidor> distribuidores = new ArrayList<Distribuidor>();
	ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	Inventario inventario = new Inventario(sillas, materiales, proveedores, distribuidores, clientes);

	public void revisarInventario() throws SQLException {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conexion = DriverManager.getConnection(url, user, password);
			String selectQuerySilla = "SELECT * FROM silla";

			statement = conexion.prepareStatement(selectQuerySilla);

			ResultSet resultados = statement.executeQuery();
			System.out.println("+------------------Silla------------------------+");
			System.out.println("|\t Id\tTipo\tCosto\tPrecio\tEstado\t|");

			while (resultados.next()) {
				int idSilla = resultados.getInt("id_silla");
				double res_costo = resultados.getDouble("costo");
				double precioVenta = resultados.getDouble("precio_venta");
				String estado = resultados.getString("estado");
				String nombre = resultados.getString("nombre");

				Silla silla = new Silla(String.valueOf(idSilla), nombre, res_costo, precioVenta, estado);
				sillas.add(silla);
			}
			inventario.setSillas(sillas);
			for (Silla ver_silla : inventario.getSillas()) {
				System.out.println(ver_silla);
			}

			System.out.println("+-----------------------------------------------+");
			// System.out.println("Total de materiales: " + total);

			System.out.println("\tCantidad de sillas: " + "[" + sillas.size() + "]");
			revisarMaterial();
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e);
		} finally {
			// Cerrar recursos (statement y conexión)
			if (statement != null) {
				statement.close();
			}
			if (conexion != null) {
				conexion.close();
			}
		}
	}

	public void realizarCompraMaterial() throws SQLException {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			ArrayList<Producto> productos_factura = new ArrayList<Producto>();
			ArrayList<Material> carga_material = new ArrayList<Material>();
			conexion = DriverManager.getConnection(url, user, password);

			revisarMaterial();
			Scanner entrada = new Scanner(System.in);
			Double precio = 0.0;
			/*
			 * if (inventario.getSillas().size() == 0) {
			 * System.out.println(" necesita armar sillas"); }
			 */
			if (inventario.getMateriales().size() == 0) {
				System.out.println("Necesita comprar materiales");
			}

			System.out.println("Desea comprar materiales?: [s] | [n]");
			String opcion = entrada.nextLine();
			System.out.println("Ingrese el nombre del proveedor: ");
			String nombre_proveedor = entrada.nextLine();
			System.out.println("Ingrese el numero de telefono del proveedor: ");
			String numero_proveedor = entrada.nextLine();
			System.out.println("Ingrese la direccion del proveedor: ");
			String direccion_proveedor = entrada.nextLine();
			System.out.println("Ingrese el correo del proveedor: ");
			String correo_proveedor = entrada.nextLine();
			String id_proveedor = String.valueOf(inventario.getProveedores().size());

			do {
				System.out.println("+-----------------------------------+");
				System.out.println("|Requisitos minimos para una silla  |");
				System.out.println("+-----------------------------------+");
				System.out.println("|  nombre  | 	cantidad        |");
				System.out.println("|madera    | 3u                     |");
				System.out.println("|clavos    | 10u                    |");
				System.out.println("|lijas     | 2u                     |");
				System.out.println("|barniz    | 0.9463kg               |");
				System.out.println("|cola      | 0.1250kg               |");
				System.out.println("+-----------------------------------+");
				System.out.println("Ingrese el nombre del material: ");
				String nombre = entrada.nextLine();
				System.out.println("Ingrese el costo del material: ");
				Double costo = entrada.nextDouble();
				System.out.println("Ingrese la cantidad de material: ");
				Double cantidad = entrada.nextDouble();
				Material material = new Material(generarId(), nombre, costo, cantidad, "compra");
				carga_material.add(material);
				for (Material mostrar_material : carga_material) {
					precio += (mostrar_material.getCosto() * mostrar_material.getCantidad());
					cantidad += mostrar_material.getCantidad();
				}
				entrada.nextLine();
				System.out.println("Desea comprar mas materiales del mismo proveedor?: [s] | [n]");
				opcion = entrada.nextLine();
				// materiales.add(material); // Agrega el material a la lista materiales
			} while (!opcion.equals("n"));

			inventario.setMateriales(carga_material);
			// retorna las claves generadas depsues de la consulta
			String insertQueryMaterial = "INSERT INTO material (nombre, costo, cantidad, estado) VALUES ( ?, ?, ?, ?)";
			statement = conexion.prepareStatement(insertQueryMaterial, Statement.RETURN_GENERATED_KEYS);
			// , Statement.RETURN_GENERATED_KEYS
			// recorro carga material para subirla a la BD
			int rowsInsertedMaterial = 0;
			for (Material materialbd : inventario.getMateriales()) {
				precio += materialbd.getCosto();
				statement.setString(1, materialbd.getNombre());
				statement.setDouble(2, materialbd.getCosto());
				statement.setDouble(3, materialbd.getCantidad());
				statement.setString(4, materialbd.getEstado());
				rowsInsertedMaterial = statement.executeUpdate();
				if (rowsInsertedMaterial != 0) {
					ResultSet IdsGeneradas = statement.getGeneratedKeys();
					// recorro carga material como almacenamiento temporal para posteriormente
					// asignar las ids generadas en la BD
					if (IdsGeneradas.next()) {
						for (Material corregirIds : inventario.getMateriales()) {
							String IdBD = IdsGeneradas.getString(1);
							corregirIds.setId(IdBD);

						}
					}
				}
			}

			System.out.println(rowsInsertedMaterial + " filas insertadas con exito");
			// ward

			/*
			 * System.out.println(
			 * "+------Id------Tipo------------Precio[u]-------Cantidad-+");
			 * 
			 * System.out.println(
			 * "+-------------------------------------------------------+");
			 */
			Proveedor proveedor = new Proveedor(id_proveedor, nombre_proveedor, numero_proveedor, direccion_proveedor,
					correo_proveedor, carga_material);
			String insertQueryProveedor = "INSERT INTO proveedor (id_material, nombre, direccion, telefono, correo_electronico) VALUES ( ?,?, ?, ?, ?)";
			statement = conexion.prepareStatement(insertQueryProveedor);
			for (Material materialid : carga_material) {
				statement.setString(1, materialid.getId());
				Producto producto = new Producto(materialid.getId(), materialid.getNombre(), materialid.getCantidad());
				productos_factura.add(producto);
			}

			statement.setString(2, nombre_proveedor);
			statement.setString(3, direccion_proveedor);
			statement.setString(4, numero_proveedor);
			statement.setString(5, correo_proveedor);

			int rowsInsertedProveedor = statement.executeUpdate();
			proveedores.add(proveedor);
			for (Proveedor revision_proveedor : inventario.getProveedores()) {
				System.out.println("Se agrego el proveedor: " + revision_proveedor.getNombre());
			}
			String insertQueryFactura = "INSERT INTO factura (id_Factura,fecha , precio_venta, institucion) VALUES ( ?,?, ?, ?)";
			statement = conexion.prepareStatement(insertQueryFactura);
			Factura factura = new Factura();
			factura = proveedor.Generar_factura(generarId(), precio, nombre_proveedor, productos_factura);
			Timestamp fecha = Timestamp.valueOf(factura.getFecha());
			// Establecer los valores de los parámetros
			statement.setInt(1, Integer.parseInt(factura.getId()));
			statement.setTimestamp(2, fecha); // Cambiado de setDate a setTimestamp
			statement.setDouble(3, factura.getVenta());
			statement.setString(4, factura.getNombre_institucion());
			System.out.println(factura.toString());
			int rowsInsertedFactura = statement.executeUpdate();
			inventario.setMateriales(materiales); // Agrega el inventario.setMateriales(materiales) aquí

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		} finally {
			// Cerrar recursos (statement y conexión)
			if (statement != null) {
				statement.close();
			}
			if (conexion != null) {
				conexion.close();
			}
		}
	}

	private void revisarMaterial() throws SQLException {
		ArrayList<Material> material = new ArrayList<Material>();
		try {

			// Establecer la conexión con la base de datos
			conexion = DriverManager.getConnection(url, user, password);
			String selectQuery = "SELECT * FROM material WHERE estado = 'compra'";
			statement = conexion.prepareStatement(selectQuery);
			ResultSet resultados = statement.executeQuery();
			System.out.println("+---------------------Material------------------------------------------+");
			System.out.println("|\tId \tTipo \t\tPrecio[Q.] \tCantidad \tEsttado\t|");
			System.out.println("+-----------------------------------------------------------------------+");
			
			Class.forName("org.mariadb.jdbc.Driver");
			statement = conexion.prepareStatement(selectQuery);
			while (resultados.next()) {
				int id_Material = resultados.getInt("id_material");
				String nombre_material = resultados.getString("nombre");
				Double costo_material = resultados.getDouble("costo");
				Double cantidad = resultados.getDouble("cantidad");
				String estado = resultados.getString("estado");
				Material rev_material = new Material(String.valueOf(id_Material), nombre_material, costo_material,
						cantidad, estado);

				// si lo aniade yy vuelvo a usar la opcion repite materiales
				material.add(rev_material);
			}
//actualizo el inventario segun lo que tenga
			// creamos una copia para evitar la copia de datos
			ArrayList<Material> copia_materiales = new ArrayList<Material>(material);
			// actualizamos inventario
			inventario.setMateriales(copia_materiales);
			// limpio par evitar repeticion
			material.clear();

			for (Material ver_material : inventario.getMateriales()) {
				System.out.println(ver_material);
			}

			System.out.println("+-----------------------------------------------------------------------+");
			System.out.println("\tCantidad de materiales: " + "[" + inventario.getMateriales().size() + "]");
			System.out.println("+-----------------------------------------------------------------------+");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void revisarMaterialCortado() throws SQLException {
		ArrayList<Material> material = new ArrayList<Material>();
		try {

			Class.forName("org.mariadb.jdbc.Driver");
			// Establecer la conexión con la base de datos
			conexion = DriverManager.getConnection(url, user, password);
			String selectQuery = "SELECT * FROM material WHERE estado = 'corte'";
			statement = conexion.prepareStatement(selectQuery);
			ResultSet resultados = statement.executeQuery();
			System.out.println("+---------------------Material--------------------------+");
			System.out.println("|\tId \tTipo \t\tPrecio[Q.] \tCantidad|");

			statement = conexion.prepareStatement(selectQuery);
			while (resultados.next()) {
				int id_Material = resultados.getInt("id_material");
				String nombre_material = resultados.getString("nombre");
				Double costo_material = resultados.getDouble("costo");
				Double cantidad = resultados.getDouble("cantidad");
				String estado = resultados.getString("estado");
				Material rev_material = new Material(String.valueOf(id_Material), nombre_material, costo_material,
						cantidad, estado);

				// si lo aniade yy vuelvo a usar la opcion repite materiales
				material.add(rev_material);
			}
//actualizo el inventario segun lo que tenga
			// creamos una copia para evitar la copia de datos
			ArrayList<Material> copia_materiales = new ArrayList<Material>(material);
			// actualizamos inventario
			inventario.setMateriales(copia_materiales);
			// limpio par evitar repeticion
			material.clear();

			for (Material ver_material : inventario.getMateriales()) {
				System.out.println(ver_material);
			}

			System.out.println("+-------------------------------------------------------+");
			System.out.println("\tCantidad de materiales: " + "[" + inventario.getMateriales().size() + "]");
			System.out.println("+-------------------------------------------------------+");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void corteDeMateriales() throws SQLException {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			revisarMaterial();
			if (inventario.getMateriales().size() == 0) {
				System.out.println("El inventario debe contener materiales");
				return;
			}

			// una silla va a necesitar: 10 clavos, 3 pedazos de madera, 0.1250 de
			// pegamento, 0.9463 de barniz y 2 lijas
			// recupera la informacion del objeto inventario
			ArrayList<Material> revisar_materiales = inventario.getMateriales();
			// crea un array para almacenar el material que se va a cortar
			ArrayList<Material> material_cortado = new ArrayList<Material>();

			int madera = 0;
			Double madera_costo = 0.0;
			int clavos = 0;
			Double clavo_costo = 0.0;
			int lijas = 0;
			Double lija_costo = 0.0;
			Double barniz = 0.0;
			Double barniz_costo = 0.0;
			Double pegamento = 0.0;
			Double pegamento_costo = 0.0;
			// itera para saber que materiales hay
			for (Material material_encontrado : revisar_materiales) {

				// Buscamos si el material ya está en la lista de conteo_material
				// lo vuelvo minusculas para obviar las mayusculas
				// segun el nombre realiza un filtro como requisitos minimos
				switch (material_encontrado.getNombre().toLowerCase()) {
				case "madera":
					// obtiene la cantidad
					madera += material_encontrado.getCantidad();
					// si hay varios del mismo nombre los suma para obtener un solo objeto
					material_encontrado
							.setCantidad(material_encontrado.getCantidad() + material_encontrado.getCantidad());
					madera_costo = material_encontrado.getCosto() / madera;
					break;
				case "clavos":
					clavos += material_encontrado.getCantidad();
					material_encontrado
							.setCantidad(material_encontrado.getCantidad() + material_encontrado.getCantidad());
					clavo_costo = material_encontrado.getCosto() / clavos;
					break;
				case "barniz":
					barniz += material_encontrado.getCantidad();
					material_encontrado
							.setCantidad(material_encontrado.getCantidad() + material_encontrado.getCantidad());
					barniz_costo = material_encontrado.getCosto() / barniz;
					break;
				case "cola":
					pegamento += material_encontrado.getCantidad();
					material_encontrado
							.setCantidad(material_encontrado.getCantidad() + material_encontrado.getCantidad());
					pegamento_costo = material_encontrado.getCosto() / pegamento;
					break;
				case "lijas":
					lijas += material_encontrado.getCantidad();
					material_encontrado
							.setCantidad(material_encontrado.getCantidad() + material_encontrado.getCantidad());
					lija_costo = material_encontrado.getCosto() / lijas;
					break;

				default:
					// es para agregar un material extra
					Material nuevoMaterial = new Material(material_encontrado.getId(), material_encontrado.getNombre(),
							material_encontrado.getCosto(), material_encontrado.getCantidad(),
							material_encontrado.getEstado());
					// aniade el material que no pertenece a los requisitos minimos
					material_cortado.add(nuevoMaterial);
					break;
				}
			}
			// si no se cumple con los requisitos minimos
			if (madera <= 3 || clavos <= 10 || lijas <= 2 || barniz <= 0.9463 || pegamento <= 0.1250) {
				System.out.println("Debe contener mas materiales para realizar el proceso de corte");
			}
//16.0713	//en base a la cantidad minima divide para obtener varios
			// si hay 6
			madera = madera / 3; // 2
			// si hay 20
			clavos = clavos / 10;// 2
			// si hay 4
			lijas = lijas / 2;// 2
			// si hay .deccimal mas que .94
			int barniz_redondeado = (int) (barniz / 0.9463); // 2
			// si hay .decimal mas .25
			int pegamento_redondeado = (int) (pegamento / 0.1250); // 2
			// de solo haber 1 en cada uno la suma seria justamente 5

			// si la suma de lo anterior me da mas de 5 entonces puedo crear mas de una
			// silla
			int cantidad_material_silla = madera + clavos + lijas + barniz_redondeado + pegamento_redondeado;
			// la suma de los materiales aproximadamente es de 5 entonces lo divido entre 5
			System.out.println("cantidad material: " + cantidad_material_silla);

			int cantidad_sillas = cantidad_material_silla / 5;
			// mostramos la cantidad de sillas que se pueuden construir
			System.out.println("Se pueden elaborar: " + cantidad_sillas + " sillas ");
			// empezamos el contador en 1 para que al menos se pueda elaboar 1 silla ?
			int contador = 0;
			while (contador < cantidad_sillas) {
				contador++;
				// alteramos el costo a precio de unidad para posteriormente usarlo para generar
				// el precioo de venta
				String mid = generarId();
				String cid = generarId();
				String lid = generarId();
				String pid = generarId();
				String bid = generarId();

				Material madera_cortada = new Material(mid, "madera", madera_costo, 3.0, "corte");
				Material clavo_cortado = new Material(cid, "clavo", clavo_costo, 10.0, "corte");
				Material lija_cortada = new Material(lid, "lija", lija_costo, 2.0, "corte");
				Material pegamento_cortado = new Material(pid, "cola", pegamento_costo, 0.1250, "corte");
				Material barniz_cortado = new Material(bid, "barniz", barniz_costo, 0.9463, "corte");

				material_cortado.add(madera_cortada);
				material_cortado.add(clavo_cortado);
				material_cortado.add(lija_cortada);
				material_cortado.add(pegamento_cortado);
				material_cortado.add(barniz_cortado);

				madera -= 3;
				clavos -= 10;
				lijas -= 2;
				barniz -= 0.9463;
				pegamento -= 0.1250;

			}
			;
			inventario.setMateriales(material_cortado);
			// ward
			conexion = DriverManager.getConnection(url, user, password);
			String insertQueryMaterial = "INSERT INTO material (nombre, costo, cantidad, estado) VALUES ( ?, ?, ?, ?)";
			statement = conexion.prepareStatement(insertQueryMaterial, Statement.RETURN_GENERATED_KEYS);
			// , Statement.RETURN_GENERATED_KEYS
			// recorro carga material para subirla a la BD
			int rowsInsertedMaterial = 0;
			for (Material materialbd : inventario.getMateriales()) {
				statement.setString(1, materialbd.getNombre());
				statement.setDouble(2, materialbd.getCosto());
				statement.setDouble(3, materialbd.getCantidad());
				statement.setString(4, materialbd.getEstado());
				rowsInsertedMaterial = statement.executeUpdate();
				if (rowsInsertedMaterial != 0) {
					ResultSet IdsGeneradas = statement.getGeneratedKeys();
					// recorro carga material como almacenamiento temporal para posteriormente
					// asignar las ids generadas en la BD
					if (IdsGeneradas.next()) {
						for (Material corregirIds : inventario.getMateriales()) {
							String IdBD = IdsGeneradas.getString(1);
							corregirIds.setId(IdBD);
						}
					}
				}
			}
			System.out.println(rowsInsertedMaterial + " filas insertadas con exito");
			// ward

			String updateQuery = "UPDATE material SET estado = ? WHERE id_material = ?";
			statement = conexion.prepareStatement(updateQuery);
			int rowsUpdatedEstadoMaterial = 0;
			for (Material materialEstado : revisar_materiales) {
				statement.setString(1, "usado");
				statement.setInt(2, Integer.parseInt(materialEstado.getId()));
				rowsUpdatedEstadoMaterial = statement.executeUpdate();
			}
			if (rowsUpdatedEstadoMaterial != 0) {
				System.out.println(rowsUpdatedEstadoMaterial + " filas insertadas con exito");
				// asignar las ids generadas en la BD
			}

			// Si no existe en la lista de conteo_material, lo agregamos

			// Ahora tenemos una lista con materiales únicos y su cantidad

			System.out.println("+-------------------------------------------------------+");
			System.out.println("Lista de materiales cortados:");
			System.out.println("+-------------------------------------------------------+");
			revisarMaterialCortado();
			// eliminar comprados btw

		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e);
		} finally {
			// Cerrar recursos (statement y conexión)
			if (statement != null) {
				statement.close();
			}
			if (conexion != null) {
				conexion.close();
			}
		}
	}

	public void ensamblaje() throws SQLException {
		try {
			ArrayList<Material> materiales_cortados = new ArrayList<Material>();
			Class.forName("org.mariadb.jdbc.Driver");
			conexion = DriverManager.getConnection(url, user, password);
			int contador = 0;
			Double costo = 0.0;
			Double venta = 0.0;
			Scanner entrada = new Scanner(System.in);
			String selectQuery = "SELECT * FROM material WHERE estado = 'corte'";
			statement = conexion.prepareStatement(selectQuery);
			ResultSet resultados = statement.executeQuery();

			statement = conexion.prepareStatement(selectQuery);
			while (resultados.next()) {
				int id_Material = resultados.getInt("id_material");
				String nombre_material = resultados.getString("nombre");
				Double costo_material = resultados.getDouble("costo");
				Double cantidad = resultados.getDouble("cantidad");
				String estado = resultados.getString("estado");
				Material rev_material = new Material(String.valueOf(id_Material), nombre_material, costo_material,
						cantidad, estado);

				// si lo aniade yy vuelvo a usar la opcion repite materiales
				materiales_cortados.add(rev_material);
			}
			inventario.setMateriales(materiales_cortados);
			if (inventario.getMateriales().size() < 5) {
				System.out.println("El inventario no contiene los materiales suficientes");
			}

			System.out.println("+-------------------------------------------------------+");
			System.out.println("Puede armar : " + inventario.getMateriales().size() / 5 + " Sillas");
			System.out.println("+-------------------------------------------------------+");
			System.out.println("Ingrese la cantidad de sillas a ensamblar: ");
			System.out.println("+-------------------------------------------------------+");

			int cantidad = entrada.nextInt();
			ArrayList<Material> materialesUsados = new ArrayList<>();
			ArrayList<Silla> sillasEnsambladas = new ArrayList<>();

			do {
				for (int i = 0; i < 5; i++) {
					Material material = inventario.getMateriales().remove(0); // Remover el primer material
					materialesUsados.add(material);
					costo += material.getCosto();
				}
				String updateQuery = "UPDATE material SET estado = ? WHERE id_material = ?";
				statement = conexion.prepareStatement(updateQuery);
				int rowsUpdatedEstadoMaterial = 0;
				for (Material materialEstado : materialesUsados) {
					statement.setString(1, "ensamblado");
					statement.setInt(2, Integer.parseInt(materialEstado.getId()));
					rowsUpdatedEstadoMaterial = statement.executeUpdate();
				}

				contador++;
				// + ((costo * 0.5) * 0.12)
				venta = (costo * 0.5);
				Silla silla = new Silla(generarId(), "silla", costo, venta, materialesUsados, "Ensamblada");
				sillasEnsambladas.add(silla);
				inventario.setSillas(sillasEnsambladas);
			} while (contador < cantidad);
			inventario.setMateriales(materialesUsados);

			System.out.println("+-------------------------------------------------------+");
			for (Material material : materialesUsados) {
				System.out.println(material);
			}
			System.out.println("+-------------------------------------------------------+");
			inventario.setSillas(sillasEnsambladas);
			for (Silla silla : sillasEnsambladas) {
				System.out.println(silla);
			}
			System.out.println("+-------------------------------------------------------+");

			String insertQuerySilla = "INSERT INTO silla (nombre, costo, precio_venta , estado) VALUES ( ?, ?, ?, ?)";
			statement = conexion.prepareStatement(insertQuerySilla, Statement.RETURN_GENERATED_KEYS);
			int rowsInsertedSilla = 0;
			for (Silla silla : sillasEnsambladas) {
				statement.setString(1, silla.getNombre());
				statement.setDouble(2, silla.getCosto());
				statement.setDouble(3, silla.getPrecio_venta());
				statement.setString(4, silla.getEstado());
				rowsInsertedSilla = statement.executeUpdate();
				if (rowsInsertedSilla != 0) {
					ResultSet IdsGeneradas = statement.getGeneratedKeys();
					// recorro carga material como almacenamiento temporal para posteriormente
					// asignar las ids generadas en la BD
					if (IdsGeneradas.next()) {
						for (Silla corregirIds : inventario.getSillas()) {
							String IdBD = IdsGeneradas.getString(1);
							corregirIds.setId(IdBD);
						}
					}
				}
			}

			System.out.println(rowsInsertedSilla + " filas insertadas con exito");
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e);
		} finally {
			// Cerrar recursos (statement y conexión)
			if (statement != null) {
				statement.close();
			}
			if (conexion != null) {
				conexion.close();
			}
		}
	}

	public void empaque() throws SQLException {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conexion = DriverManager.getConnection(url, user, password);
			ArrayList<Silla> empaque_sillas = new ArrayList<>();
			if (inventario.getSillas().size() < 1) {
				System.out.println("Debe haber armado las sillas primero");
			}

			for (Silla silla : inventario.getSillas()) {
				silla.setEstado("Empacado");
				// el costo del precio de compra + el salario minimo por dia divido la cantidad
				// de sillas para obtener el precio de venta
				silla.setCosto((silla.getCosto() + 101.4045) / inventario.getSillas().size());
				empaque_sillas.add(silla);
			}

			inventario.setSillas(empaque_sillas);
			String updateQuery = "UPDATE silla SET estado = ? WHERE id_silla = ?";
			statement = conexion.prepareStatement(updateQuery);

			int rowsUpdatedEstadoMaterial = 0;
			for (Silla silla : empaque_sillas) {
				statement.setString(1, "empaquetada");
				statement.setInt(2, Integer.parseInt(silla.getId()));
				rowsUpdatedEstadoMaterial = statement.executeUpdate();
			}
			if (rowsUpdatedEstadoMaterial > 0) {
				System.out.println(rowsUpdatedEstadoMaterial + "filas insertadas con exito");
			}
			System.out.println("+-------------------------------------------------------+");
			for (Silla silla_empacada : inventario.getSillas()) {
				System.out.println(silla_empacada);
			}
			System.out.println("+-------------------------------------------------------+");
			System.out.println("Se han empacado: " + "[" + inventario.getSillas().size() + "]" + " Sillas");

		} catch (Exception e) {
			System.err.println(e);
		} finally {
			// Cerrar recursos (statement y conexión)
			if (statement != null) {
				statement.close();
			}
			if (conexion != null) {
				conexion.close();
			}
		}
	}

	public void envioParaAlmacenar() throws SQLException {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conexion = DriverManager.getConnection(url, user, password);
			ArrayList<Silla> almacen_sillas = new ArrayList<>();
			if (inventario.getSillas().size() < 1) {
				System.out.println("Debe haber empaquetado sillas primero");
			}
			for (Silla silla : inventario.getSillas()) {
				silla.setEstado("Almacenado");
				// el costo del precio de compra + el salario minimo por dia divido la cantidad
				// de sillas para obtener el precio de venta
				silla.setCosto((silla.getCosto() + 101.4045) / inventario.getSillas().size());
				almacen_sillas.add(silla);
			}
			String updateQuery = "UPDATE silla SET estado = ? WHERE id_silla = ?";
			statement = conexion.prepareStatement(updateQuery);

			int rowsUpdatedEstadoMaterial = 0;
			for (Silla silla : almacen_sillas) {
				statement.setString(1, "almacenada");
				statement.setInt(2, Integer.parseInt(silla.getId()));
				rowsUpdatedEstadoMaterial = statement.executeUpdate();
			}
			if (rowsUpdatedEstadoMaterial > 0) {
				System.out.println(rowsUpdatedEstadoMaterial + "filas insertadas con exito");
			}
			inventario.setSillas(almacen_sillas);
			System.out.println("+-------------------------------------------------------+");
			for (Silla silla_almacenada : inventario.getSillas()) {
				System.out.println(silla_almacenada);
			}
			System.out.println("+-------------------------------------------------------+");
			System.out.println("Se han almacenado: " + "[" + inventario.getSillas().size() + "]" + "Sillas");

		} catch (Exception e) {
			System.err.println(e);
		} finally {
			// Cerrar recursos (statement y conexión)
			if (statement != null) {
				statement.close();
			}
			if (conexion != null) {
				conexion.close();
			}
		}
	}

	public void realizarVentaSillas() throws SQLException {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conexion = DriverManager.getConnection(url, user, password);

			ArrayList<Silla> sillas_venta = new ArrayList<>();
			ArrayList<Producto> productos_venta = new ArrayList<>();
			ArrayList<Distribuidor> distribuidores = new ArrayList<Distribuidor>();
			ArrayList<Cliente> clientes = new ArrayList<Cliente>();

			Scanner entrada = new Scanner(System.in);
			int contador = 0;
			Double costo = 0.0;
			if (inventario.getSillas().size() < 1) {
				System.out.println("Debe tener almacenadas sillas");
			}
			System.out.println("Puede vender: " + "[" + inventario.getSillas().size() + "]" + " Sillas");
			System.out.println("Ingrese el nombre del Distribuidor");
			String nombre_distribuidor = entrada.nextLine();
			System.out.println("Ingrese el numero de telefono del Distribuidor");
			String numero_distribuidor = entrada.nextLine();
			System.out.println("Ingrese la direccion del Distribuidor");
			String direccion_distribuidor = entrada.nextLine();
			System.out.println("Ingrese el correo del Distribuidor");
			String correo_distribuidor = entrada.nextLine();

			System.out.println("Cuantas sillas desea vender?");
			int cantidad = entrada.nextInt();

			// actualizamos las sillas para venderlas
			// insertamos los productos para realizar la factura
			for (int i = 0; i < cantidad; i++) {
				Silla silla = inventario.getSillas().remove(0);
				silla.setCosto(silla.getCosto() + (silla.getCosto() * 0.12));
				Producto producto = new Producto(silla.getId(), silla.getNombre(), silla.getCosto());
				costo = silla.getCosto();
				productos_venta.add(producto);
				sillas_venta.add(silla);
			}
//REALIZAMOS UNA ACTUALIZACION PARA ACTUALIZAR LAS SILLAS EN LA BD
			String updateQuery = "UPDATE silla SET estado = ? WHERE id_silla = ?";
			statement = conexion.prepareStatement(updateQuery);
			int rowsUpdatedEstadoSilla = 0;
			for (Silla sventa : sillas_venta) {
				statement.setString(1, "vendida");
				statement.setInt(2, Integer.parseInt(sventa.getId()));
				rowsUpdatedEstadoSilla = statement.executeUpdate();
			}
			if (rowsUpdatedEstadoSilla > 0) {
				System.out.println(rowsUpdatedEstadoSilla + "filas insertadas con exito");
			}

			// actualizamos el inventairo
			inventario.setSillas(sillas_venta);
			String informacion_venta = "Por una compra de: " + cantidad + "Sillas" + "Se tiene un cobro de : " + "Q"
					+ costo;
			// creamos un nuevo distribuidor
			Distribuidor distribuidor = new Distribuidor(generarId(), nombre_distribuidor, numero_distribuidor,
					direccion_distribuidor, correo_distribuidor, inventario.getSillas(), informacion_venta);
			System.out.println("+-------------------------------------------------------+");
			for (Silla sillas_vendidas : sillas_venta) {
				System.out.println(sillas_vendidas);
			}
			System.out.println("+-------------------------------------------------------+");
			System.out.println("Ha vendido: " + "[" + cantidad + "]" + " sillas");

			// insertamos el distribuidor en la bd
			String insertQueryDistribuidor = "INSERT INTO distribuidor (id_silla, nombre, direccion, telefono, correo_electronico) VALUES (?,?,?,?,?)";
			statement = conexion.prepareStatement(insertQueryDistribuidor);
			int rowsInsertedDistribuidor = 0;
			for (Silla silla : sillas_venta) {
				statement.setInt(1, Integer.parseInt(silla.getId()));
				statement.setString(2, distribuidor.getNombre());
				statement.setString(3, distribuidor.getDireccion());
				statement.setString(4, distribuidor.getNumero_telefono());
				statement.setString(5, distribuidor.getCorreo());
				rowsInsertedDistribuidor = statement.executeUpdate();
			}
			// creamos la factura con la inforamcion correspondiente

			Factura factura_cliente = distribuidor.Generar_factura(generarId(), costo, nombre_distribuidor,
					productos_venta);
			String insertQueryFactura = "INSERT INTO factura (id_Factura ,fecha, precio_venta, institucion) VALUES (?,?,?,?)";
			statement = conexion.prepareStatement(insertQueryFactura);
			Timestamp fecha = Timestamp.valueOf(factura_cliente.getFecha());
			statement.setInt(1, Integer.parseInt(factura_cliente.getId()));
			statement.setTimestamp(2, fecha); // Cambiado de setDate a setTimestamp
			statement.setDouble(3, costo);
			statement.setString(4, factura_cliente.getNombre_institucion());
			int rowsInsertedFactura = statement.executeUpdate();
			if (rowsInsertedFactura > 0) {
				System.out.println("Se han insertado: " + rowsInsertedFactura + " filas");
			}
			System.out.println("Presione [ENTER] ");
			entrada.nextLine();
			System.out.println("Ingrese nombre del cliente: ");
			String nombre_cliente = entrada.nextLine();
			System.out.println("Ingrese numero de telefono del cliente: ");
			String numero_cliente = entrada.nextLine();
			System.out.println(factura_cliente.toString());
			// Insertamos un nuevo cliente
			Cliente cliente = new Cliente(generarId(), nombre_cliente, numero_cliente, factura_cliente);
			String insertQueryCliente = "INSERT INTO cliente (nombre, telefono, id_Factura	) VALUES (?,?,?)";
			statement = conexion.prepareStatement(insertQueryCliente);
			statement.setString(1, cliente.getNombre());
			statement.setString(2, cliente.getNumero_telefono()); // Cambiado de setDate a setTimestamp
			statement.setInt(3, Integer.parseInt(factura_cliente.getId()));
			int rowsInsertedCliente = statement.executeUpdate();
			if (rowsInsertedFactura > 0) {
				System.out.println("Se han insertado: " + rowsInsertedFactura + " filas");
			}
			distribuidores.add(distribuidor);
			clientes.add(cliente);

			for (Distribuidor revision_distribuidor : inventario.getDistribuidores()) {
				System.out.println("Se agrego el distribuidor: " + revision_distribuidor.getNombre());
			}
			for (Cliente revision_cliente : inventario.getClientes()) {
				System.out.println("Se agrego el cliente: " + revision_cliente.getNombre());
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			// Cerrar recursos (statement y conexión)
			if (statement != null) {
				statement.close();
			}
			if (conexion != null) {
				conexion.close();
			}
		}
	}

	private String generarId() {
		Random random = new Random();
		int numeroAleatorio = random.nextInt(100000) + 1;
		String id = String.valueOf(numeroAleatorio);
		return id;
	}
}
