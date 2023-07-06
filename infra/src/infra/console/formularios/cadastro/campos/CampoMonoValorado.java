package infra.console.formularios.cadastro.campos;

public class CampoMonoValorado extends Campo{
	private String valor;
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public CampoMonoValorado(String rotulo, int minimo, int maximo) {
		super(rotulo, minimo, maximo);
	}
}
