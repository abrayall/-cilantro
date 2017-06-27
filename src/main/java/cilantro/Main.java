package cilantro;

import javax.util.List;
import javax.util.Map;

import javax.lang.System;
import static javax.util.List.*;
import static javax.util.Map.*;

public class Main {
	
	protected Console console;
	protected List<String> parameters;
	protected Map<String, String> options;
	
	public Main() {
		this(list(), map());
	}
	
	public Main(List<String> parameters, Map<String, String> options) {
		this.parameters = parameters;
		this.options = options;
		this.console = new Console(System.out, System.err, System.in).install();
	}
	
	public Integer execute() throws Exception {
		this.console.info("Please implement me!");
		return 1;
	}
	
	public static void main(String[] arguments) throws Exception {
		main(System.loadClass(System.getProperties().get("command").toString()), arguments);
	}
	
	public static void main(Class<?> clazz, String[] arguments) throws Exception {
		System.exit(Main.class.cast(clazz.newInstance()).execute());
	}
}
