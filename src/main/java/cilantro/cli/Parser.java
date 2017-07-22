package cilantro.cli;

import javax.util.Map;
import javax.util.List;

import static javax.util.Map.*;
import static javax.util.List.*;

public class Parser {
	
	protected String[] arguments;
	
	public Parser() {
		this(new String[0]);
	}
	
	public Parser(String[] arguments) {
		this.arguments = arguments;
	}
	
	public Arguments parse() {
		return parse(this.arguments);
	}
	
	public Arguments parse(String[] arguments) {
		return new Arguments(arguments, parameters(arguments), options(arguments));
	}
	
	public List<String> parameters() {
		return parameters(this.arguments);
	}
	
	public List<String> parameters(String[] arguments) {
		List<String> parameters = list();
		for (String argument : arguments) {
			if (argument.startsWith("-") == false)
				parameters.add(argument);
		}

		return parameters;
	}

	public Map<String, String> options() {
		return options(this.arguments);
	}
	
	public Map<String, String> options(String[] arguments) {
		Map<String, String> options = map();
		for (String argument : arguments)
			if (argument.startsWith("-"))
				put(options, argument);

		return options;
	}
	
	protected Map<String, String> put(Map<String, String> map, String value) {
		return (value.contains("=")) ? put(map, value.split("=")) : put(map, value, "true");			
	}
	
	protected Map<String, String> put(Map<String, String> map, String[] pair) {
		return put(map, pair[0], pair[1]);
	}
	
	protected Map<String, String> put(Map<String, String> map, String key, String value) {
		key = key.replace("-", "");
		if (map.containsKey(key))
			value = map.get(key) + "," + value;
		
		return map.set(key, value);
	}
}

