package ubu.gii.dass.c01 ;

public class NotFreeInstanceException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFreeInstanceException(){
		super("No hay mas instancias reutilizables disponibles. Reintentalo mas tarde");
	}
}