package cilantro.cli;

import javax.util.List;
import javax.util.Map;

public class Arguments {

	public String[] raw;
	public List<String> parameters;
	public Map<String, String> options;
	
	protected Arguments(String[] raw, List<String> parameters, Map<String, String> options) {
		this.raw = raw;
		this.parameters = parameters;
		this.options = options;
	}
	
	public String[] toArray() {
		return this.raw;
	}
}
