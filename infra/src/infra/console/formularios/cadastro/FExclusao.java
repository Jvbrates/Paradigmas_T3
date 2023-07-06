package infra.console.formularios.cadastro;

import java.util.Collection;

import infra.console.util.Util;
import infra.entidades.Registro;
import infra.negocios.DadoNaoEncontrado;
import infra.negocios.Registros;

public abstract class FExclusao<T extends Registro> extends FBusca<T> {
	public FExclusao(Registros<T> registros) {
		super(registros);
		setTitulo("Exclusao de " + registros.getRotulo());
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
			Collection<T> resultQueryT = buscar();
			T t = resultQueryT.iterator().next();
			if (Util.confirma(getTitulo())) {
				getRegistros().remover(t);
				System.out.println("Registro removido.");
			} else
				System.out.println("Opera��o cancelada - " + getTitulo());
		} catch (DadoNaoEncontrado e) {
			System.out.println("Registro nao encontrado.");
		}
	}
}
