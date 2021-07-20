package project.loanprocessapplication.classes;

public class NoBureauDataException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoBureauDataException(String error){
		super(error);
	}
}
