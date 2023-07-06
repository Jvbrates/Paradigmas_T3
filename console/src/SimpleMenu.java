import infra.console.menus.Menu;

import java.util.ArrayList;

public class SimpleMenu extends Menu{
    ArrayList<chamavel> objects = new ArrayList<>();

    void addOptExec(chamavel A, String name){
        objects.add(A);
        this.opcoes.add(name);
    }
    @Override
    public void executar(int opcao) throws Exception {

        objects.get(opcao).executar();
    }


}
