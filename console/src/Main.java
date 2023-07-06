import infra.console.menus.Menu;
import infra.dados.ExceptionInfraDados;
import infra.negocios.DadoNaoEncontrado;

public class Main {

    int user_id = 1;

    public static void main(String[] args) throws Exception {


        SimpleMenu sm = new SimpleMenu();
        vender v = new vender();
        comprar c = new comprar();
        estatisticas stat = new estatisticas();

        sm.addOptExec(c, "Comprar");
        sm.addOptExec(v, "Vender");
        sm.addOptExec(stat, "Estatísticas");
        while (true) {
            sm.setTitulo("Trabalho 3 Paradigmas");
            int opt = sm.mostrar();
            if(opt == sm.getOpcaoSair()){
                break;
            }

        try {

            sm.executar(opt - 1);
        }catch (ExceptionInInitializerError | ExceptionInfraDados | DadoNaoEncontrado | NoClassDefFoundError r){
           System.out.println(r.getMessage());
           System.out.println(r.getCause().getMessage());

        }


        }
    }

}


