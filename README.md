<img src="https://pbs.twimg.com/profile_images/1509319481/Cilantro-Large_400x400.png" width="150">

# Cilantro - Framework for CLIs
Cilantro is a Java framework for building sweet command line interfaces (CLI).

## CLI Parser 
Cilantro provides a "zero configuration" command line arguments parser.  That means all you have to do is give the parser the String[] arguments, and then you can easily access the parsed parameters and options.

```java
import cilantro.cli.Parser;
...

public static void main(String[] args) {
    Arguments arguments = new Parser().parse(args);
    System.out.println(arguments.parameters);
    System.out.println(arguments.options);
}
```

The CLI Parser supports parsing parameters and options.  Here are some examples:

##### All Parameters
```
[/opt]$ tool foo bar test
```
 - parameters = ["foo", "bar", "test"]
 - options = {}
<br>

##### All options
```
[/opt]$ tool -foo --bar --test=test -test2=foo
```
 - parameters = []
 - options = {"foo": "true", "bar": "true", "test": "test", "test2": "foo"}
<br>


##### Mixed parameters and options
```
[/opt]$ tool bar1 bar2 bar3 -foo --bar --test=test -test2=test2 foo bar4
```
 - parameters = ["bar1", "bar2", "bar3", "bar4"]
 - options = {"foo": "true", "bar": "true", "test": "test", "test2": "test2"}

<br>


## CLI Framework
Cilantro also provides an command line interface framework that can be used with parser or by itself.


### Design Notes
 - Console (used to interact with console)
   - print
   - println
   - printf (with color support?)
   - readln, readXXX
 
 - ColoredConsole (used to interact with console in color :-) )
    - color(name|hex|rgb)
    - red(), blue(), white(), green(), etc
    - Color object (with lighter(), darker(), etc)
 
 - SimpleArgumentParser
   - getParameters(args[]) = list[string]
   - getOptions(args[]) = map[string, string]
   
 - Command base class for all CLI tools
 
