package JBeeExceptions;

public class JbeeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6029573476762319283L;


	public JbeeException(String excpt) {
		super(excpt);
	}

	public JbeeException(Throwable cause) {
		super(cause);

	}

	public JbeeException(String excpt, Throwable cause) {
		super(excpt, cause);
	}

	public JbeeException(String excpt, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(excpt, cause, enableSuppression, writableStackTrace);
	}

}
