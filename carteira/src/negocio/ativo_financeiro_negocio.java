package negocio;

import dados.FabricaDAO;
import dados.database.DAOtipo_ativo_financeiro;
import entidades.ativo_financeiro;
import entidades.tipo_ativo_financeiro;
import infra.dados.armazenamento.Armazenamento;
import infra.dados.armazenamento.TipoArmazenamento;
import infra.negocios.DadoNaoEncontrado;
import infra.negocios.Registros;

import java.util.Collection;
import java.util.Objects;

public class ativo_financeiro_negocio extends Registros<ativo_financeiro> {

    DAOtipo_ativo_financeiro DaoTipo = new DAOtipo_ativo_financeiro();
    public ativo_financeiro_negocio() {
        Armazenamento.setAtual(TipoArmazenamento.BANCO);
        FabricaDAO f = FabricaDAO.getFabricaDAO();
        setDao(f.getDAOAtivoFinanceiro());
        setRotulo("Usuário");
    }

    public String tipo_ativo_str(long tipo_ativo_id){
        tipo_ativo_financeiro taf = new tipo_ativo_financeiro();
        taf.setId(tipo_ativo_id);

        taf = DaoTipo.buscar(taf).iterator().next();

        return taf.getAlias();

    }

    public long id_by_ticker(String ticker) throws Exception {
        Collection<ativo_financeiro> ativoFinanceiros = getDao().buscarTodos();

        for (ativo_financeiro a_f : ativoFinanceiros) {
            if (Objects.equals(a_f.getTicket(), ticker)) {
                return a_f.getId();
            }
        }

        return 0;
    }
}



