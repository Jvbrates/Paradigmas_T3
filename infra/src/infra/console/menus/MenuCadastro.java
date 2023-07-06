package infra.console.menus;

import infra.console.formularios.cadastro.FCadastro;
import infra.entidades.Registro;

import java.util.HashMap;

public class MenuCadastro<T extends Registro> extends Menu {
	HashMap<Integer, FCadastro<T>> forms = new HashMap<Integer, FCadastro<T>>();

	public MenuCadastro() {
		super();
		adicionarOpcao("Inserir");
		adicionarOpcao("Alterar");
		adicionarOpcao("Excluir");
		adicionarOpcao("Visualizar");
		adicionarOpcao("Listar");
		adicionarOpcao("Voltar");
	}

	public void adicionarForm(Integer indice, FCadastro<T> form){
		forms.put(indice, form);
	}

	public void executar(int opcao) throws Exception {
		FCadastro<T> f = forms.get(opcao);
		if (f == null)
			throw new IllegalArgumentException("Opcao invalida ou nao suportada");
		f.mostrar();
	}
}
