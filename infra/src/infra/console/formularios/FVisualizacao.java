package infra.console.formularios;

import infra.console.formularios.cadastro.FBusca;
import infra.entidades.Registro;
import infra.negocios.DadoNaoEncontrado;
import infra.negocios.Registros;

public abstract class FVisualizacao<T extends Registro> extends FBusca<T> {

	public FVisualizacao(Registros<T> registros) {
		super(registros);
		setTitulo("Visualizacao de " + getRegistros().getRotulo());
	}

	@Override
	public void mostrar() {
		super.mostrar();
		
		try {
			ler();
			submeter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void submeter() throws Exception {
		try {
			buscar().forEach(System.out::println);
			
		} catch (DadoNaoEncontrado e) {
			System.out.println("Registro nao encontrado.");
		}
	}
	protected abstract void ler();
}
