package summer.camp.judge.compiler;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

@SuppressWarnings("javadoc")
public class InMemoryJavaFileObject extends SimpleJavaFileObject {
	private String contents = null;

	public InMemoryJavaFileObject(String className, String contents) throws Exception {
		super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
		this.contents = contents;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		return contents;
	}
}
