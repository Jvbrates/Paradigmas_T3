package infra.console.formularios.cadastro;

import infra.console.formularios.Formulario;
import infra.entidades.Registro;
import infra.negocios.Registros;

public abstract class FCadastro<T extends Registro> extends Formulario {
	private Registros<T> registros;

	public FCadastro(Registros<T> registros) {
		setRegistros(registros);
	}

	public Registros<T> getRegistros() {
		return registros;
	}

	public void setRegistros(Registros<T> registros) {
		this.registros = registros;
	}

}
