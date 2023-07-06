package negocio;

import dados.FabricaDAO;
import dados.database.DAOUsuario;
import dados.database.DAOinventario_carteira;
import entidades.*;
import infra.dados.ExceptionInfraDados;
import infra.dados.armazenamento.Armazenamento;
import infra.dados.armazenamento.TipoArmazenamento;
import infra.negocios.DadoNaoEncontrado;
import infra.negocios.Registros;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class compra_venda extends Registros<historico> {

    public static class AtivoStats {
        public String getTicket() {
            return ticker;
        }

        public String getEmpresa() {
            return Empresa;
        }

        public String getTipo() {
            return tipo;
        }

        public double getGanho_percent() {
            return ganho_percent;
        }

        public double getGanho_value() {
            return ganho_value;
        }

        public double getPreco_average() {
            return preco_average;
        }

        String ticker;
        String Empresa;
        String tipo;
        double ganho_percent;
        double ganho_value;
        double preco_average;
        double total_investido;

        public double getTotal_investido() {
            return total_investido;
        }

        AtivoStats(Collection<historico> movimentacoes, java.sql.Date fechamento) throws IllegalAccessException {

            //Define o ticker, tipo e nome da empresa.
            historico primeiro = movimentacoes.iterator().next();
            ativo_financeiro_negocio at_v = new ativo_financeiro_negocio();
            ativo_financeiro at = new ativo_financeiro();
            at.setId(primeiro.getId_ativo());
            at = at_v.buscar(at).iterator().next();


            this.Empresa = at.getNome_empresa();
            this.ticker = at.getTicket();
            this.tipo = at_v.tipo_ativo_str(at.getTipo_ativo_financeiro());

            List<historico> compras = movimentacoes.stream().filter(historico::isCompra).toList();
            List<historico> vendas = movimentacoes.stream().filter(historico::isVenda).toList();

            double total_investido = .0, total_vendido = .0;
            int n_ativos_compra = 0, n_ativos_venda =0;
            if (compras.size() > 0) {
                total_investido = compras.stream().map(c -> c.getValor_unit() * c.getQuantidade()).reduce(Double::sum).get();

                n_ativos_compra = compras.stream().map(historico::getQuantidade).reduce(Integer::sum).get();
            }

            if( vendas.size() > 0){
                total_vendido = vendas.stream().map(c -> c.getValor_unit() * c.getQuantidade()).reduce(Double::sum).get();

                n_ativos_venda = vendas.stream().map(historico::getQuantidade).reduce(Integer::sum).get();


            }

            int total_acoes_quant = n_ativos_compra - n_ativos_venda;
            cotacoes cot = new cotacoes();
            cotacao c = new cotacao();
            c.setId_ativo(at.getId());
            c.setDia(fechamento);
            Collection<cotacao> c_busca = cot.buscar(c);
            if(c_busca.size() == 0)
            {
                System.out.println("Não á cotações para este ativo ["+getTicket()+"]de fechamento para este dia!!!");
                c.setValor(0);
            } else {
                c = c_busca.iterator().next();
            }
            double mercado_total = total_acoes_quant*c.getValor();
            this.total_investido = total_investido;
            this.ganho_value = mercado_total - total_investido;
            this.ganho_percent = (ganho_value/total_investido)*100;
            this.preco_average = total_investido/n_ativos_compra;


        }
    }

    DAOUsuario DUser;
    DAOinventario_carteira carteira;

    public compra_venda() {
        Armazenamento.setAtual(TipoArmazenamento.BANCO);
        FabricaDAO f = FabricaDAO.getFabricaDAO();
        setDao(f.getDAOcompra_venda());
        setRotulo("Usuário");
        DUser = new DAOUsuario();
        carteira = new DAOinventario_carteira();
    }


    public void comprar(historico operacao) throws ExceptionNegocios, DadoNaoEncontrado {

        if (operacao == null) {
            throw new NullPointerException();
        }

        if (!operacao.isCompra()) {
            throw new ExceptionNegocios("Está tentanto passar um objeto setado como venda para comprar");
        }


        usuario user = new usuario();
        inventario_carteira inventarioCarteira = new inventario_carteira();

        user.setId(operacao.getId_user());
        Collection<usuario> c_u = new ArrayList<>();

        c_u = DUser.buscar(user);


        inventarioCarteira.setId_user(user.getId());
        inventarioCarteira.setId_ativo(operacao.getId_ativo());

        Collection<inventario_carteira> i_c = new ArrayList<>();
        i_c = carteira.buscar(inventarioCarteira);
        //if (c_u.size() != 1) {
         //   throw new DadoNaoEncontrado();
       // }


        user = c_u.iterator().next();

        //Caso usuário não tenha saldo suficiente
        if (operacao.getQuantidade() * operacao.getValor_unit() > user.getSaldo()) {
            throw new ExceptionNegocios("Saldo Insuficiente para compra");
        }

        //Atualizar saldo
        user.setSaldo(user.getSaldo() - (operacao.getQuantidade() * operacao.getValor_unit()));


        if (i_c.size() != 1) {
            if (i_c.size() == 0) {
                //Usuário não tinha este ativo até o momento na carteira
                inventarioCarteira.setQuantidade(0);
                //Registra Ativo na carteira
                carteira.adicionar(inventarioCarteira);
            } else {
                throw new DadoNaoEncontrado(); //Colocar excepetion aqui para duplicata de dados
            }
        } else {

            inventarioCarteira = i_c.iterator().next();
        }

        inventarioCarteira.setQuantidade(
                inventarioCarteira.getQuantidade() +
                        operacao.getQuantidade()
        );

        // Registrar Alteração na carteira
        carteira.alterar(inventarioCarteira);
        // NUNCA CONFIE NA DATA QUE VEM DO FRONT-END(DIFERENTES UTC/GMT)
        //operacao.setDia(util.timeNow());
        // Salvar no histórico
        dao.adicionar(operacao);

    }


    public void venda(historico operacao) throws NullPointerException, DadoNaoEncontrado, ExceptionNegocios, ExceptionInfraDados  {
        if (operacao == null) {
            throw new NullPointerException();
        }

        if (operacao.isCompra()) {
            System.out.println("Está tentanto passar um objeto setado como compra para vender");
            throw new DadoNaoEncontrado();
        }

        usuario user = new usuario();
        inventario_carteira inventarioCarteira = new inventario_carteira();

        user.setId(operacao.getId_user());
        Collection<usuario> c_u = DUser.buscar(user);

        inventarioCarteira.setId_user(user.getId());
        inventarioCarteira.setId_ativo(operacao.getId_ativo());
        Collection<inventario_carteira> i_c = carteira.buscar(inventarioCarteira);

        //Verificar se o usuário tem o que deseja vender
        if (i_c.size() != 1) {
            if (i_c.size() == 0)
                throw new DadoNaoEncontrado();//Tentando vender algo que nao tem

            throw new DadoNaoEncontrado(); //Colocar excepetion aqui para duplicata de dados
        }


        inventarioCarteira = i_c.iterator().next();

        //Verificar se tem o suficiente
        if (inventarioCarteira.getQuantidade() < operacao.getQuantidade()) {
            throw new ExceptionInfraDados("Você não tem esta quantia para vender");
        }

        //BUsca usuario
        if (c_u.size() != 1) {
            throw new DadoNaoEncontrado();
        }


        //Atualiza saldo
        user = c_u.iterator().next();
        user.setSaldo(user.getSaldo() +
                (operacao.getQuantidade() * operacao.getValor_unit())
        );

        //Atualiza carteira
        inventarioCarteira.setQuantidade(inventarioCarteira.getQuantidade() - operacao.getQuantidade());

        //Registra movimentação
        //operacao.setDia(util.timeNow());
        dao.adicionar(operacao);


    }

    public Collection<inventario_carteira> carteira(long id_user) throws Exception {
        inventario_carteira i_c = new inventario_carteira();
        i_c.setId_user(id_user);

        return carteira.buscar(i_c);
    }

    public Collection<AtivoStats>  AtivosStat(long id_user, java.sql.Date from, java.sql.Date to) throws DadoNaoEncontrado, IllegalAccessException {

        historico h = new historico();
        inventario_carteira inventarioCarteira = new inventario_carteira();
        inventarioCarteira.setId_user(id_user);
        Collection<inventario_carteira> ativos = carteira.buscar(inventarioCarteira);


        h.setId_user(id_user);

        Collection<Collection<historico>> movimentacoes_ativos = new ArrayList<>();

        //Obtem as movimentações por ativo;
        ativos.forEach( a -> {
            h.setId_ativo(a.getId_ativo());
            try {
                movimentacoes_ativos.add(dao.buscarDataRangebuscar(h, from, to));
            } catch (DadoNaoEncontrado ignored){

            }
        });

        Collection<AtivoStats> At_status = new ArrayList<>();

        movimentacoes_ativos.forEach(
                m -> {
                    try {
                        At_status.add(new AtivoStats(m, to));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
        );


        return  At_status;

    }
}
