package summer.camp.judge.compiler;

import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;

@SuppressWarnings("javadoc")
public class JavaCompilerDiagnosticListener implements DiagnosticListener<JavaFileObject> {
	@Override
	public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
		System.err.println("Line Number -> " + diagnostic.getLineNumber());
		System.err.println("code -> " + diagnostic.getCode());
		System.err.println("Message -> " + diagnostic.getMessage(Locale.ENGLISH));
		System.err.println("Source -> " + diagnostic.getSource());
		System.err.println(" ");
	}
}
