package summer.camp.judge.compiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.tools.JavaFileObject;

@SuppressWarnings("javadoc")
public class CompilerTest {

	private static JavaFileObject getJavaFileObject() {
		StringBuilder contents = new StringBuilder("package math;" + "import java.io.BufferedWriter;" + "import java.io.IOException;"
				+ "import java.nio.charset.Charset;" + "import java.nio.file.Files;" + "import java.nio.file.Paths;" + "public class Calculator { "
				+ "  public void testAdd(String[] args) { " + "Charset charset = Charset.forName(\"UTF-8\");"
				+ "Integer result = Integer.parseInt(args[0]) + Integer.parseInt(args[1]);"
				+ "System.out.println(result);"
				+ "try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(\"/out.txt\"), charset)) {" + "writer.write(result);"
				+ "} catch (IOException x) {}} " + "public static void main(String[] args) { " + "    Calculator cal = new Calculator(); "
				+ "    cal.testAdd(args); " + "  } " + "} ");
		JavaFileObject so = null;
		try {
			so = new InMemoryJavaFileObject("math.Calculator", contents.toString());
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return so;
	}

	public static void testAdd(String[] args) {
		Charset charset = Charset.forName("UTF-8");
		Integer result = Integer.parseInt(args[0]) + Integer.parseInt(args[1]);
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("/out.txt"), charset)) {
			writer.write(result);
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	public static void main(String[] args) throws Exception {

		Compiler compiler = new Compiler("testUser");

		// 1.Construct an in-memory java source file from your dynamic code
		JavaFileObject file = getJavaFileObject();

		// 2.Compile your files by JavaCompiler
		compiler.compile(file);
		// 3.Load your class by URLClassLoader, then instantiate the instance,
		// and call method by reflection
		String[] arguments = { "200", "300" };
		compiler.runIt("math.Calculator", arguments);

		// compiler.cleanClassOutputFolder();
	}
}
