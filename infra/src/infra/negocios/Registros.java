package infra.negocios;

import infra.dados.dao.DAO;
import infra.entidades.Registro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Registros<T extends Registro> {
	protected String rotulo;
	protected DAO<T> dao;
	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}

	public DAO<T> getDao() {
		return dao;
	}

	public void setDao(DAO<T> dao) {
		this.dao = dao;
	}

	public void inserir(T t){
		if (t == null)
			throw new IllegalArgumentException("Nao pode ser inserido um objeto nulo (NULL)");
		try {
			dao.adicionar(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void alterar(T t) {
		if (t == null)
			throw new NullPointerException("Nao pode ser alterado um objeto nulo (NULL)");
		try {
			dao.alterar(t);
		} catch (IllegalArgumentException | DadoNaoEncontrado e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void remover(T t){
		if (t == null)
			throw new IllegalArgumentException("Nao pode ser removido um objeto nulo (NULL)");
		try {
			dao.remover(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Collection<T> buscar(T t) throws IllegalAccessException{
		if (t == null)
			throw new IllegalArgumentException("Nao pode ser buscado um objeto nulo (NULL)");

		Collection<T> r = new ArrayList<>();

		try {
			r = dao.buscar(t);
		}  catch (DadoNaoEncontrado ignored){
			System.out.println("Busca retornou vazio");

		}

		return r;

	}

	public Collection<T> buscarTodos() {
		try {
			return dao.buscarTodos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
