package summer.camp.judge.compiler;

public class ExecutionException extends RuntimeException {
	public ExecutionException(String message) {
		super(message);
	}

	public ExecutionException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public ExecutionException(String message, Exception ex) {
		super(message, ex);
	}

}
