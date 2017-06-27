package cilantro;

import javax.lang.System;
import javax.util.List;
import javax.util.Map;

import cilantro.cli.Parser;
import cilantro.io.Console;

public class Main {
	
	protected Console console;
	protected String[] arguments;
	protected List<String> parameters;
	protected Map<String, String> options;
	
	protected Parser parser = new Parser();
	
	public Main initialize(String[] arguments) {
		this.arguments = arguments;
		this.parameters = parser.parameters(arguments);
		this.options = parser.options(arguments);
		this.console = new Console(System.out, System.err, System.in).install();
		return this;
	}
	
	public Integer execute() throws Exception {
		return this.execute(this.parameters, this.options);
	}
	
	public Integer execute(List<String> parameters, Map<String, String> options) {
		this.console.info("Please implement me!");
		return 1;
	}
	
	public static void main(String[] arguments) throws Exception {
		main(System.loadClass(System.getProperties().get("command").toString()), arguments);
	}
	
	public static void main(Class<?> clazz, String[] arguments) throws Exception {
		System.exit(Main.class.cast(clazz.newInstance()).initialize(arguments).execute());
	}
}
