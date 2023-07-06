package infra.console.formularios.cadastro.campos;

import java.util.ArrayList;

public class CampoMultiValorado extends Campo{
	private Integer maximoLeituras;
	private ArrayList<String> valores = new ArrayList<String>();
	public Integer getMaximoLeituras() {
		return maximoLeituras;
	}
	public void setMaximoLeituras(Integer maximoLeituras) {
		if (maximoLeituras == null)
			throw new IllegalArgumentException("O maximo de leituras nao pode ser nulo.");
		this.maximoLeituras = maximoLeituras;
	}
	public ArrayList<String> getValores() {
		return valores;
	}
	public void setValores(ArrayList<String> valores) {
		this.valores = valores;
	}
	public CampoMultiValorado(String rotulo) {
		super(rotulo);
	}
	public CampoMultiValorado(String rotulo, int minimo, int maximo, int maximoLeituras) {
		super(rotulo, minimo, maximo);
		setMaximoLeituras(maximoLeituras);
	}
}
