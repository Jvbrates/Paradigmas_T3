package infra.dados.armazenamento;


public class Armazenamento {
	private static TipoArmazenamento atual;

	public static TipoArmazenamento getAtual() {
		return atual;
	}

	public static void setAtual(TipoArmazenamento tipo) {
		atual = tipo;
	}
}
