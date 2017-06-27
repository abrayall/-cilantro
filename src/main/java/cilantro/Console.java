package cilantro;

import static javax.util.Map.*;
import static javax.util.Timestamp.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.function.Function;

import javax.lang.Strings;
import javax.util.List;
import javax.util.Map;

public class Console {
	
	protected PrintStream out;
	protected PrintStream err;
	protected InputStream in;
	
	public Console() {
		this(System.out, System.err, System.in);
	}
	
	public Console(PrintStream out, PrintStream err, InputStream in) {
		this.out = out;
		this.err = err;
		this.in = in;
	}
	
	public int colors() {
		return 0;
	}
	
	public boolean ansi() {
		return false;
	}
	
	public Console print(String message) {
		return print(message, out);
	}
	
	public Console print(String message, PrintStream stream) {
		stream.print(message);
		return this;
	}
	
	public Console println() {
		return println("");
	}
	
	public Console println(String message) {
		return println(message, out);
	}
	
	public Console println(String message, PrintStream stream) {
		stream.println(message);
		return this;
	}
	
	public Console info(String message) {
		return this.log("info", message, out);
	}
	
	public Console warn(String message) {
		return this.log("warn", message, err);
	}
	
	public Console error(String message) {
		return this.log("error", message, err);
	}
	
	public Console log(String level, String message) {
		return this.log(level, message, level.equalsIgnoreCase("info") ? out : err);
	}
	
	public Console log(String level, String message, PrintStream stream) {
		stream.println(message(level, message));
		return this;
	}
	
	public String message(String level, String message) {
		return Strings.format("[${timestamp}] [${level}] ${message}", map(
			entry("timestamp", now().toString()),
			entry("level", level),
			entry("message", message)
		));
	}
	
	public String format(String source, Object... parameters) {
		return Strings.format(source, parameters);
	}
	
	public String format(String source, java.util.Map<String, Object> parameters) {
		return Strings.format(source, parameters);
	}
	
	public Console install() {
		java.lang.System.setOut(out);
		java.lang.System.setErr(err);
		java.lang.System.setIn(in);
		return this;
	}
	
	public static Map<String, Function<List<String>, String>> functions = map(
	//	entry("color", parameters -> Ansi.color(parameters.get(0, ""), parameters.get(1, "black")))
	);
}
