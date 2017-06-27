package cilantro.cli;

import javax.util.Map;
import javax.util.List;

import static javax.util.Map.*;
import static javax.util.List.*;

public class Parser {
	
	public Arguments parse(String[] arguments) {
		return new Arguments(arguments, parameters(arguments), options(arguments));
	}
	
	public List<String> parameters(String[] arguments) {
		List<String> parameters = list();
		for (String argument : arguments) {
			if (argument.startsWith("-") == false)
				parameters.add(argument);
		}

		return parameters;
	}

	public Map<String, String> options(String[] arguments) {
		Map<String, String> options = map();
		for (String argument : arguments) {
			if (argument.startsWith("-") && argument.contains("="))
				options.put(argument.split("=")[0].replaceAll("-", ""), argument.split("=")[1]);
		}

		return options;
	}
}
