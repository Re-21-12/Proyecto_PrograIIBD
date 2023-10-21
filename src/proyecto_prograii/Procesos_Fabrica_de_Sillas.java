package proyecto_prograii;

import java.util.*;
import java.time.*;

import java.io.*;
import java.time.LocalDateTime;
//import implementacion_interfaces.Producto;

public class Procesos_Fabrica_de_Sillas implements Interfaz_Procesos_Fabrica_de_Sillas {

	// entrada.close();

	public void revisarInventario(Inventario inventario) throws IOException {
		try {
			ArrayList<Silla> revisar_sillas = inventario.getSillas();
			ArrayList<Material> revisar_materiales = inventario.getMateriales();
			ArrayList<Material> conteo_material = new ArrayList<Material>();

			int contador_sillas = revisar_sillas.size();
			Double total = 0.0;
			for (Material material : revisar_materiales) {
				boolean material_existente = false;

				// Buscamos si el material ya está en la lista de conteo_material
				for (Material material_encontrado : conteo_material) {
					if (material_encontrado.getNombre().equals(material.getNombre())) {
						// Si ya existe, actualizamos la cantidad
						material_encontrado.setCantidad(material.getCantidad());
						material_existente = true;
						total += material_encontrado.getCantidad();
						break;
					}
				}

				// Si no existe en la lista de conteo_material, lo agregamos
				if (!material_existente) {
					Material nuevoMaterial = new Material("", material.getNombre(), 0.0,
							material.getCantidad() * revisar_sillas.size());
					conteo_material.add(nuevoMaterial);
				}
			}

			// Ahora tenemos una lista con materiales únicos y su cantidad
			for (Material material_encontrado : conteo_material) {
				System.out.println("Material: " + material_encontrado.getNombre() + ", Cantidad: "
						+ material_encontrado.getCantidad());
			}
			System.out.println("Total de materiales: " + total);

			System.out.println("Total de sillas: " + contador_sillas);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void realizarCompraMaterial(Inventario inventario) throws IOException {
		try {
			ArrayList<Material> materiales = new ArrayList<Material>(); // Mueve la inicialización aquí
			ArrayList<Producto> productos_factura = new ArrayList<Producto>();
			ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();

			Scanner entrada = new Scanner(System.in);
			Double precio = 0.0;
			String informacion_compra = "";
			if (inventario.getSillas().size() != 0) {
				System.out.println("No necesita comprar material");
			}
			if (inventario.getMateriales().size() == 0) {
				System.out.println("Necesita comprar materiales");

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
				String id_proveedor = generarId();

				do {
					System.out.println("+----------|------------------------+");
					System.out.println("|Requisitos minimos para una silla: |");
					System.out.println("|nombre    | cantidad               |");
					System.out.println("|madera    | 3u                     |");
					System.out.println("|clavos    | 10u                    |");
					System.out.println("|lijas     | 2u                     |");
					System.out.println("|barniz    | 0.9463kg               |");
					System.out.println("|pegamento | 0.1250kg               |");
					System.out.println("+----------|------------------------+");
					String id = generarId();
					System.out.println("Ingrese el nombre del material: ");
					String nombre = entrada.nextLine();
					System.out.println("Ingrese el costo del material: ");
					Double costo = entrada.nextDouble();
					System.out.println("Ingrese la cantidad de material: ");
					Double cantidad = entrada.nextDouble();
					Material material = new Material(id, nombre, costo, cantidad);
					Producto producto = new Producto(id, nombre, costo);
					productos_factura.add(producto);
					materiales.add(material);
					inventario.setMateriales(materiales);

					for (Material mostrar_material : inventario.getMateriales()) {
						precio += (mostrar_material.getCosto() * mostrar_material.getCantidad());
						cantidad += mostrar_material.getCantidad();
					}
					System.out.println("Los materiales ingresados son: " + materiales);

					System.out.println("Presione ENTER");
					entrada.nextLine();
					informacion_compra = "Por la compra de: " + materiales + "Q" + costo + "De cantidad: " + cantidad;
					System.out.println("Desea comprar mas materiales del mismo proveedor?: [s] | [n]");
					opcion = entrada.nextLine();
					materiales.add(material); // Agrega el material a la lista materiales
					inventario.setMateriales(materiales);
				} while (!opcion.equals("n"));

				Proveedor proveedor = new Proveedor(id_proveedor, nombre_proveedor, numero_proveedor,
						direccion_proveedor, correo_proveedor, materiales, informacion_compra);
				proveedores.add(proveedor);
				for (Proveedor revision_proveedor : inventario.getProveedores()) {
					System.out.println("Se agrego el proveedor: " + revision_proveedor.getNombre());
				}
				Factura factura = new Factura();
				factura = proveedor.Generar_factura(generarId(), precio, nombre_proveedor, productos_factura);

				System.out.println("La compra es: " + factura.toString());
			}
			inventario.setMateriales(materiales); // Agrega el inventario.setMateriales(materiales) aquí

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void corteDeMateriales(Inventario inventario) throws IOException {
		try {
			if (inventario.getMateriales().size() == 0) {
				System.out.println("El inventario debe contener materiales");
				return;
			}

			// una silla va a necesitar: 10 clavos, 3 pedazos de madera, 0.1250 de
			// pegamento, 0.9463 de barniz y 2 lijas
			ArrayList<Material> revisar_materiales = inventario.getMateriales();
			ArrayList<Material> material_cortado = new ArrayList<Material>();
			System.out.println(revisar_materiales);
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
			for (Material material_encontrado : revisar_materiales) {

				// Buscamos si el material ya está en la lista de conteo_material
				// lo vuelvo minusculas para obviar las mayusculas
				switch (material_encontrado.getNombre().toLowerCase()) {
				case "madera":
					madera += material_encontrado.getCantidad();
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
				case "pegamento":
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
					Material nuevoMaterial = new Material("", material_encontrado.getNombre(), 0.0,
							material_encontrado.getCantidad());
					material_cortado.add(nuevoMaterial);
					break;
				}
			}
			if (madera <= 3 || clavos <= 10 || lijas <= 2 || barniz <= 0.9463 || pegamento <= 0.1250) {
				System.out.println("Debe contener mas materiales para realizar el proceso de corte");
			}
//16.0713
			madera = madera / 3; // 2
			clavos = clavos / 10;// 2
			lijas = lijas / 2;// 2
			int barniz_redondeado = (int) (barniz / 0.9463); // 2
			int pegamento_redondeado = (int) (pegamento / 0.1250); // 2
//si la suma de lo anterior me da mas de 5 entonces puedo crear mas de una silla
			int cantidad_material_silla = madera + clavos + lijas + barniz_redondeado + pegamento_redondeado;
			int cantidad_sillas = cantidad_material_silla / 16;
			System.out.println(cantidad_sillas);
			int contador = 0;
			do {
				String id = generarId();
				contador++;
				// alteramos el costo a precio de unidad para posteriormente usarlo para generar
				// el precioo de venta
				Material madera_cortada = new Material(id, "madera", madera_costo, 3.0);
				Material clavo_cortado = new Material(id, "clavo", clavo_costo, 10.0);
				Material lija_cortada = new Material(id, "lija", lija_costo, 2.0);
				Material pegamento_cortado = new Material(id, "pegamento", pegamento_costo, 0.1250);
				Material barniz_cortado = new Material(id, "barniz", barniz_costo, 0.9463);

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
			} while (contador <= cantidad_sillas);
			inventario.setMateriales(material_cortado);
			// Si no existe en la lista de conteo_material, lo agregamos

			// Ahora tenemos una lista con materiales únicos y su cantidad
			System.out.println("Lista de materiales cortados:");
			for (Material material_encontrado : material_cortado) {
				System.out.println("Material: " + material_encontrado.getNombre() + ", Cantidad: "
						+ material_encontrado.getCantidad());
			}

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void ensamblaje(Inventario inventario) throws IOException {
		try {
			int contador = 0;
			Double costo = 0.0;
			Double venta = 0.0;
			Scanner entrada = new Scanner(System.in);
			if (inventario.getMateriales().size() < 5) {
				System.out.println("El inventario no contiene los materiales suficientes");
			}
			System.out.println("Puede armar : " + inventario.getMateriales().size() / 5 + "Sillas");
			System.out.println("Cuantas sillas desea ensamblar? : ");
			int cantidad = entrada.nextInt();
			ArrayList<Material> materialesUsados = new ArrayList<>();
			ArrayList<Silla> sillasEnsambladas = new ArrayList<>();

			do {
				for (int i = 0; i < 5; i++) {
					Material material = inventario.getMateriales().remove(0); // Remover el primer material
					materialesUsados.add(material);
					costo += material.getCosto();
				}
				contador++;
				// + ((costo * 0.5) * 0.12)
				venta = (costo * 0.5);
				Estado estado = new Estado("Ensamblaje");
				Silla silla = new Silla(generarId(), "silla", costo, venta, materialesUsados, estado);
				sillasEnsambladas.add(silla);
				inventario.setSillas(sillasEnsambladas);
			} while (contador < cantidad);
			inventario.setMateriales(materialesUsados);
			inventario.setSillas(sillasEnsambladas);
			for (Silla silla : sillasEnsambladas) {
				System.out.println(silla);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void empaque(Inventario inventario) throws IOException {
		try {
			ArrayList<Silla> empaque_sillas = new ArrayList<>();
			if (inventario.getSillas().size() < 1) {
				System.out.println("Debe haber armado las sillas primero");
			}
			Estado estado = new Estado("Empaque");
			for (Silla silla : inventario.getSillas()) {
				silla.setEstado(estado);
				// el costo del precio de compra + el salario minimo por dia divido la cantidad
				// de sillas para obtener el precio de venta
				silla.setCosto((silla.getCosto() + 101.4045) / inventario.getSillas().size());
				empaque_sillas.add(silla);
			}
			inventario.setSillas(empaque_sillas);
			System.out.println("Se han empacado: " + inventario.getSillas() + "Sillas");

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void envioParaAlmacenar(Inventario inventario) throws IOException {
		try {
			ArrayList<Silla> almacen_sillas = new ArrayList<>();
			if (inventario.getSillas().size() < 1) {
				System.out.println("Debe haber empaquetado sillas primero");
			}
			Estado estado = new Estado("Almacenada");
			for (Silla silla : inventario.getSillas()) {
				silla.setEstado(estado);
				// el costo del precio de compra + el salario minimo por dia divido la cantidad
				// de sillas para obtener el precio de venta
				silla.setCosto((silla.getCosto() + 101.4045) / inventario.getSillas().size());
				almacen_sillas.add(silla);
			}
			inventario.setSillas(almacen_sillas);
			System.out.println("Se han almacenado: " + inventario.getSillas() + "Sillas");

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void realizarVentaSillas(Inventario inventario) throws IOException {
		try {
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
			System.out.println("Puede vender: " + inventario.getSillas().size() + " Sillas");
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

			for (int i = 0; i < cantidad; i++) {
				Silla silla = inventario.getSillas().remove(0);
				silla.setCosto(silla.getCosto() + (silla.getCosto() * 0.12));
				Producto producto = new Producto(silla.getId(), silla.getNombre(), silla.getCosto());
				costo = silla.getCosto();
				productos_venta.add(producto);
				sillas_venta.add(silla);
			}
			inventario.setSillas(sillas_venta);
			String informacion_venta = "Por una compra de: " + cantidad + "Sillas" + "Se tiene un cobro de : " + "Q"
					+ costo;
			Distribuidor distribuidor = new Distribuidor(generarId(), nombre_distribuidor, numero_distribuidor,
					direccion_distribuidor, correo_distribuidor, inventario.getSillas(), informacion_venta);
			System.out.println("Ha vendido: " + cantidad + " sillas");
			Factura factura_cliente = distribuidor.Generar_factura(generarId(), costo, nombre_distribuidor,
					productos_venta);
			System.out.println("Presione [ENTER] ");
			entrada.nextLine();
			System.out.println("Ingrese nombre del cliente: ");
			String nombre_cliente = entrada.nextLine();
			System.out.println("Ingrese numero de telefono del cliente: ");
			String numero_cliente = entrada.nextLine();
			System.out.println("La factura es : " + factura_cliente.toString());
			Cliente cliente = new Cliente(generarId(), nombre_cliente, numero_cliente, factura_cliente);
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
		}
	}

	private String generarId() {
		Random random = new Random();
		int numeroAleatorio = random.nextInt(1000) + 1;
		String id = String.valueOf(numeroAleatorio);
		return id;
	}
}
