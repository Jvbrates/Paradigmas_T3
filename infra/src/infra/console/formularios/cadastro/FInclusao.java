package infra.console.formularios.cadastro;

import infra.console.formularios.cadastro.campos.Campo;
import infra.console.formularios.cadastro.campos.CampoInteiroMonoValorado;
import infra.console.formularios.cadastro.campos.CampoMonoValorado;
import infra.console.formularios.cadastro.campos.CampoMultiValorado;
import infra.console.util.Util;
import infra.entidades.Registro;
import infra.negocios.Registros;

import java.util.ArrayList;

public abstract class FInclusao<T extends Registro> extends FCadastro<T> {
	private String modo;

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	protected T registro;

	public FInclusao(Registros<T> registros) {
		super(registros);
		setTitulo("Inclus�o de " + registros.getRotulo());
	}

	protected ArrayList<Campo> campos = new ArrayList<>();

	protected void adicionarCampo(Campo c) {
		campos.add(c);
	}

	protected void removeCampo(String rotulo){
		campos.removeIf(c -> c.getRotulo().equals(rotulo));
	}

	public void setCampos(ArrayList<Campo> campos) {
		this.campos = campos;
	}

	public void setRegistro(T registro) {
		this.registro = registro;
	}

	public T getRegistro() {
		return registro;
	}

	public ArrayList<Campo> getCampos() {
		return campos;
	}

	@Override
	public void mostrar(){
		if (getRegistro() == null)
			throw new IllegalArgumentException("O registro nao pode ser nulo. Use setRegistro no construtor do formulario...");
		super.mostrar();
		ler();
		vincular();
		if (Util.confirma(getTitulo()))
			submeter();
		else
			System.out.println("Opera��o cancelada - " + getTitulo());
	}

	protected abstract void vincular();

	protected void ler() {
		for (Campo c : campos) {
			if (c instanceof CampoMonoValorado) {
				CampoMonoValorado temp = (CampoMonoValorado) c;
				if (temp.getValor() != null)
					System.out.println(c.getRotulo() + ": " + temp.getValor());
				else {
					if (modoAlteracao())
						temp.setMinimo(0);
					temp.setValor(Util.lerString(c.getRotulo() + ": ", temp.getMinimo(), temp.getMaximo()));
				}
			}
			if (c instanceof CampoInteiroMonoValorado) {
				CampoInteiroMonoValorado temp = (CampoInteiroMonoValorado) c;
				if (temp.getValor() != 0)
					System.out.println(c.getRotulo() + ": " + temp.getValor());
				else {
					if (modoAlteracao())
						temp.setMinimo(0);
					temp.setValor(Util.lerInteiro(c.getRotulo() + ": ", temp.getMinimo(), temp.getMaximo()));
				}
			}
			if (c instanceof CampoMultiValorado) {
				CampoMultiValorado temp = (CampoMultiValorado) c;
				System.out.println(temp.getRotulo());
				for (int i = 1; i <= temp.getMaximoLeituras(); i++) {
					if (modoAlteracao())
						temp.setMinimo(0);
					System.out.println("Digite \\ para parar de informar valores");
					String valorLido = Util.lerString(i + ":", temp.getMinimo(), temp.getMaximo());
					if ("\\".equals(valorLido))
						break;
					else {
						temp.getValores().add(valorLido);
					}
				}
			}
		}
	}

	public abstract void submeter();

	public CampoMonoValorado getCampoMonoValorado(String nomeCampo) {
		for (Campo c : getCampos())
			if (c instanceof CampoMonoValorado)
				if (nomeCampo.equals(c.getRotulo()))
					return (CampoMonoValorado) c;
		throw new IllegalArgumentException("Nao foi encontrado tal campo monovalorado na lista de campos do formulario");
	}

	public CampoInteiroMonoValorado getCampoInteiroMonoValorado(String nomeCampo) {
		for (Campo c : getCampos())
			if (c instanceof CampoInteiroMonoValorado)
				if (nomeCampo.equals(c.getRotulo()))
					return (CampoInteiroMonoValorado) c;
		throw new IllegalArgumentException("Nao foi encontrado tal campo monovalorado na lista de campos do formulario");
	}

	protected String getValor(String nomeCampo) {
		return getCampoMonoValorado(nomeCampo).getValor();
	}

	protected int getValorInteiro(String nomeCampo) {
		return getCampoInteiroMonoValorado(nomeCampo).getValor();
	}


	protected ArrayList<String> getValores(String nomeCampo) {
		for (Campo c : getCampos())
			if (c instanceof CampoMultiValorado)
				if (nomeCampo.equals(c.getRotulo()))
					return ((CampoMultiValorado) c).getValores();
		throw new IllegalArgumentException("Nao foi encontrado tal campo multivalorado na lista de campos do formulario");
	}

	public boolean modoAlteracao() {
		return "A".equals(getModo());
	}
}
