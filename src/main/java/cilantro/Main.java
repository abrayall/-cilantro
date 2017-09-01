package cilantro;

import java.io.PrintStream;

import javax.io.File;
import javax.lang.Classloader;
import javax.lang.Strings;
import javax.lang.System;
import javax.lang.Try;
import javax.util.List;
import javax.util.Map;

import cilantro.cli.Arguments;
import cilantro.cli.Parser;
import cilantro.io.Console;

public class Main {
	
	protected Console console;
	protected Arguments arguments;
	protected List<String> parameters;
	protected Map<String, String> options;
	
	protected Parser parser = new Parser();
	
	public String name() {
		return "Cilantro";
	}
	
	public String description() {
		return "";
	}
	
	public String version() {
		return "";
	}
	
	public Main initialize(Parser parser) {
		this.parser = parser;
		this.console = new Console(System.out, System.err, System.in).install();
		return this;
	}
	
	public Main print(String message, Object... parameters) {
		return print(console.out, message, parameters);
	}
	
	public Main print(PrintStream stream, String message, Object... parameters) {
		console.printf(stream, message, parameters);
		return this;
	}
	
	public Main println() {
		return println("");
	}
	
	public Main println(String message, Object... parameters) {
		return println(console.out, message, parameters);
	}
	
	public Main println(PrintStream stream, String message, Object... parameters) {
		console.printlnf(stream, message, parameters);
		return this;
	}
	
	public Integer error(String message, Object... parameters) {
		return error(-1, message, parameters);
	}
	
	public Integer error(int code, String message, Object... parameters) {
		console.printlnf("Error: ${0}", Strings.format(message, parameters));
		return code;
	}
	
	public Integer error(Throwable exception) {
		return error(-1, exception);
	}
	
	public Integer error(int code, Throwable exception) {
		return error(code, exception, "Error: ${0}", exception.getMessage());
	}
	
	public Integer error(int code, Throwable exception, String message, Object... parameters) {
		console.printlnf(message, parameters);
		exception.printStackTrace(console.out);
		return code;
	}
	
	public String header() {
		StringBuffer buffer = new StringBuffer();
		String header = this.name() + (this.description().equals("") ? "" : " - " + this.description()) + " " + (this.version().equals("") ? "" : "v" + this.version()).trim();
		buffer.append(Strings.generate('-', header.length()) + "\n");
		buffer.append(header  + "\n");
		buffer.append(Strings.generate('-', header.length()) + "\n");
		return buffer.toString();
	}
	
	public void header(PrintStream stream) {
		stream.print(this.header());
	}
	
	public Integer execute(String[] arguments) throws Exception {
		return this.execute(this.arguments = this.parser.parse(arguments));
	}
	
	public Integer execute(Arguments arguments) throws Exception {
		return this.execute(arguments.parameters, arguments.options);
	}
	
	public Integer execute(List<String> parameters, Map<String, String> options) throws Exception {
		console.log("info", "Please implement me!");
		return 1;
	}
	
	public static Class<?> load(String name) {
		return load(name, System.classloader());
	}
	
	public static Class<?> load(String name, ClassLoader classloader) {
		return Try.attempt(() -> classloader.loadClass(name), (Class<?>) null);
	}
	
	public static Class<?> search(List<File> classpath) throws Exception {
		return System.classloader(classpath).mains().map(clazz -> {
			return clazz.getSuperclass() != null && clazz.getSuperclass().getName().equals(Main.class.getName()) ? clazz : (Class<?>) null;
		}).filter(clazz -> clazz != null).get(0, (Class<?>) null);
	}
	
	public static void main(String[] arguments) throws Exception {
		List<File> classpath = Classloader.classpath(System.getProperty("classpath", System.getProperty("java.class.path")));
		Classloader classloader = Classloader.classloader(classpath);
		Class<?> clazz = load(System.getProperty("main"), classloader);
		if (clazz == null)
			clazz = search(classpath);
		
		main(clazz != null ? clazz : Main.class, arguments);
	}
	
	public static void main(Class<?> clazz, String[] arguments) throws Exception {
		main(clazz, new Parser(), arguments);
	}
	
	public static void main(Class<?> clazz, Parser parser, String[] arguments) throws Exception {
		Integer result = Main.class.cast(clazz.newInstance()).initialize(parser).execute(arguments);
		if (result != null)
			System.exit(result);
	}
}
