package proyecto_prograii;

import java.util.*;

import java.io.*;
import java.sql.*;
public class Principal {
	public static void main(String[] args) throws IOException {
		ArrayList<Silla> sillas = new ArrayList<Silla>();
		ArrayList<Material> materiales = new ArrayList<Material>();
		ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
		ArrayList<Distribuidor> distribuidores = new ArrayList<Distribuidor>();
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		Inventario inventario = new Inventario(sillas, materiales, proveedores, distribuidores, clientes);
		Procesos_Fabrica_de_Sillas procesos_fabrica_sillas = new Procesos_Fabrica_de_Sillas();

		Scanner entrada = new Scanner(System.in);
		System.out.println("Bienvenido a control de fabrica de sillas");
		System.out.println("Puede escoger las siguientes opciones: ");
		System.out.println(
				"\n 1. Revisar Inventario \n 2. Realizar Ordenes \n 3. Corte de materiales \n 4. Ensamblaje \n 5. Empaque \n 6. Envio Hacia el Almacen \n 0. Salir");

		String opcion = entrada.nextLine(); // Lee la opción después de mostrar las opciones
		do {

			switch (opcion) {

			case "1":
				System.out.println("Ha seleccionado Revisar Inventario");
				procesos_fabrica_sillas.revisarInventario(inventario);
				System.out.println("Presione ENTER");
				entrada.nextLine();
				opcion = "s";

				break;
			case "2":
				System.out.println("Ha seleccionado Realizar Ordenes");
				System.out.println("\n Para comprar: [c] \n Para venta: [v] ");
				String orden = entrada.nextLine();
				switch (orden) {
				case "c":
					procesos_fabrica_sillas.realizarCompraMaterial(inventario);
					System.out.println("Presione ENTER");
					entrada.nextLine();
					System.out.println("Desea regresar al menu?: [s] | [n] ");
					opcion = entrada.nextLine();

					break;
				case "v":
					procesos_fabrica_sillas.realizarVentaSillas(inventario);
					System.out.println("Presione ENTER");
					entrada.nextLine();
					System.out.println("Desea regresar al menu?: [s] | [n] ");
					opcion = entrada.nextLine();

					break;
				}
				break;
			case "3":
				System.out.println("Ha seleccionado Cortar Materiales");
				procesos_fabrica_sillas.corteDeMateriales(inventario);
				System.out.println("Presione ENTER");
				entrada.nextLine();
				System.out.println("Desea regresar al menu?: [s] | [n] ");
				opcion = entrada.nextLine();
				break;
			case "4":
				System.out.println("Ha seleccionado Ensamblar Sillas");
				procesos_fabrica_sillas.ensamblaje(inventario);
				System.out.println("Presione ENTER");
				entrada.nextLine();
				System.out.println("Desea regresar al menu?: [s] | [n] ");
				opcion = entrada.nextLine();
				break;
			case "5":
				System.out.println("Ha seleccionado Empacar Sillas");
				procesos_fabrica_sillas.empaque(inventario);
				System.out.println("Presione ENTER");
				entrada.nextLine();
				System.out.println("Desea regresar al menu?: [s] | [n] ");
				opcion = entrada.nextLine();
				break;
			case "6":
				System.out.println("Ha seleccionado Alamacenar Sillas");
				procesos_fabrica_sillas.envioParaAlmacenar(inventario);
				System.out.println("Presione ENTER");
				entrada.nextLine();
				System.out.println("Desea regresar al menu?: [s] | [n] ");
				opcion = entrada.nextLine();
				break;

			case "s":
				System.out.println("Puede escoger las siguientes opciones: ");

				System.out.println(
						"\n 1. Revisar Inventario \n 2. Realizar Ordenes \n 3. Corte de materiales \n 4. Ensamblaje \n 5. Empaque \n 6. Envio Hacia el Almacen \n 0. Salir");
				opcion = entrada.nextLine();
				break;
			default:
				System.out.println("Seleccione una opcion adecuada");
				break;
			}

		} while (!opcion.equalsIgnoreCase("0") || !opcion.equals("n"));
		System.out.println("Que tenga un lindo dia!");
		entrada.close();
	}
}
