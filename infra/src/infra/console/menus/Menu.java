package infra.console.menus;

import infra.console.util.Util;

import java.util.ArrayList;

public abstract class Menu {
	protected String titulo;
	protected ArrayList<String> opcoes = new ArrayList<String>();

	public ArrayList<String> getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(ArrayList<String> opcoes) {
		this.opcoes = opcoes;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public void adicionarOpcao(String opcao){
		opcoes.add(opcao);
	}

	public Menu() {

	}

	public Menu(String titulo) {
		setTitulo(titulo);
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int mostrar() throws Exception{
		System.out.println("----------------------------");
		System.out.println(getTitulo());
		System.out.println("----------------------------");
		desenhar();
		System.out.println(getOpcaoSair()+". "+"Sair");
		System.out.println("----------------------------");
		return Util.lerInteiro("Digite a op��o desejada:", 1, getOpcoes().size()+1);
	}

	private void desenhar() {
		int i = 0;
		for (String s : getOpcoes())
			System.out.println(++i + ". " + s);
	}

	public int getOpcaoSair() {
		return getOpcoes().size()+1;
	}

	public abstract void executar(int opcao) throws Exception;

}
