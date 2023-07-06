package dados;

import dados.database.FabricaDAOdatabase;
import entidades.*;
import entidades.usuario;
import infra.dados.armazenamento.Armazenamento;
import infra.dados.armazenamento.TipoArmazenamento;
import infra.dados.dao.DAO;


public abstract class FabricaDAO {
	public static FabricaDAO getFabricaDAO() {		
		if (TipoArmazenamento.BANCO.equals(Armazenamento.getAtual()))
			return new FabricaDAOdatabase();
		else
			throw new IllegalArgumentException("Tipo de acesso a dados nao permitido");
	}


	public abstract DAO<usuario> getDAOUsuarios();


	public abstract DAO<historico> getDAOcompra_venda();

	public abstract DAO<inventario_carteira> getDAOInventarioCarteira();

	public abstract DAO<ativo_financeiro> getDAOAtivoFinanceiro();
	public abstract DAO<cotacao> getDAOcotacao();

}
