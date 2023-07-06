import entidades.ativo_financeiro;
import entidades.historico;
import infra.console.util.Util;
import negocio.ativo_financeiro_negocio;
import infra.console.util.Util.*;
import negocio.compra_venda;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

public class comprar implements chamavel{

    @Override
    public void executar() throws Exception {

        ativo_financeiro_negocio at_f = new ativo_financeiro_negocio();
        compra_venda c_v = new compra_venda();

        String ticker;

        Collection<ativo_financeiro> ativoFinanceiros = at_f.buscarTodos();
        System.out.println("TICKER|EMPRESA|TIPO");
        for (ativo_financeiro f : ativoFinanceiros) {

            System.out.println(f.getTicket().replace(" ", "") + "," + f.getNome_empresa().replace(" ", "") + "," + at_f.tipo_ativo_str(f.getTipo_ativo_financeiro()).replace(" ", ""));
        }

        long id_ativo;
        do {
            ticker = Util.lerString("TICKER:", 4, 500);

            id_ativo = at_f.id_by_ticker(ticker);
        } while (id_ativo == 0);

        int quant = Util.lerInteiro("QUANTIDADE:", 0, 999999999);

        double valor_unit = Util.lerDouble("Valor unitário: ", 0, 999999999);

        java.sql.Date date= Util.lerDataSQL("Data da Operação");

        historico h = new historico();
        h.setId_ativo(id_ativo);
        h.setId_user(1);
        h.setCompra(true);
        h.setQuantidade(quant);
        h.setValor_unit(valor_unit);
        h.setDia(date);
        LocalDate.of(222,4,5);
        c_v.comprar(h);
    }
}
