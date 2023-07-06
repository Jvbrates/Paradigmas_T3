package infra.console.formularios.cadastro.campos;

public abstract class Campo{
	private String rotulo;
	public Campo(String rotulo) {
		setRotulo(rotulo);
	}
	public Campo(String rotulo, int minimo, int maximo) {
		this(rotulo);
		setMinimo(minimo);
		setMaximo(maximo);
	}

	public String getRotulo() {
		return rotulo;
	}
	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}
	private Integer minimo;
	private Integer maximo;
	public Integer getMinimo() {
		return minimo;
	}
	public void setMinimo(Integer minimo) {
		this.minimo = minimo;
	}
	public Integer getMaximo() {
		return maximo;
	}
	public void setMaximo(Integer maximo) {
		this.maximo = maximo;
	}
	@Override
	public boolean equals(Object obj) {
		Campo c = (Campo) obj;
		return getRotulo().equals(c.getRotulo());
	}
}
