package summer.camp.judge.compiler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.JavaFileObject;

@SuppressWarnings("javadoc")
public class CompilerTest {

	private static JavaFileObject getJavaFileObject() {
		StringBuilder contents = new StringBuilder("package math;" + "import java.util.Scanner;" + "import java.io.BufferedWriter;"
				+ "import java.io.IOException;" + "import java.nio.charset.Charset;" + "import java.nio.file.Files;" + "import java.nio.file.Paths;"
				+ "public class Calculator { " + "public void testAdd(String[] args) { " + "Charset charset = Charset.forName(\"UTF-8\");"
				+ "Scanner in = new Scanner(System.in);" + "Integer result = in.nextInt() + in.nextInt();" + "System.out.println(result);"
				+ "try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(\"/out.txt\"), charset)) {" + "writer.write(result);"
				+ "} catch (IOException x) {}} " + "public static void main(String[] args) { " + "    Calculator cal = new Calculator(); "
				+ "    cal.testAdd(args); " + "  } " + "} ");
		JavaFileObject so = null;
		try {
			so = new InMemoryJavaFileObject(getClassName(contents.toString()), contents.toString());
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return so;
	}

	private static String getClassName(String code) {
		StringBuilder className = new StringBuilder();
		String patternPackage = "package (.*?);.*";
		Pattern p = Pattern.compile(patternPackage);
		Matcher m = p.matcher(code);
		if (m.find()) {
			String packageName = m.group(1);
			className.append(packageName);
			className.append(".");
		}

		String patternClass = "public class (.*?) .*";
		p = Pattern.compile(patternClass);
		m = p.matcher(code);
		if (m.find()) {
			className.append(m.group(1));
		}

		return className.toString();
	}

	public static void main(String[] args) throws Exception {

		Compiler compiler = new Compiler(15L);

		// 1.Construct an in-memory java source file from your dynamic code
		JavaFileObject file = getJavaFileObject();

		// 2.Compile your files by JavaCompiler
		compiler.compile(file);
		// 3.Load your class by URLClassLoader, then instantiate the instance,
		// and call method by reflection
		String result = compiler.run("math.Calculator", "700 900");
		System.out.println(result);
		// compiler.cleanClassOutputFolder();
	}
}
