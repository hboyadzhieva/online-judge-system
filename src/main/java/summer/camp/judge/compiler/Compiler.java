package summer.camp.judge.compiler;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Compiler {

	private static String classOutputFolder = "./bin";

	public static class MyDiagnosticListener implements DiagnosticListener<JavaFileObject> {
		public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
			System.err.println("Line Number -> " + diagnostic.getLineNumber());
			System.err.println("code -> " + diagnostic.getCode());
			System.err.println("Message -> " + diagnostic.getMessage(Locale.ENGLISH));
			System.err.println("Source -> " + diagnostic.getSource());
			System.err.println(" ");
		}
	}

	public static class InMemoryJavaFileObject extends SimpleJavaFileObject {
		private String contents = null;

		public InMemoryJavaFileObject(String className, String contents) throws Exception {
			super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
			this.contents = contents;
		}

		public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
			return contents;
		}
	}

	public static void compile(JavaFileObject file) {
		// get system compiler:
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		// for compilation diagnostic message processing on compilation
		// WARNING/ERROR
		MyDiagnosticListener diagnosticListener = new MyDiagnosticListener();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticListener, Locale.ENGLISH, null);
		// specify classes output folder
		Iterable options = Arrays.asList("-d", classOutputFolder);
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticListener, options, null, Arrays.asList(file));
		task.call();
	}

	public static void runIt(String className) {
		// Create a File object on the root of the directory
		// containing the class file
		File file = new File(classOutputFolder);

		try {
			// Convert File to a URL
			URL url = file.toURL(); // file:/classes/demo
			URL[] urls = new URL[] { url };

			// Create a new class loader with the directory
			ClassLoader loader = new URLClassLoader(urls);

			Class thisClass = loader.loadClass(className);

			Class params[] = {};
			Object paramsObj[] = {};
			Object instance = thisClass.newInstance();
			Method thisMethod = thisClass.getDeclaredMethod("testAdd", params);

			// run the testAdd() method on the instance:
			thisMethod.invoke(instance, paramsObj);
		} catch (MalformedURLException e) {
		} catch (ClassNotFoundException e) {
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
	}

	private static JavaFileObject getJavaFileObject() {
		StringBuilder contents = new StringBuilder(
				"package math;" + "public class Calculator { " + "  public void testAdd() { "
						+ "    System.out.println(200+300) " + "  } " + "  public static void main(String[] args) { "
						+ "    Calculator cal = new Calculator(); " + "    cal.testAdd(); " + "  } " + "} ");
		JavaFileObject so = null;
		try {
			so = new InMemoryJavaFileObject("math.Calculator", contents.toString());
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return so;
	}

	public static void main(String[] args) throws Exception {
		// 1.Construct an in-memory java source file from your dynamic code
		JavaFileObject file = getJavaFileObject();

		// 2.Compile your files by JavaCompiler
		compile(file);

		// 3.Load your class by URLClassLoader, then instantiate the instance,
		// and call method by reflection
		runIt("math.Calculator");
	}
}