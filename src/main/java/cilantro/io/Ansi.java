package cilantro.io;

import javax.lang.Strings;
import javax.lang.Try;

import static javax.util.Map.*;

public class Ansi {
	
	public static Color red   = red();
	public static Color black = black();
	public static Color green = green();
	public static Color blue = blue();
	public static Color yellow = yellow();
	public static Color magenta = magenta();
	public static Color cyan = cyan();
	
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
    }
    
    public static class Reset extends Command {
    	protected Reset() {
    		super("m", 0);
    	}
    }
    
    public static Color color(String name) {
    	return Try.attempt(() -> (Color) Ansi.class.getMethod(name).invoke(null), (Color)null);
    }
    
    public static Color color(int number) {
    	return new Color(number);
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
    
    public static String format(String text, Color foreground) {
    	return format(text, foreground, null);
    }
    
    public static String format(String text, Color foreground, Color background) {  
    	return Strings.format("${foreground}${background}${text}${reset}", map(
    		entry("text", text),
    		entry("foreground", foreground != null ? foreground.toString() : ""),
    		entry("background", background != null ? background.toString(true) : ""),
    		entry("reset", reset)
    	));
    }
    
    public static void main(String[] arguments) throws Exception {
    	System.out.println(format("HelloWord", color("black"), color("red")));
    }
}
