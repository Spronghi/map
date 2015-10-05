package util;

public class EmptyDatasetException extends Exception{
	private static final long serialVersionUID = 1L;

	public EmptyDatasetException(String msg){
		super(msg);
	}
	public EmptyDatasetException(){
		super();
	}

}
