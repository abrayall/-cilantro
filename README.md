<img src="https://pbs.twimg.com/profile_images/1509319481/Cilantro-Large_400x400.png" width="150">

# Cilantro - Framework for CLIs
Cilantro is a Java framework for building sweet command line interfaces (CLI).



### Design Notes
 - Console (used to interact with console)
   - print
   - println
 
 - ColoredConsole (used to interact with console in color :-) )
    - color(name|hex|rgb)
    - red(), blue(), white(), green(), etc
    - Color object (with lighter(), darker(), etc)
 
 - SimpleArgumentParser
   - getParameters(args[]) = list[string]
   - getOptions(args[]) = map[string, string]
   
 - Command base class for all CLI tools
 
