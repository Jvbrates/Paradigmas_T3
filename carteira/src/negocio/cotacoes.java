package negocio;

import dados.FabricaDAO;
import dados.database.DAOUsuario;
import dados.database.DAOinventario_carteira;
import entidades.cotacao;
import infra.dados.armazenamento.Armazenamento;
import infra.dados.armazenamento.TipoArmazenamento;
import infra.negocios.Registros;

public class cotacoes extends Registros<cotacao> {
    public cotacoes(){
        Armazenamento.setAtual(TipoArmazenamento.BANCO);
        FabricaDAO f = FabricaDAO.getFabricaDAO();
        setDao(f.getDAOcotacao());
        setRotulo("Cotação");
    }
}
