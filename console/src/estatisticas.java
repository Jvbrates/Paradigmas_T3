import entidades.ativo_financeiro;
import entidades.inventario_carteira;
import infra.console.util.Util;
import negocio.ativo_financeiro_negocio;
import negocio.compra_venda;
import negocio.controle_carteira;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class estatisticas implements chamavel{
    @Override
    public void executar() throws Exception {
        compra_venda c_v = new compra_venda();
        ativo_financeiro_negocio a_f = new ativo_financeiro_negocio();

        java.sql.Date from  = Util.lerDataSQL("Data de Partida:");
        java.sql.Date to  = Util.lerDataSQL("Data de Fim:");
        Collection<compra_venda.AtivoStats> ativoStats = c_v.AtivosStat(1, from, to);
        Collection<inventario_carteira> carteiraCollection = c_v.carteira(1);
        double RentTotal = .0;
       for (compra_venda.AtivoStats as: ativoStats) {
            System.out.println("------------------------------------");
            printAtivoStats(as);
            System.out.println("------------------------------------");
            RentTotal+=as.getGanho_value();
        }
        System.out.println("------------------------------------");
        System.out.println("Rentabilidade Total da Carteira:" + RentTotal);
        System.out.println("------------------------------------");
        System.out.println("Dados Atuais da Carteira:");
        System.out.println("------------------------------------");
        System.out.println("ticker| ");
        ativo_financeiro at = new ativo_financeiro();
        for (inventario_carteira carteira : carteiraCollection) {
            at.setId(carteira.getId_ativo());
            System.out.println(a_f.buscar(at).iterator().next().getTicket()+" "+carteira.getQuantidade());
        }



    }


    public void printAtivoStats(compra_venda.AtivoStats ativoStats){
        System.out.println("ticker:"+ativoStats.getTicket());
        System.out.println("EMPRESA:"+ativoStats.getEmpresa());
        System.out.println("TIPO:"+ativoStats.getTipo());
        System.out.println("Valor médio de aquisição: R$"+ativoStats.getPreco_average());
        System.out.println("GANHO/PERDA: R$"+ativoStats.getGanho_value());
        System.out.println("Rentabilidade:"+ativoStats.getGanho_percent()+"%");
        System.out.println("Valor Total Investido:"+ativoStats.getTotal_investido());
    }


    public void printCarteira(inventario_carteira i_c){

        System.out.println("Quantidade:"+i_c.getQuantidade());

    }
}
