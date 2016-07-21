package summer.camp.judge.compiler;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.JavaFileObject;

import summer.camp.judge.entities.Solution;
import summer.camp.judge.entities.TestCase;

@SuppressWarnings("javadoc")
public class TestSolution {

	private static final String OK_MESSAGE = "OK";

	private static final String WRONG_ANSWER_MESSAGE = "WA";

	private static final String TIME_LIMIT_EXPIRED_MESSAGE = "TL";

	private static final String COMPILATION_ERROR_MESSAGE = "CE";

	Solution solution;

	public TestSolution(Solution solution) {
		this.solution = solution;
	}

	public void evaluate() {
		String code = solution.getText();

		String className = getClassName(code);
		JavaFileObject file = getJavaFileObject(className, code);

		Compiler compiler = new Compiler(solution.getUser().getKeyValue());
		try {
			compiler.compile(file);
		} catch (CompilationErrorException e) {
			/// TODO add somewhere the exception message
			solution.setResult(COMPILATION_ERROR_MESSAGE);
			return;
		}
		Long timeLimit = solution.getTask().getTimelimit();

		StringBuilder result = new StringBuilder();

		List<TestCase> testCases = solution.getTask().getTestCases();
		for (TestCase testCase : testCases) {
			String input = testCase.getInput();
			long startTime = System.currentTimeMillis();
			String programOutput = compiler.run(className, input);
			long endTime = System.currentTimeMillis();

			if (((endTime - startTime) > timeLimit) && (timeLimit != null)) {
				addToResult(result, TIME_LIMIT_EXPIRED_MESSAGE);
			}
			String testCaseOutput = testCase.getOutput();
			if (programOutput.equals(testCaseOutput)) {
				addToResult(result, OK_MESSAGE);
			} else {
				addToResult(result, WRONG_ANSWER_MESSAGE);
			}
		}

		solution.setResult(result.toString());

		compiler.cleanClassOutputFolder();
	}

	private void addToResult(StringBuilder result, String message) {
		if (result.length() != 0) {
			result.append(", ");
		}
		result.append(message);
	}

	private String getClassName(String code) {
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

	private JavaFileObject getJavaFileObject(String className, String code) {
		JavaFileObject file = null;

		try {
			file = new InMemoryJavaFileObject(className, code);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO throw exception
		}

		return file;
	}
}
