package cilantro.io;

import static javax.util.Map.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.function.Function;

import javax.lang.System;
import javax.lang.Strings;
import javax.util.List;
import javax.util.Map;
import javax.util.Timestamp;

import cilantro.io.Ansi.Color;

public class Console {
	
	public PrintStream out;
	public PrintStream err;
	public InputStream in;
	
	protected int colors = 0;
	protected Map<String, Function<List<String>, String>> functions;
	
	public Console() {
		this(System.out, System.err, System.in);
	}
	
	public Console(PrintStream out, PrintStream err, InputStream in) {
		this.out = out;
		this.err = err;
		this.in = in;
		
		this.colors = Integer.parseInt(this.detect("colors", "0"));
		this.functions = map(
			entry("format",		parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.commands(parameters))),
			entry("color", 		parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.color(parameters.get(1, "")), Ansi.color(parameters.get(2, "")))),
			entry("foreground", parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.color(parameters.get(1, "")))),
			entry("background", parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), null, Ansi.color(parameters.get(1, "")))),
			entry("black", 		parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.black)),
			entry("red", 		parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.red)),
			entry("green", 		parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.green)),
			entry("blue", 		parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.blue)),
			entry("yellow", 	parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.yellow)),
			entry("magenta", 	parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.magenta)),
			entry("cyan", 		parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.cyan)),
			entry("bold", 		parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.bold)),
			entry("dim", 		parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.dim)),
			entry("underline",  parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.underline)),
			entry("blink", 		parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.blink)),
			entry("reverse",	parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.reverse)),
			entry("hidden", 	parameters -> this.colors < 8 ? parameters.get(0, "") : Ansi.format(parameters.get(0, ""), Ansi.hidden))
		);
	}
	
	public int colors() {
		return this.colors;
	}
	
	public Console colors(int colors) {
		this.colors = colors;
		return this;
	}
	
	public String detect(String type, String defaultValue) {
		String term = System.environment().get("TERM", "").toLowerCase();
		if (type.equalsIgnoreCase("colors") && (System.isUnix() || System.isMac()) && (term.contains("xterm") || term.contains("color") || term.contains("ansi")))
			return "8";
		else if (type.equalsIgnoreCase("colors"))
			return "0";
		
		return defaultValue;
	}
	
	public Console print(String message) {
		return print(out, message);
	}
	
	public Console print(PrintStream stream, String message) {
		stream.print(message);
		return this;
	}
	
	public Console printf(String message, Object... parameters) {
		return printf(out, message, parameters);
	}
	
	public Console printf(PrintStream stream, String message, Object... parameters) {
		stream.print(format(message, parameters));
		return this;
	}
	
	public Console println() {
		return println("");
	}
	
	public Console println(String message) {
		return println(out, message);
	}
	
	public Console println(PrintStream stream, String message) {
		stream.println(message);
		return this;
	}
	
	public Console printlnf(String message, Object... parameters) {
		return printlnf(out, message, parameters);
	}
	
	public Console printlnf(PrintStream stream, String message, Object... parameters) {
		stream.println(format(message, parameters));
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
		return format("[${timestamp}] [${level}] ${message}", map(
			entry("timestamp", Timestamp.now().toString()),
			entry("level", level),
			entry("message", format(message, functions))
		));
	}
	
	public Console install() {
		java.lang.System.setOut(out);
		java.lang.System.setErr(err);
		java.lang.System.setIn(in);
		return this;
	}
	
	public String format(String source, Object... parameters) {
		return Strings.format(source, functions, parameters);
	}
	
	public String format(String source, java.util.Map<String, Object> parameters) {
		return Strings.format(source, functions, parameters);
	}
	
    public static String format(String text, Color foreground) {
    	return format(text, foreground, null);
    }
    
    public static String format(String text, Color foreground, Color background) {  
    	return Ansi.format(text, foreground, background);
    }
	
	public static void main(String[] arguments) throws Exception {
		Console console = new Console().colors(256);
		console.printlnf("${blue(blue)}");
		console.printlnf("${format(should be blue and bold, blue, bold)}");
		console.printlnf("${color(green, 10)}");

		for (int i = 1; i <= 255; i++) 
			console.printlnf("${color(" + i + "," + i + ")}");
		
	}
}
