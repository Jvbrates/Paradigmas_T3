package infra.dados.dao;

import infra.dados.ExceptionInfraDados;
import infra.entidades.Registro;
import infra.negocios.DadoNaoEncontrado;

import java.sql.Date;
import java.util.Collection;

public interface DAO<T extends Registro> {
	public void adicionar(T t) throws ExceptionInfraDados;
	public void remover(T t) throws DadoNaoEncontrado;
	public void alterar(T e) throws DadoNaoEncontrado;
	public Collection<T> buscar(T elemento) throws DadoNaoEncontrado;
	public Collection<T> buscarTodos() throws DadoNaoEncontrado;

	public Collection<T> buscarDataRangebuscar(T t, java.sql.Date from, java.sql.Date to) throws DadoNaoEncontrado;
}
