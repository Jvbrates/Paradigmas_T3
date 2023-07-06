package infra.console.formularios;

/**
 * A classe Formulario representa um formulario 
 * @author Piveta
 *
 */
public abstract class Formulario {
	protected String titulo;

	public String getTitulo() {
		if (titulo == null)
			return "(Sem t�tulo no formulario)";
		else
			return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void mostrar(){
		System.out.println("----------------------------");
		try {
		System.out.println(getTitulo());
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("----------------------------");
	}


}
