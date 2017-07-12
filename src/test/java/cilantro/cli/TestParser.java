package cilantro.cli;

import javax.lang.Assert;

public class TestParser {

	public void test() throws Exception {
		Parser parser = new Parser();
		Assert.equals(0, parser.parameters(new String[0]).size());
		Assert.equals(1, parser.parameters(new String[] { "foo" }).size());
		Assert.equals(2, parser.parameters(new String[] { "foo", "bar", "--test" }).size());
		Assert.equals("foo", parser.parameters(new String[] { "-test", "foo", "bar", "--test" }).get(0));
		Assert.equals("bar", parser.parameters(new String[] { "-test", "foo", "bar", "--test" }).get(1));
		
		Assert.equals(0, parser.options(new String[0]).size());
		Assert.equals(0, parser.options(new String[] { "foo" }).size());
		Assert.equals(1, parser.options(new String[] { "foo", "bar", "--test" }).size());
		Assert.equals("true", parser.options(new String[] { "-test", "foo", "bar", "--bar" }).get("test"));
		Assert.equals("true", parser.options(new String[] { "--test", "foo", "bar", "--bar" }).get("test"));
		Assert.equals("true", parser.options(new String[] { "--test", "foo", "bar", "-bar" }).get("bar"));
		Assert.equals("true", parser.options(new String[] { "--test", "foo", "bar", "--bar" }).get("bar"));

		Assert.equals("yes", parser.options(new String[] { "-test=yes", "foo", "bar", "--bar=test" }).get("test"));
		Assert.equals("test", parser.options(new String[] { "-test=yes", "foo", "bar", "--bar=test" }).get("bar"));
		Assert.equals("yes", parser.options(new String[] { "--test=yes", "foo", "bar", "-bar=test" }).get("test"));
		Assert.equals("test", parser.options(new String[] { "--test=yes", "foo", "bar", "-bar=test" }).get("bar"));
		Assert.equals("2", parser.options(new String[] { "--test=3", "foo", "bar", "--bar=2", "-foobar=1" }).get("bar"));
		Assert.equals("1", parser.options(new String[] { "--test=3", "foo", "bar", "--bar=2", "-foobar=1" }).get("foobar"));
	}
	
	public static void main(String[] arguments) throws Exception {
		new TestParser().test();
	}
}
