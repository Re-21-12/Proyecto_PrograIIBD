package proyecto_prograii;
import java.io.*;
public interface Interfaz_Procesos_Fabrica_de_Sillas {
public abstract void revisarInventario(Inventario inventario)throws IOException;
public abstract void realizarCompraMaterial(Inventario inventario) throws IOException;
public abstract void corteDeMateriales(Inventario inventario)throws IOException;
public abstract void ensamblaje(Inventario inventario) throws IOException;
public abstract void empaque(Inventario inventario) throws IOException;
public abstract void envioParaAlmacenar(Inventario inventario)throws IOException;
public abstract void realizarVentaSillas(Inventario inventario)throws IOException;

}
