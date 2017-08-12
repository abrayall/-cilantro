package cilantro.cli;

import javax.lang.Assert;

import cilantro.Main;

public class TestMain {

	public void test() throws Exception {
		Main.main(Test.class, new String[] {"foo", "bar", "--test", "-test2=test2", "foo2"});
		Assert.equals(3, Test.ARGUMENTS.parameters.size());
		Assert.equals("foo", Test.ARGUMENTS.parameters.get(0));
		Assert.equals("bar", Test.ARGUMENTS.parameters.get(1));
		Assert.equals("foo2", Test.ARGUMENTS.parameters.get(2));
		
		Assert.equals(2, Test.ARGUMENTS.options.size());
		Assert.equals("true", Test.ARGUMENTS.options.get("test"));
		Assert.equals("test2", Test.ARGUMENTS.options.get("test2"));
	}
	
	public static void main(String[] arguments) throws Exception {
		new TestMain().test();
	}
		
	public static class Test extends cilantro.Main {
		public static Arguments ARGUMENTS;
		
		public Integer execute(Arguments arguments) {
			ARGUMENTS = arguments;
			return null;
		}
	}
}
