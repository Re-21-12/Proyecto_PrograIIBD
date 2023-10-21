package proyecto_prograii;

public class Estado {
private String etapa;

public String getEtapa() {
	return etapa;
}

public void setEtapa(String etapa) {
	this.etapa = etapa;
}

/**
 * @param etapa
 */
public Estado(String etapa) {
	super();
	this.etapa = etapa;
}

@Override
public String toString() {
	return "Estado [etapa=" + etapa + "]";
}

}
