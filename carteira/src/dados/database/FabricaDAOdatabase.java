package dados.database;

import dados.FabricaDAO;
import entidades.*;
import infra.dados.dao.DAO;

public class FabricaDAOdatabase extends FabricaDAO {

	private static final DAOUsuario daoUsuarios = new DAOUsuario();
	private static final DAOcotacoes daoCotacao = new DAOcotacoes();
	private static final DAOcompra_venda daoCompra_venda = new DAOcompra_venda();
	private static final DAOinventario_carteira daoInventario_carteira = new DAOinventario_carteira();

	private static final DAOativo_financeiro daoativo_financeiro = new DAOativo_financeiro();

	public DAO<usuario> getDAOUsuarios(){return daoUsuarios;}


	public DAO<historico> getDAOcompra_venda(){ return daoCompra_venda;}

	@Override
	public DAO<inventario_carteira> getDAOInventarioCarteira() {
		return daoInventario_carteira;
	}

	@Override
	public DAO<ativo_financeiro> getDAOAtivoFinanceiro() {
		return daoativo_financeiro;
	}

	@Override
	public DAO<cotacao> getDAOcotacao() {
		return daoCotacao;
	}

}
