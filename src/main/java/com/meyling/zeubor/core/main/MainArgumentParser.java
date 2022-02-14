package com.meyling.zeubor.core.main;

import joptsimple.OptionException;
import joptsimple.OptionSet;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

public class MainArgumentParser extends AbstractArgumentParser {

    public void parseArguments(final String[] args) {
        getOptionParser().accepts("level").withRequiredArg().ofType(Level.class)
                .describedAs("log level")
                .defaultsTo(Level.INFO);
        getOptionParser().accepts("version");
        getOptionParser().acceptsAll( asList( "help", "?" ), "show help" ).forHelp();
        getOptionParser().posixlyCorrect(true);
        try {
            final OptionSet options = getOptionParser().parse(args);
            if (options.has("help")) {
                printHelp();
                System.exit(0);
            }
            if (options.has("version")) {
                System.out.println(new MainContext().getDescriptiveKernelVersion());
                System.exit(0);
            }
            // FIXME
//	    	final Logger logger = (Logger) Log.log;
//	    	logger.setLevel((Level) options.valueOf("level"));
            // System.out.println("log level is: " + logger.getLevel());

            final List<?> ar = options.nonOptionArguments();
            if (ar.size() == 0) {
                errorExitWithHelp("no command given");
            }
            final String command = ar.get(0).toString();
            final String[] arguments = ar.subList(1, ar.size()).toArray(new String[0]);
            switch (command) {
                case "help":
                    if (arguments.length == 0) {
                        printHelp();
                        System.exit(0);
                    } else {
                        String[] help = new String[]{ "--help" };
                        switch (arguments[0]) {
                            case "gui":
                            case "ranking":
                            case "test":
                            case "breed":
                            case "video":
                                parseArguments(arguments[0], help);
                                break;
                            default:
                                errorExitWithHelp("unknown command '" + arguments[0] + "'");
                        }
                    }
                case "gui":
                case "test":
                case "ranking":
                case "breed":
                case "video":
                    parseArguments(command, arguments);
                    break;
                default:
                    errorExitWithHelp("unknown command '" + command + "'");
            }
        } catch (final OptionException e) {
            errorExitWithHelp(e.getMessage());
        }
    }

    public void printHelp() {
        System.out.println("Kumi Zeubors World");
        System.out.println();
        System.out.println("usage: [-l <level>] [-?]  <command> [<options>] [<args>]");
        System.out.println("Simulate virtual algae world with neuronal nets.");
        System.out.println();
        System.out.println("commands are:");
        System.out.println("   gui       Start gui window showing soma algae eating players");
        System.out.println("   test      Test players for fitness in defined scenarios");
        System.out.println("   breed     Breed new generations of players descending from given ones");
        System.out.println("   ranking   Show ranking results");
        System.out.println("   video     Produce video");
        System.out.println("   help      Give details for a command and its options and arguments");
        System.out.println();
        try {
            getOptionParser().printHelpOn(System.out);
        } catch (IOException ex) {
        }
        System.out.println();
        System.out.println("Examples");
        System.out.println();
        System.out.println("   gui 4");
        System.out.println("   -l ERROR breed inga");
        System.out.println("   ranking -i .");
        System.out.println("   video 1");
        System.out.println();
        System.out.println("call 'help <command>' or '<command> --help' for help on a specific command.");
        System.out.println();
        System.out.println("Report bugs by sending email to <kumi.zeubor@gmx.com>");
    }


    private void parseArguments(final String parserName, final String[] args) {
        AbstractArgumentParser parser = getParser(parserName);
        parser.parseArguments(args);
    }

    private AbstractArgumentParser getParser(final String parser) {
        switch (parser) {
            case "gui":
                return new GuiArgumentParser();
            case "test":
                return new TestArgumentParser();
            case "breed":
                return new BreedArgumentParser();
// FIXME
//    	case "ranking":
//    		return new RankingArgumentParser();
            case "video":
                return new VideoArgumentParser();
            default:
                throw new IllegalArgumentException("unknown parser: '" + parser + "'");
        }
    }


}
