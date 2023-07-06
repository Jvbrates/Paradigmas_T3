package infra.console.formularios.cadastro;

import infra.entidades.Registro;
import infra.negocios.Registros;

public class FListagem<T extends Registro> extends FCadastro<T>{
	public FListagem(Registros<T> registros) {
		super(registros);
		setTitulo("Listagem de " + registros.getRotulo());
	}

	@Override
	public void mostrar() {
		super.mostrar();
		try {
			for (T temp : getRegistros().buscarTodos()) {
				System.out.println(temp);
				System.out.println("------------------");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
