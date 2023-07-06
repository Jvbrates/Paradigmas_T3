package negocio;

import dados.FabricaDAO;
import entidades.usuario;
import infra.dados.armazenamento.Armazenamento;
import infra.dados.armazenamento.TipoArmazenamento;
import infra.negocios.Registros;
import infra.dados.dao.database.DAOdatabase;

public class usuarios extends Registros<usuario> {
    public usuarios() {
        Armazenamento.setAtual(TipoArmazenamento.BANCO);
        FabricaDAO f = FabricaDAO.getFabricaDAO();
        setDao(f.getDAOUsuarios());
        setRotulo("Usuário");
    }
}
