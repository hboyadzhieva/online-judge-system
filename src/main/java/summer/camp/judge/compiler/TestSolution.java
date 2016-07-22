package summer.camp.judge.compiler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.JavaFileObject;

import org.apache.commons.lang.exception.ExceptionUtils;

import summer.camp.judge.entities.Solution;
import summer.camp.judge.entities.TestCase;

@SuppressWarnings("javadoc")
public class TestSolution {

	private static final String OK_MESSAGE = "OK";

	private static final String WRONG_ANSWER_MESSAGE = "WA";

	private static final String TIME_LIMIT_EXPIRED_MESSAGE = "TL";

	private static final String COMPILATION_ERROR_MESSAGE = "CE";

	private static final String RUNTIME_ERROR_MESSAGE = "RE";

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
		} catch (RuntimeException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			rootCause.printStackTrace(pw);
			solution.setLogs(sw.toString());
			solution.setResult(COMPILATION_ERROR_MESSAGE);
			return;
		}
		Long timeLimit = solution.getTask().getTimelimit();

		StringBuilder result = new StringBuilder();

		List<TestCase> testCases = solution.getTask().getTestCases();
		try {

			for (TestCase testCase : testCases) {
				String input = testCase.getInput();
				long startTime = System.currentTimeMillis();
				String programOutput = compiler.run(className, input);
				long endTime = System.currentTimeMillis();

				if ((timeLimit != null) && ((endTime - startTime) > timeLimit)) {
					addToResult(result, TIME_LIMIT_EXPIRED_MESSAGE);
				}
				String testCaseOutput = testCase.getOutput();
				if (testCaseOutput.equals(programOutput)) {
					addToResult(result, OK_MESSAGE);
				} else {
					addToResult(result, WRONG_ANSWER_MESSAGE);
				}
			}
		} catch (ExecutionException ex) {
			Throwable rootCause = ExceptionUtils.getRootCause(ex);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			rootCause.printStackTrace(pw);
			solution.setLogs(sw.toString());
			solution.setResult(RUNTIME_ERROR_MESSAGE);
		}

		if (solution.getResult() == null) {
			solution.setResult(result.toString());
		}

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
