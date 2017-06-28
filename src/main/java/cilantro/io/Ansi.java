package cilantro.io;

import static javax.util.Map.*;

import javax.lang.Strings;
import javax.lang.Try;
import javax.util.List;

public class Ansi {
	
	public static Color red   = red();
	public static Color black = black();
	public static Color green = green();
	public static Color blue = blue();
	public static Color yellow = yellow();
	public static Color magenta = magenta();
	public static Color cyan = cyan();
	public static Color white = white();
	
	public static Formatting bold = bold();
	public static Formatting dim = dim();
	public static Formatting underline = underline();
	public static Formatting blink = blink();
	public static Formatting reverse = reverse();
	public static Formatting hidden = hidden();
	
	public static Reset reset = new Reset();
	
    public static class Command {
    	
    	protected int number;
    	protected String category;
    	
    	protected Command(String category, int number) {
    		this.category = category;
    		this.number = number;
    	}
    	
    	public int number() {
    		return this.number;
    	}
    	
    	public String toString() {
    		return this.toString(this.number, this.category);
    	}
    	
    	public String toString(Integer number) {
    		return this.toString(number, this.category);
    	}
    	
    	public String toString(String category) {
    		return this.toString(this.number, category);
    	}
    	
    	public String toString(Integer number, String category) {
    		return Strings.format("\u001b[${0}${1}", number, category);
    	}
    }
    
    public static class Color extends Command {
    	
    	protected Color(int number) {
    		super("m", number);
    	}
    	
    	public String toString(boolean background) {
   			return this.toString(this.number + (background ? 10 : 0));
    	}
    	
    	public Color darker() {
    		return new Color(this.number - 60);
    	}
    	
    	public Color lighter() {
    		return new Color(this.number + 60);
    	}
    }
    
    public static class AdvancedColor extends Color {
    	protected AdvancedColor(int number) {
			super(number);
		}

		public String toString(boolean background) {
    		return Strings.format("\u001b[${0};5${1}%{2}", background ? 48 : 38, number, category);
    	}
    }
    
    public static class Formatting extends Command {
    	protected Formatting(int number) {
    		super("m", number);
    	}
    }
    
    public static class Reset extends Command {
    	protected Reset() {
    		super("m", 0);
    	}
    }
    
    public static Command command(String name) {
    	return Try.attempt(() -> (Command) Ansi.class.getMethod(name).invoke(null), (Command)null); 
    }
    
    public static List<Command> commands(String... names) {
    	return commands(List.list(names));
    }
    
    public static List<Command> commands(List<String> names) {
    	return names.map(name -> Try.attempt(() -> command(name))).filter(command -> command != null);
    }
    
    public static Color color(String name) {
    	return color(name, "normal");
    }
    
    public static Color color(String name, String shade) {
    	Color color = (Color)command(name);
    	if (shade.equals("lighter"))
    		return color.lighter();
    	
    	return color;
    }
    
    public static Color color(int number) {
    	return new Color(number);
    }
    
    public static Color color(int number, boolean advanced) {
    	return advanced ? new AdvancedColor(number) : new Color(number);
    }
    
    public static Color black() {
    	return color(30);
    }
    
    public static Color red() {
    	return color(31);
    }
    
    public static Color green() {
    	return color(32);
    }
    
    public static Color yellow() {
    	return color(33);
    }
    
    public static Color blue() {
    	return color(34);
    }
    
    public static Color magenta() {
    	return color(35);
    }
        
    public static Color cyan() {
    	return color(36);
    }
        
    public static Color white() {
    	return color(37);
    }
    
    public static Formatting formatting(String name) {
    	return (Formatting) command(name);
    }
    
    public static Formatting formatting(int number) {
    	return new Formatting(number);
    }
    
    public static Formatting bold() {
    	return formatting(1);
    }
    
    public static Formatting dim() {
    	return formatting(2);
    }
    
    public static Formatting underline() {
    	return formatting(5);
    }
    
    public static Formatting blink() {
    	return formatting(5);
    }
    
    public static Formatting reverse() {
    	return formatting(7);
    }
    
    public static Formatting hidden() {
    	return formatting(8);
    }
    
    public static String format(String text, String... formatting) {
    	return format(text, commands(formatting));
    }
    
    public static String format(String text, Command... commands) {
    	return format(text, commands);
    }
    
    public static String format(String text, java.util.List<Command> commands) {
    	return Strings.format("${formatting}${text}${reset}", map(
    		entry("text", text),
    		entry("formatting", List.list(commands).map(command -> command.toString()).join("")),
    		entry("reset", reset)
        ));
    }
    
    public static String format(String text, Color foreground) {
    	return format(text, foreground, null);
    }
    
    public static String format(String text, Color foreground, Color background, Formatting... formatting) {  
    	return Strings.format("${foreground}${background}${formatting}${text}${reset}", map(
    		entry("text", text),
    		entry("foreground", foreground != null ? foreground.toString() : ""),
    		entry("background", background != null ? background.toString(true) : ""),
    		entry("formatting", List.list(formatting).map(format -> format.toString()).join("")),
    		entry("reset", reset)
    	));
    }
}
