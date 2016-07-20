package summer.camp.judge.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;

@SuppressWarnings("javadoc")
public class Compiler {

	private static final String CLASSES_OUTPUT_FOLDER = "./compiledTasks/";

	private String currentUserClassesOutputFolder;

	private File currentUserOutputFolder;

	public Compiler(String userId) {
		this.currentUserClassesOutputFolder = CLASSES_OUTPUT_FOLDER + userId + "/";
		this.currentUserOutputFolder = new File(currentUserClassesOutputFolder);
		if (!currentUserOutputFolder.exists()) {
			currentUserOutputFolder.mkdirs();
		}
	}

	public void compile(JavaFileObject code) {
		// get system compiler:
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		// for compilation diagnostic message processing on compilation
		// WARNING/ERROR
		JavaCompilerDiagnosticListener diagnosticListener = new JavaCompilerDiagnosticListener();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticListener, Locale.ENGLISH, null);
		// specify classes output folder
		Iterable<String> options = Arrays.asList("-d", currentUserClassesOutputFolder, "-s", currentUserClassesOutputFolder);
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticListener, options, null, Arrays.asList(code));
		task.call();
	}

	public String runIt(String className, String[] arguments) {
		// Create a File object on the root of the directory
		// containing the class file

		URLClassLoader loader = null;

		try {
			// Convert File to a URL
			URL url = currentUserOutputFolder.toURL(); // file:/classes/demo
			URL[] urls = new URL[] { url };

			// Create a new class loader with the directory
			loader = new URLClassLoader(urls);

			Class thisClass = loader.loadClass(className);

			Class params[] = { String[].class };
			Object paramsObj[] = { arguments };
			Object instance = thisClass.newInstance();
			Method mainMethod = thisClass.getDeclaredMethod("main", params);

			// run the testAdd() method on the instance:
			PrintStream test = new PrintStream("./test");
			System.setOut(test);
			mainMethod.invoke(instance, paramsObj);
			test.flush();
			test.close();

			return null;
			// return result();
		} catch (MalformedURLException e) {

		} catch (ClassNotFoundException e) {

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				loader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private String result() {
		Charset charset = Charset.forName("UTF-8");
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(currentUserClassesOutputFolder + "/out.txt"), charset)) {
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void cleanClassOutputFolder() {
		try {
			FileUtils.deleteDirectory(currentUserOutputFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
