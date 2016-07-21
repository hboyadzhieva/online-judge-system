package summer.camp.judge.compiler;

public class CompilationErrorException extends IllegalArgumentException {
	public CompilationErrorException(String message) {
		super(message);
	}

	public CompilationErrorException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
