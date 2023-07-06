package infra.console.formularios.cadastro;

import infra.entidades.Registro;
import infra.negocios.Registros;

public abstract class FAlteracao<T extends Registro> extends FBusca<T>{
	public FAlteracao(Registros<T> registros) {
		super(registros);
		setTitulo("Alteracao de " + registros.getRotulo());
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
}
