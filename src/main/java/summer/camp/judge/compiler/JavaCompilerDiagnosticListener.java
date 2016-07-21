package summer.camp.judge.compiler;

import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;

@SuppressWarnings("javadoc")
public class JavaCompilerDiagnosticListener implements DiagnosticListener<JavaFileObject> {

	private static final String MESSAGE_HEADER = "Unresolved compilation problem:";

	private static final String LINE_NUMBER = "Line Number -> ";

	private static final String CODE = "code -> ";

	private static final String MESSAGE = "Message -> ";

	private static final String SOURCE = "Source -> ";

	private static final String NEW_LINE = "\n";

	@Override
	public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
		StringBuilder exceptionMessage = new StringBuilder();
		exceptionMessage.append(MESSAGE_HEADER);

		exceptionMessage.append(NEW_LINE);
		exceptionMessage.append(LINE_NUMBER);
		exceptionMessage.append(diagnostic.getLineNumber());

		exceptionMessage.append(NEW_LINE);
		exceptionMessage.append(CODE);
		exceptionMessage.append(diagnostic.getCode());

		exceptionMessage.append(NEW_LINE);
		exceptionMessage.append(MESSAGE);
		exceptionMessage.append(diagnostic.getMessage(Locale.ENGLISH));

		exceptionMessage.append(NEW_LINE);
		exceptionMessage.append(SOURCE);
		exceptionMessage.append(diagnostic.getSource());

		throw new CompilationErrorException(exceptionMessage.toString());
	}
}
