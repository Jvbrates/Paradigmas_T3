package negocio;

import dados.FabricaDAO;
import dados.database.DAOcotacoes;
import dados.database.DAOinventario_carteira;
import infra.dados.armazenamento.Armazenamento;
import infra.dados.armazenamento.TipoArmazenamento;
import infra.negocios.Registros;
import entidades.inventario_carteira;

public class controle_carteira extends Registros<inventario_carteira> {


    public controle_carteira() {
        Armazenamento.setAtual(TipoArmazenamento.BANCO);
        FabricaDAO f = FabricaDAO.getFabricaDAO();
        setDao(f.getDAOInventarioCarteira());
        setRotulo("Usuário");
    }
}
