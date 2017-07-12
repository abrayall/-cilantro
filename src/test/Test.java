package test;

import cilantro.Main;

public class Test extends Main {

	public Integer execute() throws Exception {
		console.println("Cilantro Test v1.0");
		console.println();
		console.info("Starting...");
		return 1;
	}
	
	public static void main(String[] arguments) throws Exception {
		Main.main(Test.class, arguments);
	}
}
